package censusanalyser;

import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

    Map<String, CensusDAO> censusMap;
    List<CensusDAO> censusDAOList;

    public CensusAnalyser() {
        censusMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String ...csvFilePath) throws CensusAnalyserException {
        censusMap = new CensusLoader().loadCensusData(IndiaCensusCSV.class, csvFilePath);
        censusDAOList = censusMap.values().stream().collect(Collectors.toList());
        return censusMap.size();
    }

    public int loadUsCensusData(String csvFilePath) {
        censusMap = new CensusLoader().loadCensusData(UsCensusCSV.class, csvFilePath);
        censusDAOList = censusMap.values().stream().collect(Collectors.toList());
        return censusMap.size();
    }

    /* Getting States Wise Sorted Data and And Handling Exception For Given CSV File*/
    public String getStateWiseSortedCensusData(String csvFilePath) {
        if(censusMap == null || censusMap.size()==0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator =Comparator.comparing(census -> census.state);
        censusDAOList =censusMap.values().stream().collect(Collectors.toList());
        this.sort(censusComparator);
        String sortedStateCensusJson=new Gson().toJson(censusDAOList);
        return sortedStateCensusJson;
    }

    /* Getting Population Wise Sorted Data and And Handling Exception For Given CSV File*/
    public String getPopulationWiseSortedCensusData() {
        if(censusDAOList.size()==0 || censusDAOList ==null)
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<CensusDAO> indiaCensusDAOComparator = Comparator.comparing(census -> census.population);
        this.sort(indiaCensusDAOComparator);
        String sortedCensusJson = new Gson().toJson(censusDAOList);
        return sortedCensusJson;
    }

    /* Getting Population Density Wise Sorted Data and And Handling Exception For Given CSV File*/
    public String getDensityWiseSortedCensusData() {
        if(censusDAOList.size()==0 || censusDAOList ==null)
            throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<CensusDAO> indiaCensusDAOComparator = Comparator.comparing(census -> census.populationDensity);
        this.sort(indiaCensusDAOComparator);
        String sortedCensusJson = new Gson().toJson(censusDAOList);
        return sortedCensusJson;
    }

    /* Getting Area Wise Sorted Data and And Handling Exception For Given CSV File*/
    public String getAreaWiseSortedCensusData() {
        if(censusDAOList.size()==0 || censusDAOList ==null)
            throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<CensusDAO> indiaCensusDAOComparator = Comparator.comparing(census -> census.totalArea);
        this.sort(indiaCensusDAOComparator);
        String sortedCensusJson = new Gson().toJson(censusDAOList);
        return sortedCensusJson;
    }

    public String getUsStateSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        if(censusDAOList.size()==0 || censusDAOList==null)
            throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<CensusDAO> censusDaoComparator = Comparator.comparing(census -> census.state);
        this.sort(censusDaoComparator);
        String sortedCensusJson = new Gson().toJson(censusDAOList);
        return sortedCensusJson;
    }

    /* Function To Sort States */
    private void sort(Comparator<CensusDAO> indiaCensusCsvComparator) {
        for (int i = 0; i < censusDAOList.size()-1; i++) {
            for (int j = 0; j < censusDAOList.size()-i-1; j++) {
                CensusDAO census1 = censusDAOList.get(j);
                CensusDAO census2 = censusDAOList.get(j+1);
                if (indiaCensusCsvComparator.compare(census1,census2)>0) {
                    censusDAOList.set(j,census2);
                    censusDAOList.set(j+1,census1);
                }
            }
        }
    }
}
