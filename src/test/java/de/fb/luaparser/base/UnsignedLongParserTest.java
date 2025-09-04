package de.fb.luaparser.base;

import de.fb.luaparser.BaseParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class UnsignedLongParserTest {
    @Test
    public void testShouldParseUnsignedLong() {
        UnsignedLongParser parser = UnsignedLongParser.INSTANCE;
        long result1 = parser.parse("12345");
        long result2 = parser.parse("0");
        long result3 = parser.parse("9876543210");

        assertEquals(12345L, result1);
        assertEquals(0L, result2);
        assertEquals(9876543210L, result3);
    }

    @Test
    public void testShouldThrowErrorWhenOutOfRange() {
        UnsignedLongParser parser = UnsignedLongParser.INSTANCE;
        assertThrows(BaseParseException.class, () -> parser.parse("18446744073709551616"));
    }
}
