package com.axur.challenge.DAO;

import com.axur.challenge.ChallengeApplication;
import com.axur.challenge.model.Whitelist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChallengeApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class WhitelistDAOTest {

    @Autowired
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

    @Test
    void findByClientAndRegexNotNullValues() {
        Whitelist whitelistActual = this.repository.findByClientAndRegex(whitelist.getClient(), whitelist.getRegex());
        assertEquals(whitelistActual.getClient(), whitelist.getClient());
        assertEquals(whitelistActual.getRegex(), whitelist.getRegex());

        whitelistActual = this.repository.findByClientAndRegex(whitelist2.getClient(), whitelist2.getRegex());
        assertEquals(whitelist2.getClient(), whitelistActual.getClient());
        assertEquals(whitelist2.getRegex(), whitelistActual.getRegex());
    }

    @Test
    void findByClientNullAndRegex() {
        Whitelist whitelistActual = this.repository.findByClientNullAndRegex(whitelist1.getRegex());
        assertEquals(whitelist1.getClient(), whitelistActual.getClient());
        assertEquals(whitelist1.getRegex(), whitelistActual.getRegex());
    }

    @Test
    void findByClientAndGlobal() {
        List<Whitelist> listWhitelist = new ArrayList<>();
        listWhitelist.add(whitelist);
        listWhitelist.add(whitelist1);

        List<Whitelist> listWhitelistActual = repository.findByClientAndGlobal(whitelist.getClient());
        for (Integer index = 0; index < listWhitelist.size(); index++){
            assertEquals(listWhitelist.get(index).getClient(),listWhitelistActual.get(index).getClient());
            assertEquals(listWhitelist.get(index).getRegex(),listWhitelistActual.get(index).getRegex());
        }
    }
}