package censusanalyser.adapter;

import censusanalyser.DAO.CensusDAO;
import censusanalyser.exception.CensusAnalyserException;
import censusanalyser.service.CensusAnalyser;

import java.util.Map;

public class CensusAdapterFactory {

    public static Map<String, CensusDAO> getCensusAdapter(CensusAnalyser.Country country, String... csvFilePath) {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndianCensusAdapter().loadCensusData(csvFilePath);
        else if (country.equals(CensusAnalyser.Country.US))
            return new UsCensusAdapter().loadCensusData(csvFilePath);
        throw new CensusAnalyserException("Invalid Country",
                CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}
