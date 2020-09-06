package kr.pe.ryudaewan.stiletto.util;

import kr.pe.ryudaewan.stiletto.util.entity.ColMetaData;
import kr.pe.ryudaewan.stiletto.util.entity.TableMetaData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.pe.ryudaewan.stiletto.util.Converter.*;

/**
 * Created by ryudaewan on 2017-06-26.
 */
public class JavaGen {
    private String outPath = "./gen/java";
    private List<TableMetaData> tableMetaDatas;
    private Map<String, String> dataTypeMap = new HashMap<>();

    public JavaGen(List<TableMetaData> tableMetaDatas) {
        this.tableMetaDatas = tableMetaDatas;
        dataTypeMap.put("DATE", "Date");
        dataTypeMap.put("NUMBER", "double");
        dataTypeMap.put("VARCHAR2", "String");
        dataTypeMap.put("CHAR", "String");
    }

    private PrintWriter getJavaFile(String tableNm) throws IOException {
        String fileNm = this.outPath + "/" + getClassNm(tableNm) + ".java";
        File javaFile = new File(fileNm);

        if (!javaFile.exists()) javaFile.createNewFile();

        return new PrintWriter(new FileWriter(javaFile));
    }

    private void getOneJava(TableMetaData tbl) throws IOException {
        String tblNm = tbl.getTableNm();
        PrintWriter java = null;
        String comment = "";

        try {
            java = this.getJavaFile(tblNm);

            java.println("package com.cjlogistics.crm;\n");
            java.println("import java.util.Date;\n");
            java.println("public class " + getClassNm(tbl.getTableNm()) + " {");

            List<ColMetaData> cols = tbl.getColMetaDatas();

            for(ColMetaData col: cols) {
                if (null == col.getComments() || "".equals(col.getComments())) {
                    comment = tbl.getTableNm() + "." + col.getColumnNm();
                } else {
                    comment = col.getComments();
                }


                java.println("  // " + comment);
                java.println("  private " + this.dataTypeMap.get(col.getDataType()) + " " + getVarNm(col.getColumnNm()) + ";\n");
            }

            java.println("}");

        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (null != java) java.close();

        }
    }

    public void genJavas() throws IOException {
        for (TableMetaData tbl : this.tableMetaDatas) {
            this.getOneJava(tbl);
        }
    }
}
