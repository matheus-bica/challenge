package com.axur.challenge;

//import java.util.concurrent.TimeUnit;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Runner implements CommandLineRunner {
//
//	private final RabbitTemplate rabbitTemplate;
//	private final Receiver receiver;
//
//	public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
//		this.receiver = receiver;
//		this.rabbitTemplate = rabbitTemplate;
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//			System.out.println("Sending message...");
////			rabbitTemplate.convertAndSend(ChallengeApplication.responseExchange, ChallengeApplication.queueInsertion, "{'client':123, 'regex':'[a-z]'}");
////			rabbitTemplate.convertAndSend(ChallengeApplication.responseExchange, ChallengeApplication.queueValidation, "{'client':null, 'regex':'[a-z]'}");
////			//rabbitTemplate.convertAndSend(ChallengeApplication.responseExchange, ChallengeApplication.routingKey, "{'client':null, 'regex':'[a-z]'}");
////			receiver.getLatch().await(3, TimeUnit.SECONDS);
//	}
//}