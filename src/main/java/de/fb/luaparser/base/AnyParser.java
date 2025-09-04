package de.fb.luaparser.base;

import de.fb.luaparser.BaseParseException;
import de.fb.luaparser.Parser;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * A parser that tries to parse one of the given parsers. If one of them succeeds, it returns the result of that parser.
 * If none of them succeed, it throws a BaseParseException.
 * @param <T> the type the result of the parser will be
 */
public class AnyParser<T> extends Parser<T> {
    private final Parser<T>[] parsers;

    /**
     * Creates a new AnyParser with the given parsers.
     * @param parsers the parsers to try
     */
    @SafeVarargs
    public AnyParser(final @NotNull Parser<T> ...parsers) {
        this.parsers = parsers;
    }

    @Override
    public Result<T> parse(String input, int position) throws BaseParseException {
        for (Parser<T> parser : parsers) {
            Optional<Result<T>> result = parser.tryParse(input, position);
            if (result.isPresent()) {
                return result.get();
            }
        }
        throw new BaseParseException("No parser matched", position);
    }
}
