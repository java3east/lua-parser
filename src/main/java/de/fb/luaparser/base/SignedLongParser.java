package de.fb.luaparser.base;

import de.fb.luaparser.BaseParseException;
import de.fb.luaparser.Parser;

import java.util.Optional;

public class SignedLongParser extends Parser<Long> {
    public static final SignedLongParser INSTANCE = new SignedLongParser();

    private SignedLongParser() {}

    @Override
    public Result<Long> parse(String input, int position) throws BaseParseException {
        AnyParser<Character> signParser = CharParser.anyOf('+', '-');
        Parser<Long> unsignedParser = UnsignedLongParser.INSTANCE;

        Optional<Result<Character>> signResult = signParser.tryParse(input, position);
        if (signResult.isPresent())
            position = signResult.get().position();

        Result<Long> unsigned = unsignedParser.parse(input, position);
        long number = unsigned.result();
        if (signResult.isPresent() && signResult.get().result() == '-') {
            number = -number;
        }
        return new Result<>(number, unsigned.position());
    }
}
