package com.axur.challenge.listener;

import com.axur.challenge.ChallengeApplication;
import com.axur.challenge.DAO.WhitelistDAO;
import com.axur.challenge.formatters.JsonFormatter;
import com.axur.challenge.model.Whitelist;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChallengeApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ListenerValidationTest {

    @Resource
    ListenerValidation listenerValidation;

    @Resource
    WhitelistDAO whitelistDAO;

    private final Whitelist whitelistDownWords = new Whitelist("test","[a-z]");
    private final Whitelist whitelistUpperWords = new Whitelist("test1","[A-Z]");
    private final Whitelist whitelistNullClient = new Whitelist(null, "[0-9]");

    @BeforeEach
    void setUp() {
        whitelistDAO.save(whitelistDownWords);
        whitelistDAO.save(whitelistUpperWords);
        whitelistDAO.save(whitelistNullClient);
    }

    @Test
    void receivedValidationFound() {
        JsonFormatter inputData = new JsonFormatter("test", "", "abcdecom", "20", false);
        assertTrue(listenerValidation.receivedValidation(inputData));
    }

    @Test
    void receivedValidationNotFound() {
        JsonFormatter inputData = new JsonFormatter("test1", "", "abcd.com", "20", false);
        assertFalse(listenerValidation.receivedValidation(inputData));
    }

    @Test
    void receivedValidationClientNonExistent() {
        JsonFormatter inputData = new JsonFormatter("bica", "", "123456", "20", false);
        assertTrue(listenerValidation.receivedValidation(inputData));
    }

    @Test
    void checkRegex() {

    }
}