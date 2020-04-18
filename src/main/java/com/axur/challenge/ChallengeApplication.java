package com.axur.challenge;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.axur.challenge.listener.ListenerInsertion;
import com.axur.challenge.listener.ListenerValidation;

import javax.annotation.Resource;

@SpringBootApplication
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}
	
	static final String responseExchange = "response.exchange";
	static final String routingKey = "response.routing.key";
	static final String insertionQueue = "insertion.queue";
	static final String validationQueue = "validation.queue";

	@Resource
	ListenerInsertion listenerInsertion;
	@Resource
	ListenerValidation listenerValidation;
	
	@Bean
	public ConnectionFactory connectionFactory() {
	    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
	    connectionFactory.setHost("127.0.0.1");
	    connectionFactory.setVirtualHost("/");
	    connectionFactory.setUsername("guest");
	    connectionFactory.setPassword("guest");
	    return connectionFactory;
	}
	
	@Bean
	Queue queueInsertion() {
		return new Queue(insertionQueue, true);
	}
		
	@Bean
	Queue queueValidation() {
		return new Queue(validationQueue, true);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(responseExchange);
	}

	@Bean
	SimpleMessageListenerContainer messageListenerContainerInsertion() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setConcurrentConsumers(3);
		container.setQueueNames(insertionQueue);
		container.setMessageListener(listenerInsertion);
		return container;
	}
	
	@Bean
	SimpleMessageListenerContainer messageListenerContainerValidation() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setConcurrentConsumers(3);
		container.setQueueNames(validationQueue);
		container.setMessageListener(listenerValidation);
		return container;
	}

}
