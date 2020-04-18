package com.axur.challenge.listener;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axur.challenge.DAO.WhitelistDAO;
import com.axur.challenge.formatters.InputData;
import com.axur.challenge.model.Whitelist;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

@Component
public class ListenerValidation implements MessageListener {

	@Autowired
	private WhitelistDAO whitelistDAO;

	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(String message) {
		System.out.println("Received message: " + message);
		latch.countDown();
	}

	@Override
	public void onMessage(Message message) {
		System.out.println("Received Validation: " + new String(message.getBody()) + " From: "
				+ message.getMessageProperties().getReceivedRoutingKey());
		try {
			InputData inputData = new Gson().fromJson(new String(message.getBody()), InputData.class);
			receivedValidation(inputData);
		} catch (JsonParseException e) {
			System.out.println("It was not possible to read the input");
		}
	}

	public void receivedValidation(InputData inputData) {
		// getting all regex from this client
		List<Whitelist> listWhitelist = whitelistDAO.getWhitelist(inputData.getClient());
		boolean match = false;
		// verify if the regex works for the url provided
		if (listWhitelist != null) {
			int index = 0;
			while (match == false && index < listWhitelist.size()) {
				match = checkRegex(listWhitelist.get(index).getRegex(), inputData.getUrl());
				index++;
			}
		}

		if (match == true) {

		} else {

		}
	}

	public boolean checkRegex(String regex, String url) {
		return Pattern.compile(regex).matcher(url).find();
	}
}