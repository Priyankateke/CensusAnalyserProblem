package censusanalyser;

public class CSVBuilderException extends Exception {

    enum ExceptionType {
        UNABLE_TO_PARSE
    }

    CSVBuilderException.ExceptionType Etype;

    public CSVBuilderException(String message, CSVBuilderException.ExceptionType Etype) {
        super(message);
        this.Etype = Etype;
    }
}
