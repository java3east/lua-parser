package de.fb.luaparser.base;

import de.fb.luaparser.BaseParseException;
import de.fb.luaparser.Parser;

import java.util.Optional;

/**
 * A jump parser jumps to the first location where a given parser matches, and returns the input from the original
 * position to that location as a string.
 */
public class JumpParser extends Parser<String> {
    /**
     * Creates a new JumpParser that jumps to the first location where the given parser matches.
     * The returned string ends before the matched part.
     * @param parser the parser to jump to
     * @return the created JumpParser
     */
    public static JumpParser of(Parser<?> parser) {
        return new JumpParser(parser, false);
    }

    /**
     * Creates a new JumpParser that jumps to the first location where the given parser matches.
     * The returned string includes the matched part.
     * @param parser the parser to jump to
     * @return the created JumpParser
     */
    public static JumpParser consume(Parser<?> parser) {
        return new JumpParser(parser, true);
    }

    private final Parser<?> parser;
    private final boolean consume;

    /**
     * Creates a new JumpParser that jumps to the first location where the given parser matches.
     * The consume parameter determines whether the returned string includes the matched part or not.
     * @param parser the parser to jump to
     * @param consume if true, the returned string includes the matched part; if false, it ends before the matched part
     */
    private JumpParser(Parser<?> parser, boolean consume) {
        this.parser = parser;
        this.consume = consume;
    }

    @Override
    public Result<String> parse(String input, final int position) throws BaseParseException {
        int jump = position;
        while (jump < input.length()) {
            Optional<? extends Result<?>> pr = parser.tryParse(input, jump);
            if (pr.isPresent()) {
                int end = consume ? pr.get().position() : jump;
                return new Result<>(input.substring(position, end), end);
            }
            jump++;
        }
        throw new BaseParseException("Could not find target parser", position);
    }
}
