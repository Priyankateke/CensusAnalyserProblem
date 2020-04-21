package censusanalyser;

public class CensusAnalyserException extends RuntimeException{

    public enum ExceptionType {
        CSV_FILE_PROBLEM, CSV_TEMPLATE_PROBLEM, NO_CENSUS_DATA
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type=type;
    }
}
