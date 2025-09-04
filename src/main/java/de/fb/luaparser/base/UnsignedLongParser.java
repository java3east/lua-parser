package de.fb.luaparser.base;

import de.fb.luaparser.BaseParseException;
import de.fb.luaparser.Parser;

import java.util.List;

/**
 * Parser for unsigned long integers. Parses a sequence of digits and converts it to an unsigned long.
 * If the number is out range, a BaseParseException is thrown.
 */
public class UnsignedLongParser extends Parser<Long> {
    public static final UnsignedLongParser INSTANCE = new UnsignedLongParser();

    private UnsignedLongParser() {}

    @Override
    public Result<Long> parse(String input, int position) throws BaseParseException {
        AnyParser<Character> digitParser = CharParser.anyOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        Parser.Result<List<Character>> result = digitParser.parseMany1(input, position);
        StringBuilder numberString = new StringBuilder();
        for (Character c : result.result()) {
            numberString.append(c);
        }
        try {
            long number = Long.parseUnsignedLong(numberString.toString());
            return new Result<>(number, result.position());
        } catch (NumberFormatException e) {
            throw new BaseParseException("Number out of range for unsigned long: " + numberString, position);
        }
    }
}
