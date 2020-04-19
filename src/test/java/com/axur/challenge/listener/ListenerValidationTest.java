package com.axur.challenge.listener;

import com.axur.challenge.DAO.WhitelistDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//With this property we load every test to the second database in order to not create or use the main database
@TestPropertySource(properties = {"spring.datasource.url = jdbc:mysql://${MYSQL_HOST:localhost}:3306/axr_challenge_test?user=root&password=secret"})
class ListenerValidationTest {

    @Resource
    ListenerValidation listenerValidation;

    @Resource
    WhitelistDAO whitelistDAO;

    @Before
    void setUp() {

    }

    @After
    void tearDown() {
    }

    @Test
    void onMessage() {
    }

    @Test
    void receivedValidation() {
    }

    @Test
    void checkRegex() {
    }
}