package censusanalyser;

import java.util.Map;

public class UsCensusAdapter extends CensusAdapter {
    public Map<String, CensusDAO> censusMap;

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) {
        censusMap = super.loadCensusData(UsCensusCSV.class, csvFilePath[0]);
        return censusMap;
    }
}
