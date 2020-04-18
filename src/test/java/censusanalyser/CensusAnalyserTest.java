package censusanalyser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH="./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_FILE_TYPE="./src/test/resources/IndiaStateCensusData.json";
    private static final String INDIA_CENSUS_CSV_FILE_WITH_WRONG_DELIMITER="./src/test/resources/IndiaStateCensusDataWithWrongDelimiter.csv";
    private static final String INDIAN_STATE_CODE_CSV_FILE_PATH="./src/test/resources/IndiaStateCodeData.csv";
    private static final String INDIAN_STATE_CODE_CSV_WRONG_FILE_PATH="./src/main/resources/IndiaStateCodeData.csv";
    private static final String INDIAN_STATE_CODE_CSV_WRONG_FILE_TYPE="./src/test/resources/IndiaStateCodeData.json";
    private static final String INDIAN_STATE_CODE_CSV_FILE_WITH_WRONG_DELIMITER="./src/test/resources/IndianStateCodeDataWithWrongDelimiter.csv";

    CensusAnalyser censusAnalyser;

    @Before
    public void setUp() {
        censusAnalyser = new CensusAnalyser();
    }

    /* TC 1.1 : Given the States Census CSV file, Check to ensure the Number of Record matches */
    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    /* TC 1.2 : Given the State Census CSV File if incorrect Returns a custom Exception */
    @Test
    public void givenIndiaCensusData_WhenWrongFilePath_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    /* TC 1.3 : Given the State Census CSV File when correct
    but type incorrect Returns a custom Exception */
    @Test
    public void givenStateCensusCsvFile_WhenIncorrectType_ShouldThrowException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    /* TC 1.4 : Given the State Census CSV File when correct but
     delimiter incorrect Returns a custom Exception */
    @Test
    public void givenStateCensusCSVFile_WhenIncorrectDelimiter_ShouldThrowException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_WITH_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_TEMPLATE_PROBLEM,e.type);
        }
    }

    /* TC 1.5 : Given the State Census CSV File when correct but
     csv header incorrect Returns a custom Exception*/
    @Test
    public void givenStateCensusCSVFile_WhenIncorrectHeader_ShouldThrowException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_WITH_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_TEMPLATE_PROBLEM,e.type);
        }
    }

    /* TC 2.1 : Given the States Code CSV file, Check to ensure the Number of Record matches */
    @Test
    public void givenIndianStateCodeCSV_ShouldReturnExactCount() {
        try {
            int numOfRecords = censusAnalyser.loadIndianStateCodeData(INDIAN_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    /* TC 2.2 : Given the State Code CSV File if incorrect Returns a custom Exception */
    @Test
    public void givenIndiaStateCodeData_WhenWrongFilePath_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCodeData(INDIAN_STATE_CODE_CSV_WRONG_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.STATE_CODE_FILE_PROBLEM,e.type);
        }
    }

    /* TC 2.3 : Given the State Code CSV File when correct
    but type incorrect Returns a custom Exception */
    @Test
    public void givenStateCodeCsvFile_WhenIncorrectType_ShouldThrowException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCodeData(INDIAN_STATE_CODE_CSV_WRONG_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.STATE_CODE_FILE_PROBLEM,e.type);
        }
    }

    /* TC 2.4 : Given the State Code CSV File when correct but
     delimiter incorrect Returns a custom Exception */
    @Test
    public void givenStateCodeCSVFile_WhenIncorrectDelimiter_ShouldThrowException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCodeData(INDIAN_STATE_CODE_CSV_FILE_WITH_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.STATE_CODE_TEMPLATE_PROBLEM,e.type);
        }
    }

    /* TC 2.5 : Given the State Code CSV File when correct but
     csv header incorrect Returns a custom Exception*/
    @Test
    public void givenStateCodeCSVFile_WhenIncorrectHeader_ShouldThrowException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCodeData(INDIAN_STATE_CODE_CSV_FILE_WITH_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.STATE_CODE_TEMPLATE_PROBLEM,e.type);
        }
    }
}
