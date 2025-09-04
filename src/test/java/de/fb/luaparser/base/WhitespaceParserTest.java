package de.fb.luaparser.base;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WhitespaceParserTest {
    @Test
    public void testParseWhitespace() {
        WhitespaceParser parser = WhitespaceParser.INSTANCE;
        assertEquals("   ", parser.parse("   abc", 0).result());
        assertEquals(" \t\n", parser.parse(" \t\nabc", 0).result());
        assertEquals("\r\n ", parser.parse("\r\n abc", 0).result());
    }
}
