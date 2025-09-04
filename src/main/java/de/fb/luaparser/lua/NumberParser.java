package de.fb.luaparser.lua;

import de.fb.luaparser.BaseParseException;
import de.fb.luaparser.Parser;
import de.fb.luaparser.base.CharParser;
import de.fb.luaparser.base.SignedLongParser;

import java.util.Optional;

/**
 * Parses a number in Lua format, which can be an integer or a floating point number.
 * The number can be in scientific notation as well.
 * See <a href="https://www.lua.org/pil/2.3.html">Lua 5.4 Reference Manual - 3.4.1</a>
 */
public class NumberParser extends Parser<Double> {
    public static final NumberParser INSTANCE = new NumberParser();

    private NumberParser() {}

    @Override
    public Result<Double> parse(String input, int position) throws BaseParseException {
        Optional<Result<Long>> longResult = SignedLongParser.INSTANCE.tryParse(input, position);
        if (longResult.isPresent())
            position = longResult.get().position();

        long resultLong = longResult.map(Result::result).orElse(0L);

        Optional<Result<Character>> dotResult = CharParser.anyOf('.').tryParse(input, position);
        if (longResult.isEmpty() && dotResult.isEmpty())
            throw new BaseParseException("Expected number", position);
        if (dotResult.isEmpty()) {
            return new Result<>((double) resultLong, position);
        }

        Result<Long> decimalResult = SignedLongParser.INSTANCE.parse(input, dotResult.get().position());

        Optional<Result<Character>> eResult = CharParser.anyOf('e', 'E').tryParse(input, decimalResult.position());
        if (eResult.isEmpty()) {
            String numberString = resultLong + "." + decimalResult.result();
            return new Result<>(Double.parseDouble(numberString), decimalResult.position());
        }

        Result<Long> exponentResult = SignedLongParser.INSTANCE.parse(input, eResult.get().position());
        String numberString = resultLong + "." + decimalResult.result() + "e" + exponentResult.result();
        return new Result<>(Double.parseDouble(numberString), exponentResult.position());
    }
}
