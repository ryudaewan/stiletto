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

/**
 * Created by ryudaewan on 2017-06-26.
 */
public class JspGen {
    private String outPath = "./gen/jsp";
    private List<TableMetaData> tableMetaDatas;
    private Map<String, String> dataMap = new HashMap<>();

    public JspGen(List<TableMetaData> tableMetaDatas) {
        this.tableMetaDatas = tableMetaDatas;
        dataMap.put("NUMBER", "number");
    }

    private PrintWriter getJspFile(String tableNm) throws IOException {
        String fileNm = this.outPath + "/" + tableNm + ".jsp";
        File jspFile = new File(fileNm);

        if (!jspFile.exists()) jspFile.createNewFile();

        return new PrintWriter(new FileWriter(jspFile));
    }

    public void genOneJsp(TableMetaData tbl) throws IOException {
        String tblNm = tbl.getTableNm();
        List<ColMetaData> cols = tbl.getColMetaDatas();
        PrintWriter jsp = null;

        try {
            jsp = this.getJspFile(tblNm);

            jsp.println("<%@ page contentType=\"text/html; charset=UTF-8\" %>");
            jsp.println("<html lang=\"<%= session.getAttribute(\"lang\") %>\"><head>");
            jsp.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
            jsp.println("<title></title></head><body>");
            jsp.println("<div id=\"div_" + tbl.getTableNm().toLowerCase() + "\">");
            jsp.println("<form id=\"frm_" + tblNm.toLowerCase() + "\" action=\"\">");

            for (ColMetaData col : cols) {
                if (null == col.getComments() || "".equals(col.getComments())) {
                    jsp.print(tblNm + "." + col.getColumnNm());
                } else {
                    jsp.print(col.getComments());
                }

                jsp.print(" <input type=\"" + this.getInputType( col.getDataType() )+ "\" id=\"" + col.getColumnNm().toLowerCase() + "\" maxlength=\"" + col.getLength() + "\"/>");
                jsp.println("<!--" + tblNm + "." + col.getColumnNm() + "--><br/>");
            }

            jsp.println("<input type=\"submit\" value=\"저장\" id=\"submit_frm_" + tblNm.toLowerCase() + "\"/>");
            jsp.println("<input type=\"reset\"  value=\"초기화\" id=\"reset_frm_" + tblNm.toLowerCase() + "\">");
            jsp.println("</form></div></body></html>");
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (null != jsp) jsp.close();

        }
    }

    public void genJsps() throws IOException {
        for (TableMetaData tbl : this.tableMetaDatas) {
            this.genOneJsp(tbl);
        }
    }

    private String getInputType(String dbType) {
        String htmlType = this.dataMap.get(dbType);

        if (null == htmlType || "".equals(htmlType)) htmlType = "text";

        return htmlType;
    }
}
