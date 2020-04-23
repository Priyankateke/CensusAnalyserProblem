package censusanalyser;

public class IndiaStateCodeDTO {

    public int srNo;
    public String stateInStateCode;
    public int tin;
    public String stateCode;

    public IndiaStateCodeDTO(IndiaStateCodeCSV indiaStateCodeCSV) {
        srNo=indiaStateCodeCSV.srNo;
        stateInStateCode=indiaStateCodeCSV.state;
        tin=indiaStateCodeCSV.tin;
        stateCode=indiaStateCodeCSV.stateCode;
    }
    public IndiaStateCodeDTO() {

    }
}
