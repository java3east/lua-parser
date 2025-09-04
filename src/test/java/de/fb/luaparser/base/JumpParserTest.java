package de.fb.luaparser.base;

import de.fb.luaparser.Parser;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JumpParserTest {
    @Test
    public void testConsume() {
        JumpParser jp = JumpParser.consume(CharParser.get('\n'));
        String input = "line1\nline2\nline3\n";
        Parser.Result<List<String>> results = jp.parseMany0(input, 0);
        assertEquals("line1\n", results.result().getFirst());
        assertEquals("line2\n", results.result().get(1));
        assertEquals("line3\n", results.result().get(2));
        assertEquals(3, results.result().size());
    }

    @Test
    public void testNoConsume() {
        JumpParser jp = JumpParser.of(CharParser.get('\n'));
        String input = "line1\nline2\nline3\n";
        Parser.Result<String> result = jp.parse(input, 0);
        Parser.Result<Character> newLine = CharParser.get('\n').parse(input, result.position());
        assertEquals("line1", result.result());
        assertEquals('\n', newLine.result().charValue());
    }
}
