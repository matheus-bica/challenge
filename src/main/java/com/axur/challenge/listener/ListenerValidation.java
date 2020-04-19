package com.axur.challenge.listener;

import java.util.List;
import java.util.regex.Pattern;

import com.axur.challenge.DAO.WhitelistDAO;
import com.axur.challenge.formatters.JsonFormatter;
import com.axur.challenge.model.Whitelist;
import com.axur.challenge.sender.SendResponse;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ListenerValidation implements MessageListener {

	@Resource
	private WhitelistDAO whitelistDAO;

	@Resource
	SendResponse sendResponse;

	@Override
	public void onMessage(Message message) {
		System.out.println("Received Validation: " + new String(message.getBody()) + " From: "
				+ message.getMessageProperties().getReceivedRoutingKey());
		try {
			JsonFormatter jsonFormatter = new Gson().fromJson(new String(message.getBody()), JsonFormatter.class);
			receivedValidation(jsonFormatter);
		} catch (JsonParseException e) {
			System.out.println("It was not possible to read the input");
		}
	}

	public void receivedValidation(JsonFormatter inputData) {
//		Getting all regex from this client
		List<Whitelist> listWhitelist = whitelistDAO.findByClientAndGlobal(inputData.getClient());
		boolean match = false;
		String regexMatched = "";
//		Verify if the regex works for the url provided
		if (listWhitelist.size() != 0) {
			int index = 0;
			while (!match && index < listWhitelist.size()) {
				match = checkRegex(listWhitelist.get(index).getRegex(), inputData.getUrl());
				if (match) {
					regexMatched = listWhitelist.get(index).getRegex();
				}
				index++;
			}
		}
		inputData.setRegex(regexMatched);
		inputData.setMatch(match);

		sendResponse.sendMessage(inputData);
	}

	public boolean checkRegex(String regex, String url) {
		return Pattern.compile(regex).matcher(url).find();
	}
}