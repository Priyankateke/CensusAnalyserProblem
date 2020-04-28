package censusanalyser.builder;

import censusanalyser.exception.CSVBuilderException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder implements ICSVBuilder {
    public Iterator<ICSVBuilder> getCSVFileIterator(Reader reader, Class csvClass) {
        return this.getCSVToBean(reader, csvClass).iterator();
    }

    @Override
    public List<ICSVBuilder> getCSVFileList(Reader reader, Class csvClass) {
        return this.getCSVToBean(reader, csvClass).parse();
    }

    /**CSV To Bean */
    private CsvToBean getCSVToBean(Reader reader, Class csvClass) {
       try {
           //start reading csv file
           //convert csv to bean
           CsvToBeanBuilder<ICSVBuilder> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);  //open close principle
           csvToBeanBuilder.withType(csvClass);
           csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true); //ignore space
           CsvToBean<ICSVBuilder> csvToBean = csvToBeanBuilder.build(); //start building
           return csvToBean;
       } catch (IllegalStateException e) {
           throw new CSVBuilderException(e.getMessage(),
                   CSVBuilderException.ExceptionType.UNABLE_TO_PARSE); //if file not read or found
       }
    }
}