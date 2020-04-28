package censusanalyser.adapter;

import censusanalyser.DAO.CensusDAO;
import censusanalyser.DTO.IndiaCensusCSV;
import censusanalyser.DTO.IndiaStateCodeCSV;
import censusanalyser.builder.CSVBuilderFactory;
import censusanalyser.builder.ICSVBuilder;
import censusanalyser.exception.CensusAnalyserException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IndianCensusAdapter extends CensusAdapter {

    public Map<String, CensusDAO> censusMap;
    List<CensusDAO> censusDAOList;

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) {
        censusMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        this.loadIndianStateCodeData(csvFilePath[1]);
        return censusMap;
    }

    public int loadIndianStateCodeData(String csvFilePath) throws
            CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();

            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCodeCSVIterator;

            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> this.censusMap.get(csvState.state) != null)
                    .forEach(csvState -> this.censusMap.get(csvState.state).stateCode = csvState.stateCode);
            censusDAOList = this.censusMap.values().stream().collect(Collectors.toList());

            return this.censusMap.size();

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_TEMPLATE_PROBLEM);
        }
    }
}
