package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    Map<String, CensusDAO> censusMap;
    List<CensusDAO> censusDAOList;

    public CensusAnalyser() {
        censusMap = new HashMap<>();
        censusDAOList = new ArrayList<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws  CensusAnalyserException {
        return this.loadCensusData(csvFilePath,IndiaCensusCSV.class);
    }

    /* Loading Indian Census Data */
    private <E> int loadCensusData(String csvFilePath, Class<E> censusCSVClass) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {

            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> csvIterable = () -> censusCSVIterator;

            if (censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            else if (censusCSVClass.getName().equals("censusanalyser.UsCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(UsCensusCSV.class::cast)
                        .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }

            censusDAOList = censusMap.values().stream().collect(Collectors.toList());
            return censusMap.size();

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_TEMPLATE_PROBLEM);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /* Loading Indian State Code Data */
    public int loadIndianStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {

            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCodeCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> censusMap.get(csvState.state) != null)
                    .forEach(csvState -> censusMap.get(csvState.state).stateCode = csvState.stateCode);
            return censusMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_TEMPLATE_PROBLEM);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int loadUsCensusData(String csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath,UsCensusCSV.class);
    }

    /* Getting States Wise Sorted Data and And Handling Exception For Given CSV File*/
    public String getStateWiseSortedCensusData() {
        if(censusDAOList.size()==0 || censusDAOList ==null)
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<CensusDAO> indiaCensusDAOComparator = Comparator.comparing(censusMap -> censusMap.state );
        this.sort(indiaCensusDAOComparator);
        String sortedCensusJson = new  Gson().toJson(censusDAOList);
        return sortedCensusJson;
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
