package censusanalyser;

import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

    public enum Country {INDIA, US}

    Map<String, CensusDAO> censusMap;
    Map<SortField, Comparator<CensusDAO>> sortMap = null;
    List<CensusDAO> censusDTOList;

    public CensusAnalyser() {
        this.sortMap = new HashMap<>();
        this.sortMap.put(SortField.STATE, Comparator.comparing(census -> census.state));
        this.sortMap.put(SortField.POPULATION, Comparator.comparing(census -> census.population));
        this.sortMap.put(SortField.POPULATIONSDENSITY, Comparator.comparing(census -> census.densityPerSqKm));
        this.sortMap.put(SortField.TOTALAREA, Comparator.comparing(census -> census.totalArea));
        this.sortMap.put(SortField.STATECODE, Comparator.comparing(census -> census.stateCode));
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusMap = CensusAdapterFactory.getCensesAdapter(country, csvFilePath);
        return censusMap.size();
    }

    public String getStateWiseSortedCensusData(SortField sortField) {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusDTOList = censusMap.values().stream().collect(Collectors.toList());
        this.sort(this.sortMap.get(sortField).reversed());
        String sortedStateCensusJson = new Gson().toJson(censusDTOList);
        return sortedStateCensusJson;

    }

    /** Sort Function To sort Data*/
    private void sort(Comparator<CensusDAO> censusCSVComparator) {
        for (int i = 0; i < this.censusDTOList.size() - 1; i++) {
            for (int j = 0; j < this.censusDTOList.size() - i - 1; j++) {
                CensusDAO census1 = this.censusDTOList.get(j);
                CensusDAO census2 = this.censusDTOList.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    this.censusDTOList.set(j, census2);
                    this.censusDTOList.set(j + 1, census1);
                }
            }
        }
    }
}
