package censusanalyser;

/** For Creating Object On OpenCSVBuilder*/
public class CSVBuilderFactory {
    public static ICSVBuilder createCSVBuilder() {
        return new OpenCSVBuilder();
    }
}
