package censusanalyser.exception;

public class CSVBuilderException extends RuntimeException {

    public enum ExceptionType {
        UNABLE_TO_PARSE
    }

    ExceptionType type;

    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
