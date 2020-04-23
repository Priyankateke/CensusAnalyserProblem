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

    Map<String, IndiaCensusDAO> censusMap;
    List<IndiaCensusDAO> indiaCensusDAOList;

    public CensusAnalyser() {
        censusMap = new HashMap<>();
    }

    /* Loading Indian Census Data */
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {

            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCsvIterator = csvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);

            while (censusCsvIterator.hasNext()) {
                IndiaCensusCSV indiaCensusCsv = censusCsvIterator.next();
                censusMap.put(indiaCensusCsv.state,new IndiaCensusDAO(indiaCensusCsv));
            }
            indiaCensusDAOList = censusMap.values().stream().collect(Collectors.toList());
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

    /* Function To get number of entries in csv file*/
    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numberOfEnteries =(int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return numberOfEnteries;
    }

    /* Getting States Wise Sorted Data and And Handling Exception For Given CSV File*/
    public String getStateWiseSortedCensusData() {
        if(indiaCensusDAOList.size()==0 || indiaCensusDAOList ==null)
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<IndiaCensusDAO> indiaCensusCsvComparator = Comparator.comparing(censusMap -> censusMap.state );
        this.sort(indiaCensusCsvComparator);
        String sortedCensusJson = new  Gson().toJson(indiaCensusDAOList);
        return sortedCensusJson;
    }

    /* Getting Population Wise Sorted Data and And Handling Exception For Given CSV File*/
    public String getPopulationWiseSortedCensusData() {
        if(indiaCensusDAOList.size()==0 || indiaCensusDAOList==null)
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<IndiaCensusDAO> indiaCensusDaoComparator = Comparator.comparing(census -> census.population);
        this.sort(indiaCensusDaoComparator);
        String sortedCensusJson = new Gson().toJson(indiaCensusDAOList);
        return sortedCensusJson;
    }

    /* Getting Population Density Wise Sorted Data and And Handling Exception For Given CSV File*/
    public String getDensityWiseSortedCensusData() {
        if(indiaCensusDAOList.size()==0 || indiaCensusDAOList==null)
            throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<IndiaCensusDAO> indiaCensusDaoComparator = Comparator.comparing(census -> census.densityPerSqKm);
        this.sort(indiaCensusDaoComparator);
        String sortedCensusJson = new Gson().toJson(indiaCensusDAOList);
        return sortedCensusJson;

    }

    /* Function To Sort States */
    private void sort(Comparator<IndiaCensusDAO> indiaCensusCsvComparator) {
        for (int i = 0; i < indiaCensusDAOList.size()-1; i++) {
            for (int j = 0; j < indiaCensusDAOList.size()-i-1; j++) {
                IndiaCensusDAO census1 = indiaCensusDAOList.get(j);
                IndiaCensusDAO census2 = indiaCensusDAOList.get(j+1);
                if (indiaCensusCsvComparator.compare(census1,census2)>0) {
                    indiaCensusDAOList.set(j,census2);
                    indiaCensusDAOList.set(j+1,census1);
                }
            }
        }
    }
}
