package de.fb.luaparser.base;

import de.fb.luaparser.BaseParseException;
import de.fb.luaparser.Parser;

/**
 * A char parser tries to parse the given character from the input string. If the character is found, it returns it as the result.
 * If the character is not found, it throws a BaseParseException. If the end of the input is reached, it also throws a BaseParseException.
 */
public class CharParser extends Parser<Character> {
    /**
     * Creates a new Parser that tries to parse any of the given characters.
     * @param chars the characters to parse
     * @return a new AnyParser that tries to parse any of the given characters
     */
    public static AnyParser<Character> anyOf(char... chars) {
        CharParser[] charParsers = new CharParser[chars.length];
        for (int i = 0; i < chars.length; i++) {
            charParsers[i] = new CharParser(chars[i]);
        }
        return new AnyParser<>(charParsers);
    }

    private final char c;

    /**
     * Creates a new CharParser that tries to parse the given character.
     * @param c the character to parse
     */
    public CharParser(char c) {
        this.c = c;
    }

    @Override
    public Result<Character> parse(String input, int position) throws BaseParseException {
        if (position >= input.length()) {
            throw new BaseParseException("Unexpected end of input, expected '" + c + "'", position);
        }
        if (input.charAt(position) == c) {
            return new Result<>(c, position + 1);
        } else {
            throw new BaseParseException("Expected '" + c + "', found '" + input.charAt(position) + "'", position);
        }
    }
}
