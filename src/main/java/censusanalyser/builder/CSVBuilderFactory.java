package censusanalyser.builder;

/** For Creating Object Of OpenCSVBuilder*/
public class CSVBuilderFactory {
    public static ICSVBuilder createCSVBuilder() {
        return new OpenCSVBuilder();
    }
}
