package de.fb.luaparser.base;

import de.fb.luaparser.BaseParseException;
import de.fb.luaparser.Parser;

import java.util.HashMap;

/**
 * A char parser tries to parse the given character from the input string. If the character is found, it returns it as the result.
 * If the character is not found, it throws a BaseParseException. If the end of the input is reached, it also throws a BaseParseException.
 */
public class CharParser extends Parser<Character> {
    private static final HashMap<Character, CharParser> CACHE = new HashMap<>();

    /**
     * Returns a cached CharParser for the given character, or creates a new one if it doesn't exist yet.
     * @param c the character to parse
     * @return a cached CharParser for the given character
     */
    public static CharParser get(char c) {
        return CACHE.computeIfAbsent(c, CharParser::new);
    }

    /**
     * Parses the given character from the input string at the given position.
     * @throws BaseParseException if the character is not found or the end of the input is reached
     * @param c the character to parse
     * @param input the input string
     * @param position the position to start parsing from
     * @return a Parser.Result containing the parsed character and the new position
     */
    public static Parser.Result<Character> parse(char c, String input, int position) throws BaseParseException {
        return get(c).parse(input, position);
    }

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
    private CharParser(char c) {
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
