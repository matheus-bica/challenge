package com.axur.challenge.listener;

import java.util.List;
import java.util.regex.Pattern;

import com.axur.challenge.DAO.WhitelistDAO;
import com.axur.challenge.formatters.JsonFormatter;
import com.axur.challenge.model.Whitelist;
import com.axur.challenge.sender.SendResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ListenerValidation implements MessageListener {

    private static final Logger logger = LogManager.getLogger(ListenerValidation.class);

    @Resource
    private WhitelistDAO whitelistDAO;

    @Resource
    SendResponse sendResponse;

    @Override
    public void onMessage(Message message) {
        logger.info("Received Validation: " + new String(message.getBody()) + " From: "
                + message.getMessageProperties().getReceivedRoutingKey());
        try {
            JsonFormatter jsonFormatter = new Gson().fromJson(new String(message.getBody()), JsonFormatter.class);
            receivedValidation(jsonFormatter);
        } catch (JsonParseException e) {
            logger.info("It was not possible to read the input");
        }
    }

    public boolean receivedValidation(JsonFormatter inputData) {
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
                    logger.info("Found a match for " + inputData.getUrl() + " using regex " + regexMatched);
                }
                index++;
            }
        } else {
            logger.info("Did not found any Line in the database for this client or any global");
        }
        inputData.setRegex(regexMatched);
        inputData.setMatch(match);

        sendResponse.sendMessage(inputData);

        return match;
    }

    public boolean checkRegex(String regex, String url) {
        return Pattern.compile(regex).matcher(url).find();
    }
}