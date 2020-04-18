package censusanalyser;

public class CensusAnalyserException extends RuntimeException{

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM, CENSUS_TEMPLATE_PROBLEM, STATE_CODE_FILE_PROBLEM;
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type=type;
    }
}
