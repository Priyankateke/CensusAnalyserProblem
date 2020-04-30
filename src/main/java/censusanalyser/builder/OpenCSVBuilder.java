package censusanalyser.builder;

import censusanalyser.exception.CSVBuilderException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder implements ICSVBuilder {
    public Iterator<ICSVBuilder> getCSVFileIterator(Reader reader, Class csvClass) {
        return this.getCSVToBean(reader, csvClass).iterator();
    }

    /**CSV To Bean */
    private CsvToBean getCSVToBean(Reader reader, Class csvClass) {
       try {
           CsvToBeanBuilder<ICSVBuilder> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
           csvToBeanBuilder.withType(csvClass);
           csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
           CsvToBean<ICSVBuilder> csvToBean = csvToBeanBuilder.build();
           return csvToBean;
       } catch (IllegalStateException e) {
           throw new CSVBuilderException(e.getMessage(),
                   CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
       }
    }
}