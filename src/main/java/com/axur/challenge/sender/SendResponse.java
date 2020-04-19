package com.axur.challenge.sender;

import com.axur.challenge.ChallengeApplication;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class SendResponse implements Serializable {

	private final RabbitTemplate rabbitTemplate;

    public SendResponse(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String regex, String correlationId) {
        System.out.println("Sending Message...");
        String jsonMessage = "{'match':true, 'regex':" + regex + ", 'correlationId': " + correlationId + "}";
        rabbitTemplate.convertAndSend(ChallengeApplication.responseExchange, ChallengeApplication.routingKey, jsonMessage);
    }
}
