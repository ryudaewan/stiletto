package kr.pe.ryudaewan.stiletto.util;

import kr.pe.ryudaewan.stiletto.util.entity.TableMetaData;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ryudaewan on 2017-06-26.
 */
public class Main {
    public static void main(String... args) throws SQLException, IOException{
        List<TableMetaData> tbls = new TableInfoHunter().hunt();
        JspGen jspGen = new JspGen(tbls);
        JavaGen javaGen = new JavaGen(tbls);
        jspGen.genJsps();
        javaGen.genJavas();
    }
}
