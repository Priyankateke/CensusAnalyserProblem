package censusanalyser;

public class CensusAnalyserException extends RuntimeException {

    public enum ExceptionType {
        UNABLE_TO_PARSE, CSV_FILE_PROBLEM, CSV_TEMPLATE_PROBLEM, NO_CENSUS_DATA, INVALID_COUNTRY
    }

   public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type=type;
    }
}
