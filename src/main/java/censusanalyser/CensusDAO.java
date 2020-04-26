package censusanalyser;

public class CensusDAO {

    public String state;
    public double population;
    public double totalArea;
    public String stateCode;
    public double populationDensity;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state=indiaCensusCSV.state;
        population=indiaCensusCSV.population;
        totalArea=indiaCensusCSV.areaInSqKm;
        populationDensity=indiaCensusCSV.densityPerSqKm;
    }

    public CensusDAO(UsCensusCSV UsCensusCSV) {
        state = UsCensusCSV.state;
        stateCode=UsCensusCSV.stateId;
        population = UsCensusCSV.population;
        totalArea = UsCensusCSV.totalArea;
        populationDensity=UsCensusCSV.populationDensity;
    }
}
