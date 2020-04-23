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

    Map<String, IndiaCensusDTO> censusMap;
    List<IndiaCensusDTO> indiaCensusDTOList;

    Map<String, IndiaStateCodeDTO> stateCodeMap;
    List<IndiaStateCodeDTO> indiaStateCodeDTOList;

    public CensusAnalyser() {
        censusMap = new HashMap<>();
        stateCodeMap = new HashMap<>();
    }

    /* Loading Indian Census Data */
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
           ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();

            Iterator<IndiaCensusCSV> censusCsvIterator = csvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            while (censusCsvIterator.hasNext()) {
                IndiaCensusCSV indiaCensusCsv = censusCsvIterator.next();
                censusMap.put(indiaCensusCsv.state,new IndiaCensusDTO(indiaCensusCsv));
            }
            indiaCensusDTOList = censusMap.values().stream().collect(Collectors.toList());
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
            while (stateCodeCSVIterator.hasNext()) {
                IndiaStateCodeCSV indiaStateCodeCSV = stateCodeCSVIterator.next();
                stateCodeMap.put(indiaStateCodeCSV.state,new IndiaStateCodeDTO(indiaStateCodeCSV));
            }
            indiaStateCodeDTOList = stateCodeMap.values().stream().collect(Collectors.toList());
            return stateCodeMap.size();
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
    public String getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        if(indiaCensusDTOList.size()==0 || indiaCensusDTOList==null)
            throw new CensusAnalyserException("No Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<IndiaCensusDTO> indiaCensusCsvComparator = Comparator.comparing(censusMap -> censusMap.state );
        this.sort(indiaCensusCsvComparator);
        String sortedCensusJson = new  Gson().toJson(indiaCensusDTOList);
        return sortedCensusJson;
    }

    /* Function To Sort States */
    private void sort(Comparator<IndiaCensusDTO> indiaCensusCsvComparator) {
        for (int i = 0; i < indiaCensusDTOList.size()-1; i++) {
            for (int j=0; j < indiaCensusDTOList.size()-i-1; j++) {
                IndiaCensusDTO census1 = indiaCensusDTOList.get(j);
                IndiaCensusDTO census2 = indiaCensusDTOList.get(j+1);
                if (indiaCensusCsvComparator.compare(census1,census2)>0) {
                    indiaCensusDTOList.set(j,census2);
                    indiaCensusDTOList.set(j+1,census1);
                }
            }
        }
    }
}
