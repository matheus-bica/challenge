package com.axur.challenge.sender;

import com.axur.challenge.ChallengeApplication;
import com.axur.challenge.formatters.JsonFormatter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class SendResponse implements Serializable {

    private final RabbitTemplate rabbitTemplate;

    public SendResponse(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(JsonFormatter inputData) {
        System.out.println("Sending Message...");
        String jsonMessage = inputData.getOutputMessage();
        System.out.println("Sending back message:" + jsonMessage);
        rabbitTemplate.convertAndSend(ChallengeApplication.responseExchange, ChallengeApplication.routingKey, jsonMessage);
    }
}
