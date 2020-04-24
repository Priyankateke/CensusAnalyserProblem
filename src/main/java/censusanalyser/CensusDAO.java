package censusanalyser;

public class CensusDAO {

    public int tin;
    public String state;
    public double population;
    public double totalArea;
    public double densityPerSqKm;
    public String stateCode;
    public double populationDensity;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state=indiaCensusCSV.state;
        population=indiaCensusCSV.population;
        totalArea=indiaCensusCSV.areaInSqKm;
        //densityPerSqKm=indiaCensusCSV.densityPerSqKm;
        populationDensity=indiaCensusCSV.densityPerSqKm;
    }

     public CensusDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        state=indiaStateCodeCSV.state;
        tin=indiaStateCodeCSV.tin;
        stateCode=indiaStateCodeCSV.stateCode;
    }

    public CensusDAO(UsCensusCSV UsCensusCSV) {
        state = UsCensusCSV.state;
        stateCode=UsCensusCSV.stateId;
        population = UsCensusCSV.population;
        totalArea = UsCensusCSV.totalArea;
        densityPerSqKm = UsCensusCSV.totalArea;
        populationDensity=UsCensusCSV.populationDensity;
    }
}
