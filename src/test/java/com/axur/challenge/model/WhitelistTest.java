package com.axur.challenge.model;

import com.axur.challenge.formatters.JsonFormatter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhitelistTest {

    private final Whitelist whitelist = new Whitelist("test", "[a-z]");
    private final Whitelist whitelist_dif = new Whitelist("different", "[0-9]");
    private final Whitelist whitelist2 = new Whitelist("test", "[a-z]");
    private final JsonFormatter jsonFormatter = new JsonFormatter();


    @Test
    void testEqualsTrue() {
        assertEquals(whitelist, whitelist2);
    }

    @Test
    void testEqualsFalse() {
        assertNotEquals(whitelist, whitelist_dif);
    }

    @Test
    void testEqualsDifObject() {
        assertNotEquals(whitelist, jsonFormatter);
    }
}