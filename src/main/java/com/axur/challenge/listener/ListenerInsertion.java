package com.axur.challenge.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.axur.challenge.DAO.DAO;
import com.axur.challenge.DAO.WhitelistDAO;
import com.axur.challenge.formatters.InputData;
import com.axur.challenge.model.Whitelist;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

@EnableAutoConfiguration
@Component
public class ListenerInsertion implements MessageListener {
	
	@Autowired
	private static DAO<Whitelist> whitelistDAO = new WhitelistDAO();
			
	@Override
	public void onMessage(Message message) {
		System.out.println("Received Insertion: " + new String(message.getBody()) + " From: "
				+ message.getMessageProperties().getReceivedRoutingKey());
		try {
			InputData inputData = new Gson().fromJson(new String(message.getBody()), InputData.class);
			System.out.println("InputData: " + inputData);
			receivedInsertion (inputData);
		} catch (JsonParseException e) {
			System.out.println("It was not possible to read the input");
		}
	}
	
	@TransactionalEventListener
	public void receivedInsertion(InputData inputData) {
		Whitelist whitelist = new Whitelist();
		String client = inputData.getClient();

		if (client == null) {
			whitelist.setClient("global");
		} else {
			whitelist.setClient(inputData.getClient());
		}
		whitelist.setRegex(inputData.getRegex());

		System.out.println("Received Insertion Whitelist: " + whitelist);
		try {
			// Forçando assim funciona
			// A estrutura da tabela whitelist
			// String client, String regex (igual a classe Whitelist)
			// --------------------------------------------------------
//			String query = "select * from whitelist limit 1";
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/axr_challenge","root","secret");
//			Statement st = con.createStatement();
//			ResultSet rs = st.executeQuery(query);
//			rs.next();
//			String result = rs.getString("client");
//			System.out.println("Result: " + result);
			// --------------------------------------------------------
			//System.out.println("ENTITY MANAGER----> " + em);
			Whitelist selectedWhitelist = whitelistDAO.getSpecific(whitelist);
			System.out.println("selectedWhitelist: " + selectedWhitelist);
//			if (selectedWhitelist == null || !selectedWhitelist.equals(whitelist)) {
//				whitelistDAO.insertWhitelist(whitelist);
//				System.out.println("Whitelist Added");
//			} else {
//				System.out.println("Already added before");
//			}
//
		} catch (NullPointerException dae) {
			System.out.println("Whitelist NOT Added");
			System.err.println(dae);
		}
	}

}