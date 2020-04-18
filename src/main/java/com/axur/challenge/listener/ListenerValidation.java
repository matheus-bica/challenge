package com.axur.challenge.listener;

import java.util.List;
import java.util.regex.Pattern;

import com.axur.challenge.DAO.WhitelistDAO;
import com.axur.challenge.formatters.JsonFormatter;
import com.axur.challenge.model.Whitelist;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ListenerValidation implements MessageListener {

	@Resource
	private WhitelistDAO whitelistDAO;

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
		List<Whitelist> listWhitelist = whitelistDAO.findAll();
		boolean match = false;
//		Verify if the regex works for the url provided
		if (listWhitelist != null) {
			for (int i = 0; i < listWhitelist.size(); i++){
				System.out.println(listWhitelist.get(i));
			}
//			int index = 0;
//			while (match == false && index < listWhitelist.size()) {
//				match = checkRegex(listWhitelist.get(index).getRegex(), inputData.getUrl());
//				index++;
//			}
		}

		if (match == true) {

		} else {

		}
	}

	public boolean checkRegex(String regex, String url) {
		return Pattern.compile(regex).matcher(url).find();
	}
}