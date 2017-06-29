package kr.pe.ryudaewan.stiletto.util.entity;

/**
 * Created by ryudaewan on 2017-06-26.
 */
public class ColMetaData {
    private String columnNm;
    private String dataType;
    private int length;
    private String comments;

    @Override
    public String toString() {
        return "ColMetaData{" +
                "columnNm='" + columnNm + '\'' +
                ", dataType='" + dataType + '\'' +
                ", length=" + length +
                ", comments='" + comments + '\'' +
                '}';
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getColumnNm() {
        return columnNm;
    }

    public void setColumnNm(String columnNm) {
        this.columnNm = columnNm;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

}
