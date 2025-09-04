package de.fb.luaparser.base;

import de.fb.luaparser.BaseParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class AnyParserTest {
    @Test
    public void testShouldParseAny() {
        AnyParser<Character> parser = new AnyParser<>(
                CharParser.get('a'), CharParser.get('b'), CharParser.get('c')
        );

        char resultA = parser.parse("a");
        char resultB = parser.parse("b");
        char resultC = parser.parse("c");

        assertEquals('a', resultA);
        assertEquals('b', resultB);
        assertEquals('c', resultC);
    }

    @Test
    public void testShouldThrowErrorWhenNoParserMatches() {
        AnyParser<Character> parser = new AnyParser<>(
                CharParser.get('a'), CharParser.get('b'), CharParser.get('c')
        );

        assertThrows(BaseParseException.class, () -> parser.parse("d"));
    }
}
