package com.axur.challenge.formatters;

import com.axur.challenge.model.Whitelist;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonFormatterTest {

    private final JsonFormatter getOutput = new JsonFormatter("client", "regex", "url", "15", true);
    private final JsonFormatter getOutputNullRegexFalseMatch = new JsonFormatter("client", "", "url", "15", false);
    private final JsonFormatter getOutputRegexAndTrueMatch = new JsonFormatter("client", "[a-z]", "url", "15", true);
    String actual;
    String expected;

    @Test
    void getOutputMessage() {
        actual = getOutput.getOutputMessage();
        expected = "{'match':true , 'regex':'regex', 'correlationId':15}";
        assertEquals(expected, actual);
    }

    @Test
    void getOutputMessageEmptyRegexFalseMatch() {
        actual = getOutputNullRegexFalseMatch.getOutputMessage();
        expected = "{'match':false , 'regex':null, 'correlationId':15}";
        assertEquals(expected, actual);
    }

    @Test
    void getOutputMessageRegexAndTrueMatch() {
        actual = getOutputRegexAndTrueMatch.getOutputMessage();
        expected = "{'match':true , 'regex':'[a-z]', 'correlationId':15}";
        assertEquals(expected, actual);
    }

    @Test
    void testToString() {
        actual = getOutput.toString();
        expected = "{'client':'client', 'regex':'regex', 'url':'url', 'correlationId':'15', 'match':true}";
        assertEquals(expected, actual);

        actual = getOutputNullRegexFalseMatch.toString();
        expected = "{'client':'client', 'regex':'', 'url':'url', 'correlationId':'15', 'match':false}";
        assertEquals(expected, actual);

        actual = getOutputRegexAndTrueMatch.toString();
        expected = "{'client':'client', 'regex':'[a-z]', 'url':'url', 'correlationId':'15', 'match':true}";
        assertEquals(expected, actual);
    }
}