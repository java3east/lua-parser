package de.fb.luaparser;

/**
 * A LuaParseException should be used to indicate an error during parsing of Lua code.
 * This exception should be thrown by the top-level parser that is exposed to the user of the library,
 * and is usually a wrapper around a {@link BaseParseException} thrown by an internal parser.
 */
public class LuaParseException extends RuntimeException {
    /**
     * Creates a new LuaParseException with the given file name, line, column and message.
     * @param fileName the name of the file where the error occurred
     * @param line the line number where the error occurred
     * @param column the column number where the error occurred in the line
     * @param message the message describing the error
     */
    public LuaParseException(String fileName, int line, int column, String message) {
        super("Error parsing Lua content (@" + fileName + ":" + line + "+column " + column + "): " + message);
    }
}
