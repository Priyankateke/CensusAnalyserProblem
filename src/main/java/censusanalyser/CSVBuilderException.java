package censusanalyser;

public class CSVBuilderException extends Exception {

    enum ExceptionType {
        UNABLE_TO_PARSE
    }

    CSVBuilderException.ExceptionType type;

    public CSVBuilderException(String message, CSVBuilderException.ExceptionType type) {
        super(message);
        this.type = type;
    }
}
