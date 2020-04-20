package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder implements ICSVBuilder {
   public  Iterator<ICSVBuilder> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
       try {
           CsvToBeanBuilder<ICSVBuilder> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
           csvToBeanBuilder.withType(csvClass);
           csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
           csvToBeanBuilder.withSeparator(',');
           CsvToBean<ICSVBuilder> csvToBean = csvToBeanBuilder.build();
           return csvToBean.iterator();
       } catch (IllegalStateException e) {
           throw new CSVBuilderException(e.getMessage(),CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
       }
   }
}