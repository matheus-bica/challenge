package com.axur.challenge.listener;

import com.axur.challenge.DAO.WhitelistDAO;
import com.axur.challenge.formatters.JsonFormatter;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

import com.axur.challenge.model.Whitelist;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import javax.annotation.Resource;

@Service
public class ListenerInsertion implements MessageListener {

    @Resource
    private WhitelistDAO whitelistDAO;

    @Override
    public void onMessage(Message message) {
        System.out.println("Received Insertion: " + new String(message.getBody()) + " From: "
                + message.getMessageProperties().getReceivedRoutingKey());
        try {
            JsonFormatter inputData = new Gson().fromJson(new String(message.getBody()), JsonFormatter.class);
            receivedInsertion(inputData);
        } catch (JsonParseException e) {
            System.out.println("It was not possible to read the input message");
        }
    }

    public void receivedInsertion(JsonFormatter inputData) {
        Whitelist whitelist = new Whitelist(inputData.getClient(), inputData.getRegex());
        try {
            Whitelist selectWhitelist;
            if (inputData.getClient() == null) {
                selectWhitelist = whitelistDAO.findByClientNullAndRegex(whitelist.getRegex());
            } else {
                selectWhitelist = whitelistDAO.findByClientAndRegex(whitelist.getClient(), whitelist.getRegex());
            }
            if (selectWhitelist == null) {
                whitelistDAO.save(whitelist);
                System.out.println("Whitelist client: " + whitelist.getClient() + " and regex: " + whitelist.getRegex() + " was inserted successfully!");
            } else {
                System.out.println("The pair client and regex already exist");
            }
        } catch (Exception dae) {
            System.out.println(dae);
            dae.printStackTrace();
        }
    }

}