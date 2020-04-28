package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.BeforeClass;
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

    private static final String US_CENSUS_CSV_FILE_PATH = "src/test/resources/USCensusData.csv";

    static CensusAnalyser censusAnalyser;

    @BeforeClass
    public static void beforeClass() {
        censusAnalyser = new CensusAnalyser();
    }

    /** Given India Census CSV File Should Return Exact Count*/
    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    /** Given India Census CSV File is Correct But Wrong File Path Should Throw The Exception*/
    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM, e.type);
        }
    }

    /** Given the Indian Census CSV File when correct
   but type incorrect Returns a custom Exception */
    @Test
    public void givenIndianCensusData_WhenIncorrectType_ShouldThrowException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        }
    }

    /** Given the State Census CSV File when correct but
    delimiter incorrect Returns a custom Exception */
    @Test
    public void givenIndianCensusCSVFile_WhenIncorrectDelimiter_ShouldThrowException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_WITH_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
   }

   /** Given the State Census CSV File when correct but
     csv header incorrect Returns a custom Exception */
    @Test
    public void givenIndianCensusCSVFile_WhenIncorrectHeader_ShouldThrowException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_WITH_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
    }

    /** Given the State Code CSV File is Correct But incorrect File Path Should throw Exception */
    @Test
    public void givenIndiaStateCodeData_WhenWrongFilePath_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIAN_STATE_CODE_CSV_WRONG_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        }
    }

    /** Given Indian State Code CSV File is Correct But incorrect File Type(extension) Should Throw the exception */
    @Test
    public void givenIndianStateCodeCensusCsvFile_WhenIncorrectType_ShouldThrowException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIAN_STATE_CODE_CSV_WRONG_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM,e.type);
        }
    }

    /** Given the State Code CSV File when correct but
    delimiter incorrect Returns a custom Exception */
    @Test
    public void givenStateCodeCSVFile_WhenIncorrectDelimiter_ShouldThrowException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIAN_STATE_CODE_CSV_FILE_WITH_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
    }

    /** Given the State Code CSV File when correct but
     csv header incorrect Returns a custom Exception */
    @Test
    public void givenStateCodeCSVFile_WhenIncorrectHeader_ShouldThrowException() {
        try {
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIAN_STATE_CODE_CSV_FILE_WITH_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_TEMPLATE_PROBLEM,e.type);
        }
    }

    /** Given Indian Census Data When Sorted On States Should Return Start State */
    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnStartResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATE);
        CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
        Assert.assertEquals("Andhra Pradesh", censusCSV[censusCSV.length - 1].state);
    }

    /** Given India Census Data when sorted on States should return end State */
    @Test
    public void givenIndiaCensusData_whenSortedOnStates_shouldReturnSortedDataEndState() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATE);
        CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
        Assert.assertEquals("West Bengal", censusCSV[0].state);
    }

    /** Given Indian Census Data When Sorted On State Code Should Return Start state Code */
    @Test
    public void givenIndianCensusData_WhenSortedOnStateCode_ShouldReturnStartResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATECODE);
        CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
        Assert.assertEquals("AP", censusCSV[censusCSV.length - 1].stateCode);
    }

    /** Given Indian Census Data When Sorted On State Code Should Return End State Code */
    @Test
    public void givenIndianCensusData_WhenSortedOnStateCode_ShouldReturnEndResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATECODE);
        CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
        Assert.assertEquals("WB", censusCSV[0].stateCode);
    }

    /** Given Indian Census Data When Sorted On Population Should Return Sorted Result*/
    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATION);
        CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
        Assert.assertEquals(199812341, censusCSV[0].population, 0.0);
    }

    /** given Indian Census Data When Sorted On PopulationDensity Should Return Sorted Result */
    @Test
    public void givenIndianCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATIONSDENSITY);
        CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
        Assert.assertEquals(1102, censusCSV[0].densityPerSqKm, 0.0);
    }

    /** given Indian Census Data When Sorted On Total area Should Return Sorted Result */
    @Test
    public void givenIndianCensusData_WhenSortedOnTotalArea_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.TOTALAREA);
        CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
        Assert.assertEquals(342239, censusCSV[0].totalArea, 0.0);
    }

    /** given US Census Data Should Return Exact Record */
    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecord() {
        int censusDataCount = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(51, censusDataCount);
    }

    /** given US Census Data When Sorted On state Should Return Sorted Result */
    @Test
    public void givenUSCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATE);
        CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
        Assert.assertEquals("Wyoming", censusCSV[0].state);
    }

    /** given US Census Data When Sorted On Population Should Return Sorted Result */
    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATION);
        CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
        Assert.assertEquals(3.7253956E7, censusCSV[0].population, 0.0);
    }

    /** given US Census Data When Sorted On PopulationDensity Should Return Sorted Result */
    @Test
    public void givenUSCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATIONSDENSITY);
        System.out.println(sortedCensusData);
        CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
        Assert.assertEquals(3805.61, censusCSV[0].densityPerSqKm, 0.00);
    }

    /** given US Census Data When Sorted On Total Area Should Return Sorted Result */
    @Test
    public void givenUSCensusData_WhenSortedOnTotalArea_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.TOTALAREA);
        System.out.println(sortedCensusData);
        CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
        Assert.assertEquals(1723338.01, censusCSV[0].totalArea, 0.0);
    }

    /** given US Census Data When Sorted On State Code Should Return Sorted Result */
    @Test
    public void givenUSCensusData_WhenSortedOnStateCode_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATECODE);
        System.out.println(sortedCensusData);
        CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
        Assert.assertEquals("WY", censusCSV[0].stateCode);
    }

    @Test
    public void givenUSAndIndiaCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATIONSDENSITY);
        CensusDAO[] usCensusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);

        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE_CSV_FILE_PATH);
        String sortedIndiaCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATIONSDENSITY);
        CensusDAO[] indiaCensusCSV = new Gson().fromJson(sortedIndiaCensusData, CensusDAO[].class);

        Assert.assertEquals(true, usCensusCSV[0].population < indiaCensusCSV[0].population);
    }
}