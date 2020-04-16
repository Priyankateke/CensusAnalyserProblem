package censusanalyser;

public class CensusAnalyserException extends Throwable{

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM;
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type=type;
    }
}
