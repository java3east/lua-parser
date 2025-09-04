package de.fb.luaparser.base;

import de.fb.luaparser.BaseParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CharParserTest {
    @Test
    public void testShouldParseChar() {
        CharParser parser = new CharParser('a');
        char result = parser.parse("a");
        assertEquals('a', result);
    }

    @Test
    public void testShouldThrowErrorWhenCharDoesNotMatch() {
        CharParser parser = new CharParser('a');
        assertThrows(BaseParseException.class, () -> parser.parse("b"));
    }

    @Test
    public void testShouldThrowErrorWhenEndOfInputIsReached() {
        CharParser parser = new CharParser('a');
        assertThrows(BaseParseException.class, () -> parser.parse(""));
    }
}
