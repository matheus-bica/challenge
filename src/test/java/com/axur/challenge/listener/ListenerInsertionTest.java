package com.axur.challenge.listener;

import com.axur.challenge.DAO.WhitelistDAO;
import com.axur.challenge.formatters.JsonFormatter;
import com.axur.challenge.model.Whitelist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//With this property we load every test to the second database in order to not create or use the main database
@TestPropertySource(properties = {"spring.datasource.url = jdbc:mysql://${MYSQL_HOST:localhost}:3306/axr_challenge_test?user=root&password=secret"})
class ListenerInsertionTest {

    @Resource
    ListenerInsertion listenerInsertion;
    @Resource
    WhitelistDAO whitelistDAO;

    List<Whitelist> listWhitelist = new ArrayList<>();

    @AfterEach
    void tearDown() {
        listWhitelist.forEach(whitelist -> {
            if (whitelist != null) {
                whitelistDAO.delete(whitelist);
            }
        });
    }

    @Test
    void onMessageSimpleInsertion() {
        String bodyMessage = "{'client'='bica', 'regex'='[a-z]'}";

        MessageProperties messagePropertiesClientNotNull = new MessageProperties();
        messagePropertiesClientNotNull.setCorrelationId("15");
        messagePropertiesClientNotNull.setReceivedDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
        messagePropertiesClientNotNull.setRedelivered(false);
        messagePropertiesClientNotNull.setReceivedRoutingKey("insertion.queue");
        messagePropertiesClientNotNull.setDeliveryTag(1);
        messagePropertiesClientNotNull.setConsumerTag("amq.ctag-6Y5ZOrM-M4JypcT-Z1DBpw");
        messagePropertiesClientNotNull.setConsumerQueue("insertion.queue");

        Message messageClientNotNull = new Message(bodyMessage.getBytes(), messagePropertiesClientNotNull);

        //Testing the Insertion of one data in the database
        listenerInsertion.onMessage(messageClientNotNull);
        Whitelist actualWhitelist = whitelistDAO.findByClientAndRegex("bica", "[a-z]");
        listWhitelist.add(actualWhitelist);
        assertNotNull(actualWhitelist);
    }

    @Test
    void wrongColumnInInput() {
        String bodyMessage = "{'client'='bicatest', 'invalidcolumn'='invalid', 'regex'='[a-z]'}";

        MessageProperties messagePropertiesClientNotNull = new MessageProperties();
        messagePropertiesClientNotNull.setCorrelationId("15");
        messagePropertiesClientNotNull.setReceivedDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
        messagePropertiesClientNotNull.setRedelivered(false);
        messagePropertiesClientNotNull.setReceivedRoutingKey("insertion.queue");
        messagePropertiesClientNotNull.setDeliveryTag(1);
        messagePropertiesClientNotNull.setConsumerTag("amq.ctag-6Y5ZOrM-M4JypcT-Z1DBpw");
        messagePropertiesClientNotNull.setConsumerQueue("insertion.queue");

        Message messageClientNotNull = new Message(bodyMessage.getBytes(), messagePropertiesClientNotNull);

        //Testing the Insertion of one data in the database
        listenerInsertion.onMessage(messageClientNotNull);
        Whitelist actualWhitelist = whitelistDAO.findByClientAndRegex("bicatest", "[a-z]");
        listWhitelist.add(actualWhitelist);
        assertNotNull(actualWhitelist);
    }

    @Test
    void withNullClientReceivedInsertion() {
        JsonFormatter inputData = new JsonFormatter(null, "[A-Z]", "", "15", false);
        listenerInsertion.receivedInsertion(inputData);
        Whitelist actualWhitelist = whitelistDAO.findByClientNullAndRegex(inputData.getRegex());
        listWhitelist.add(actualWhitelist);
        assertNotNull(actualWhitelist);
    }
}