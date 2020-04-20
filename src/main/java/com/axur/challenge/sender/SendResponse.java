package com.axur.challenge.sender;

import com.axur.challenge.ChallengeApplication;
import com.axur.challenge.formatters.JsonFormatter;
import com.axur.challenge.listener.ListenerValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class SendResponse implements Serializable {
    private static final Logger logger = LogManager.getLogger(SendResponse.class);

    private final RabbitTemplate rabbitTemplate;

    public SendResponse(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(JsonFormatter inputData) {
        String jsonMessage = inputData.getOutputMessage();
        logger.info("Sending back message after validation:" + jsonMessage);
        rabbitTemplate.convertAndSend(ChallengeApplication.responseExchange, ChallengeApplication.routingKey, jsonMessage);
    }
}
