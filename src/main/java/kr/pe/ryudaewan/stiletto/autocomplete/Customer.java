package kr.pe.ryudaewan.stiletto.autocomplete;

/**
 * Created by ryudaewan on 2017-05-17.
 */
public class Customer {
    private String custNm;
    private int id;
    private String langCd;
    private double jwVal;
    private String keyword;

    public double getJwVal() {
        return jwVal;
    }

    public void setJwVal(double jwVal) {
        this.jwVal = jwVal;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCustNm() {
        return custNm;
    }

    public void setCustNm(String custNm) {
        this.custNm = custNm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }
}
