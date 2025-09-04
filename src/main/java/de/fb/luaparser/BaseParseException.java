package de.fb.luaparser;

/**
 * A base parse exception should be used to indicate an error during parser in a parser
 * extending the {@link Parser} class. It is meant to be used for internal parsers only,
 * those that are not exposed to the user of the library.
 */
public class BaseParseException extends RuntimeException {
    /**
     * Creates a new BaseParseException with the given message and position.
     * @param message the message to be included in the exception
     * @param position the absolute position in the input where the error occurred
     */
    public BaseParseException(String message, int position) {
        super("Parse error at position " + position + ": " + message);
    }
}
