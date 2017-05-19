package kr.pe.ryudaewan.stiletto.stringdistance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleDriver;

/**
 * Created by ryudaewan on 2017-05-16.
 *
 * @see https://docs.oracle.com/database/121/ARPLS/u_match.htm
 */
public class Main {
    private String url = "jdbc:oracle:thin:@112.217.239.173:1521:SBORA";
    private String id = "devuser";
    private String password = "coreplus01";
    private String sql = "SELECT CT.NAME,\n" +
            "  UTL_MATCH.JARO_WINKLER(?, ct.name) jw,\n" +
            "  UTL_MATCH.JARO_WINKLER_SIMILARITY(?, ct.name) jws,\n" +
            "  UTL_MATCH.EDIT_DISTANCE(?, ct.name) ed,\n" +
            "  UTL_MATCH.EDIT_DISTANCE_SIMILARITY(?, ct.name) eds\n" +
            "FROM " + id.toUpperCase() + ".COMPANY_NAME ct\n" +
            "ORDER BY JW DESC";
    private String header = "\n\"input\",\"company_nm\",\"jw\",\"jws\",\"ed\",\"eds\"";

    public static void main(String... args) throws SQLException, IOException {
        Main x = new Main();
        String companyNm = "";

        System.out.print("Input Company Name: ");
        x.perform(x.getCompanyNm(System.in));
    }

    public void perform(String companyNm) throws SQLException {
        new OracleDriver();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String line = "";

        try {
            conn = DriverManager.getConnection(url, id, password);
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, companyNm.toUpperCase());
            pstmt.setString(2, companyNm.toUpperCase());
            pstmt.setString(3, companyNm.toUpperCase());
            pstmt.setString(4, companyNm.toUpperCase());
            rs = pstmt.executeQuery();

            System.out.println(header);

            while (rs.next()) {
                line = "\"" + companyNm + "\",\"";
                line += rs.getString(1) + "\",\"";
                line += rs.getDouble(2) * 100 + "\",\"";
                line += rs.getInt(3) + "\",\"";
                line += rs.getInt(4) + "\",\"";
                line += rs.getInt(5) + "\"";
                System.out.println(line);
            }
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            if (null != rs) rs.close();
            if (null != pstmt) pstmt.close();
            if (null != conn) conn.close();
        }
    }

    public String getCompanyNm(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return br.readLine();
    }
}
