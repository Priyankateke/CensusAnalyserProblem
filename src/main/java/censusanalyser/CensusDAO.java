package censusanalyser;

public class IndiaCensusDAO {
    public String state;
    public String stateCode;
    public long population;
    public long areaInSqKm;
    public long densityPerSqKm;

    public int tin;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state=indiaCensusCSV.state;
        population=indiaCensusCSV.population;
        areaInSqKm=indiaCensusCSV.areaInSqKm;
        densityPerSqKm=indiaCensusCSV.densityPerSqKm;
    }

  /*  public IndiaCensusDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        state=indiaStateCodeCSV.state;
        tin=indiaStateCodeCSV.tin;
        stateCode=indiaStateCodeCSV.stateCode;
    }*/
}
