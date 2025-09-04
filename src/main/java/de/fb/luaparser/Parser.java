package de.fb.luaparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A template for a parser that can parse a string input and return a result of type T.
 * @param <T> the type the result of the parser will be
 */
public abstract class Parser<T> {
    /**
     * A record to hold the result of a parse operation and the position of the next character to be parsed.
     * @param result the result of the parse operation
     * @param position the position of the next character to be parsed
     * @param <T> the type of the result
     */
    public record Result<T>(T result, int position) { }

    /**
     * Parses the input string starting from the given position and returns a Result object containing the parsed result
     * and the position of the next character to be parsed.
     * @throws BaseParseException if an error occurs during parsing
     * @param input the input string to be parsed
     * @param position the position to start parsing from
     * @return a Result object containing the parsed result and the position of the next character to be parsed
     */
    public abstract Result<T> parse(String input, int position) throws BaseParseException;

    /**
     * Tries to parse the input string starting from the given position and returns an Optional containing the Result object
     * if parsing was successful, or an empty Optional if an error occurred during parsing. This will catch all exceptions,
     * including BaseParseException.
     * @param input the input string to be parsed
     * @param position the position to start parsing from
     * @return an Optional containing the Result object if parsing was successful, or an empty Optional if an error
     * occurred during parsing
     */
    public Optional<Result<T>> tryParse(String input, int position) {
        try {
            return Optional.of(parse(input, position));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Tries to parse as many times as possible from the input string starting from the given position and returns a Result
     * object containing a list of parsed results and the position of the next character to be parsed. If atLeastOne
     * is true, it will throw a BaseParseException if no results were parsed.
     * @param input the input string to be parsed
     * @param position the position to start parsing from
     * @param atLeastOne whether to require at least one successful parse
     * @return a Result object containing a list of parsed results and the position of the next character to be parsed
     */
    public Result<List<T>> parseMany(String input, int position, boolean atLeastOne) {
        List<T> results = new ArrayList<>();
        int currentPosition = position;
        while (true) {
            Optional<Result<T>> result = tryParse(input, currentPosition);
            if (result.isPresent()) {
                results.add(result.get().result());
                currentPosition = result.get().position();
            } else {
                break;
            }
        }
        if (atLeastOne && results.isEmpty()) {
            throw new BaseParseException("Expected at least one match", position);
        }
        return new Result<>(results, currentPosition);
    }

    /**
     * Parses as many times as possible from the input string starting from the given position and returns a Result object
     * containing a list of parsed results and the position of the next character to be parsed. This will not throw
     * an exception if no results were parsed. It is equivalent to calling parseMany(input, position, false).
     * @param input the input string to be parsed
     * @param position the position to start parsing from
     * @return a Result object containing a list of parsed results and the position of the next character to be parsed
     */
    public Result<List<T>> parseMany0(String input, int position) {
        return parseMany(input, position, false);
    }

    /**
     * Parses as many times as possible from the input string starting from the given position and returns a Result object
     * containing a list of parsed results and the position of the next character to be parsed. This will throw
     * a BaseParseException if no results were parsed. It is equivalent to calling parseMany(input, position, true).
     * @param input the input string to be parsed
     * @param position the position to start parsing from
     * @return a Result object containing a list of parsed results and the position of the next character to be parsed
     */
    public Result<List<T>> parseMany1(String input, int position) {
        return parseMany(input, position, true);
    }

    /**
     * Parses the entire input string and returns the parsed result. If expectFullParse is true, it will throw a
     * RuntimeException if the entire input string was not consumed during parsing.
     * @param input the input string to be parsed
     * @param expectFullParse whether to expect the entire input string to be consumed during parsing
     * @return the parsed result
     */
    public T parse(String input, boolean expectFullParse) {
        Result<T> result = parse(input, 0);
        if (expectFullParse && result.position != input.length()) {
            throw new RuntimeException("Expected full parse but only parsed up to position " + result.position);
        }
        return result.result;
    }

    /**
     * Parses the entire input string and returns the parsed result. This is equivalent to calling
     * parse(input, true). It will throw a RuntimeException if the entire input string was not consumed
     * during parsing.
     * @param input the input string to be parsed
     * @return the parsed result
     */
    public T parse(String input) {
        return parse(input, true);
    }
}
