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

@SpringBootApplication
@Configuration
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}
	
	static final String responseExchange = "response.exchange";
	static final String routingKey = "response.routing.key";
	static final String insertionQueue = "insertion.queue";
	static final String validationQueue = "validation.queue";
	
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

//	@Bean
//	DirectExchangeRoutingKeyConfigurer bindingInsertion(@Qualifier("queueInsertion") Queue queueInsertion, DirectExchange exchange) {
//		return BindingBuilder.bind(queueInsertion).to(exchange);
//	}
//	
//	@Bean
//	DirectExchangeRoutingKeyConfigurer queueValidation(@Qualifier("queueValidation") Queue queueValidation, DirectExchange exchange) {
//		return BindingBuilder.bind(queueValidation).to(exchange);
//	}
	
	@Bean
	SimpleMessageListenerContainer messageListenerContainerInsertion() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setConcurrentConsumers(3);
		container.setQueueNames(insertionQueue);
		container.setMessageListener(new ListenerInsertion());
		return container;
	}
	
	@Bean
	SimpleMessageListenerContainer messageListenerContainerValidation() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setConcurrentConsumers(3);
		container.setQueueNames(validationQueue);
		container.setMessageListener(new ListenerValidation());
		return container;
	}
//	
//	@Bean
//    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(null);
//        return rabbitTemplate;
//    }

//	@Bean
//	MessageListenerAdapter listenerAdapter(Receiver receiver) {
//		return new MessageListenerAdapter(receiver, "receiveMessage");
//	}

}
