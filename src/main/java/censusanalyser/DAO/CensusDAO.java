package censusanalyser.DAO;

import censusanalyser.DTO.IndiaCensusCSV;
import censusanalyser.DTO.UsCensusCSV;

public class CensusDAO {

    public String state;
    public String stateCode;
    public double population;
    public double totalArea;
    public double densityPerSqKm;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state=indiaCensusCSV.state;
        population=indiaCensusCSV.population;
        totalArea=indiaCensusCSV.totalArea;
        densityPerSqKm=indiaCensusCSV.densityPerSqKm;
    }

    public CensusDAO(UsCensusCSV UsCensusCSV) {
        state = UsCensusCSV.state;
        stateCode=UsCensusCSV.stateCode;
        population = UsCensusCSV.population;
        totalArea = UsCensusCSV.totalArea;
        densityPerSqKm=UsCensusCSV.densityPerSqKm;
    }
}
