package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusCSV> censusCSVList = null;
    List<IndiaStateCodeCSV> stateCodeCSVList = null;

    /* Loading Indian Census Data */
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusCSVList = csvBuilder.getCSVFileList(reader,IndiaCensusCSV.class);
            return censusCSVList.size();
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
            stateCodeCSVList = csvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class);
            return stateCodeCSVList.size();
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
        if(censusCSVList.size()==0 || censusCSVList==null)
            throw new CensusAnalyserException("No Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<IndiaCensusCSV> indiaCensusCsvComparator = Comparator.comparing(census -> census.state);
        this.sort(indiaCensusCsvComparator);
        String sortedCensusJson = new Gson().toJson(censusCSVList);
        return sortedCensusJson;
    }

    /* Getting State Code Wise Sorted Data and And Handling Exception For Given CSV File*/
    public String getStateCodeWiseSortedData(String csvFilePath) throws CensusAnalyserException {
        if(stateCodeCSVList.size()==0 ||  stateCodeCSVList==null)
            throw new CensusAnalyserException("No Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<IndiaStateCodeCSV> indiaStateCodeCsvComparator = Comparator.comparing(census -> census.stateCode);
        this.sorting(indiaStateCodeCsvComparator);
        String sortedCensusJson = new Gson().toJson(stateCodeCSVList);
        return sortedCensusJson;
    }

    /* Function To Sort States*/
    private void sort(Comparator<IndiaCensusCSV> indiaCensusCsvComparator) {
        for (int i = 0; i < censusCSVList.size()-1; i++){
            for (int j=0; j < censusCSVList.size()-i-1; j++){
                IndiaCensusCSV census1 = censusCSVList.get(j);
                IndiaCensusCSV census2 = censusCSVList.get(j+1);
                if (indiaCensusCsvComparator.compare(census1,census2)>0){
                    censusCSVList.set(j,census2);
                    censusCSVList.set(j+1,census1);
                }
            }
        }
    }

    /* Function To Sort State Code */
    private void sorting(Comparator<IndiaStateCodeCSV> indiaStateCodeCsvComparator) {
        for (int i = 0; i < stateCodeCSVList.size()-1; i++){
            for (int j=0; j < stateCodeCSVList.size()-i-1; j++){
                IndiaStateCodeCSV census1 = stateCodeCSVList.get(j);
                IndiaStateCodeCSV census2 = stateCodeCSVList.get(j+1);
                if (indiaStateCodeCsvComparator.compare(census1,census2)>0){
                    stateCodeCSVList.set(j, census2);
                    stateCodeCSVList.set(j+1,census1);
                }
            }
        }
    }
}
