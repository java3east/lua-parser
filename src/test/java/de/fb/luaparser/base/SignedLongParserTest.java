package de.fb.luaparser.base;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SignedLongParserTest {
    @Test
    public void testShouldParseSignedLong() {
        SignedLongParser parser = SignedLongParser.INSTANCE;
        long result1 = parser.parse("12345");
        long result2 = parser.parse("-12345");
        long result3 = parser.parse("+12345");

        assertEquals(12345L, result1);
        assertEquals(-12345L, result2);
        assertEquals(12345L, result3);
    }

    @Test
    public void testShouldParseZero() {
        SignedLongParser parser = SignedLongParser.INSTANCE;
        long result1 = parser.parse("0");
        long result2 = parser.parse("-0");
        long result3 = parser.parse("+0");

        assertEquals(0L, result1);
        assertEquals(0L, result2);
        assertEquals(0L, result3);
    }
}
