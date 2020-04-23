package censusanalyser;

public class IndiaCensusDTO {
    public String state;
    public String stateCode;
    public long population;
    public long areaInSqKm;
    public long densityPerSqKm;

    public int tin;

    public IndiaCensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state=indiaCensusCSV.state;
        population=indiaCensusCSV.population;
        areaInSqKm=indiaCensusCSV.areaInSqKm;
        densityPerSqKm=indiaCensusCSV.densityPerSqKm;
    }

    public IndiaCensusDTO(IndiaStateCodeCSV indiaStateCodeCSV) {
        state=indiaStateCodeCSV.state;
        tin=indiaStateCodeCSV.tin;
        stateCode=indiaStateCodeCSV.stateCode;
    }
}
