package com.axur.challenge.DAO;

import com.axur.challenge.model.Whitelist;
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
class WhitelistDAOTest {

    @Resource
    private WhitelistDAO repository;

    Whitelist whitelist = new Whitelist("test", "[a-z]");
    Whitelist whitelist1 = new Whitelist(null, "[0-9]");
    Whitelist whitelist2 = new Whitelist("test4", "[1]");

    @BeforeEach
    void setUp() {
        repository.save(whitelist);
        repository.save(whitelist1);
        repository.save(whitelist2);
    }

    @AfterEach
    void tearDown() {
        repository.delete(whitelist);
        repository.delete(whitelist1);
        repository.delete(whitelist2);
    }

    @Test
    void findByClientAndRegexNotNullValues() {
        Whitelist whitelistActual = this.repository.findByClientAndRegex(whitelist.getClient(), whitelist.getRegex());
        assertEquals(whitelistActual.getClient(), whitelist.getClient());
        assertEquals(whitelistActual.getRegex(), whitelist.getRegex());

        whitelistActual = repository.findByClientAndRegex(whitelist2.getClient(), whitelist2.getRegex());
        assertEquals(whitelist2.getClient(), whitelistActual.getClient());
        assertEquals(whitelist2.getRegex(), whitelistActual.getRegex());
    }

    @Test
    void findByClientNullAndRegex() {
        Whitelist whitelistActual = repository.findByClientNullAndRegex(whitelist1.getRegex());
        assertEquals(whitelist1.getClient(), whitelistActual.getClient());
        assertEquals(whitelist1.getRegex(), whitelistActual.getRegex());
    }

    @Test
    void findByClientAndGlobal() {
//        List<Whitelist> listWhitelist = new ArrayList<>();
//        listWhitelist.add(whitelist);
//        listWhitelist.add(whitelist1);
//
//        List<Whitelist> listWhitelistActual = repository.findByClientAndGlobal(whitelist.getClient());
//        assertEquals(listWhitelist,listWhitelistActual);
    }
}