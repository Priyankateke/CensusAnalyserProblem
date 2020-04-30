package censusanalyser.DTO;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV {
    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public double population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public double totalArea;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public double densityPerSqKm;
}
