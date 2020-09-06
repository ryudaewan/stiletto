package kr.pe.ryudaewan.stiletto.util.entity;

import java.util.List;

/**
 * Created by ryudaewan on 2017-06-26.
 */
public class TableMetaData {
    private String tableNm;
    private String comment;
    private List<ColMetaData> colMetaDatas;

    public String getTableNm() {
        return tableNm;
    }

    public void setTableNm(String tableNm) {
        this.tableNm = tableNm;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<ColMetaData> getColMetaDatas() {
        return colMetaDatas;
    }

    public void setColMetaDatas(List<ColMetaData> colMetaDatas) {
        this.colMetaDatas = colMetaDatas;
    }

    @Override
    public String toString() {
        return "TableMetaData{" +
                "tableNm='" + tableNm + '\'' +
                ", comment='" + comment + '\'' +
                ", colMetaDatas=" + colMetaDatas +
                '}';
    }
}