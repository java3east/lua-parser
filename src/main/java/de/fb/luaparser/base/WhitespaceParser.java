package de.fb.luaparser.base;

import de.fb.luaparser.BaseParseException;
import de.fb.luaparser.Parser;

import java.util.List;

public class WhitespaceParser extends Parser<String> {
    public static final WhitespaceParser INSTANCE = new WhitespaceParser();

    private WhitespaceParser() {}

    @Override
    public Result<String> parse(String input, int position) throws BaseParseException {
        Result<List<Character>> chars = CharParser.anyOf(' ', '\t', '\n', '\r').parseMany1(input, position);
        StringBuilder sb = new StringBuilder();
        for (Character c : chars.result()) {
            sb.append(c);
        }
        return new Result<>(sb.toString(), chars.position());
    }
}
