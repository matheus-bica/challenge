package com.axur.challenge.listener;

import com.axur.challenge.DAO.WhitelistDAO;
import com.axur.challenge.formatters.JsonFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

import com.axur.challenge.model.Whitelist;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import javax.annotation.Resource;

@Service
public class ListenerInsertion implements MessageListener {

    private static final Logger logger = LogManager.getLogger(ListenerInsertion.class);

    @Resource
    private WhitelistDAO whitelistDAO;

    @Override
    public void onMessage(Message message) {
        logger.info("Received Insertion" +  new String(message.getBody()) + " From: "
                + message.getMessageProperties().getReceivedRoutingKey());
        try {
            JsonFormatter inputData = new Gson().fromJson(new String(message.getBody()), JsonFormatter.class);
            receivedInsertion(inputData);
        } catch (JsonParseException e) {
            logger.info("It was not possible to read the input message");
        }
    }

    public boolean receivedInsertion(JsonFormatter inputData) {
        Whitelist whitelist = new Whitelist(inputData.getClient(), inputData.getRegex());
        try {
            Whitelist selectWhitelist;
            if (inputData.getClient() == null) {
                logger.info("Client equals null");
                selectWhitelist = whitelistDAO.findByClientNullAndRegex(whitelist.getRegex());
            } else {
                selectWhitelist = whitelistDAO.findByClientAndRegex(whitelist.getClient(), whitelist.getRegex());
            }
            if (selectWhitelist == null) {
                logger.info("Saving Whitelist: " + whitelist.toString());
                whitelistDAO.save(whitelist);
                logger.info("Saved Whitelist: " + whitelist.toString());
                return true;
            } else {
                logger.info("The pair client and regex already exist");
            }
        } catch (Exception dae) {
            logger.error("An error occurred when trying to receive an insertion" + dae);
            dae.printStackTrace();
            return false;
        }
        return false;
    }

}