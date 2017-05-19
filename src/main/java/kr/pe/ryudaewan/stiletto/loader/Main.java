package kr.pe.ryudaewan.stiletto.loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by ryudaewan on 2017-05-15.
 */
public class Main {
    private String url = "jdbc:oracle:thin:@112.217.239.173:1521:SBORA";
    private String id = "devuser";
    private String password = "coreplus01";
    private String sql = "insert into devuser.company_name(id, name, nationality) values(" +
            "?, ?, ?)";
    private String fileNm = null;
    private String countryCd = "";
    private int startId = 0;

    public Main(String fileNm, String countryCd, int startId) throws SQLException, FileNotFoundException {
        this.fileNm = fileNm;
        this.countryCd = countryCd;
        this.startId = startId;
    }

    public static void main(String... args) throws IOException, SQLException {
        String fileNm = "comp-nm-kr-00.log";
        String countryCd = "KR";
        int startId = 1650;

        Main main = new Main(fileNm, countryCd, startId);
        System.out.println("\n" + main.perform() + " rows updated.");
    }

    public int perform() throws IOException, SQLException {
        int lineNo = startId;
        int rowsUpdated = 0;
        String companyNm = "";
        String line = "";

        BufferedReader br = null;
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            br = new BufferedReader(new FileReader(this.fileNm));
            connection = DriverManager.getConnection(this.url, this.id, this.password);
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(this.sql);

            while ((line = br.readLine()) != null) {
                companyNm = this.getCompanyNm(line);

                if ("".equals(companyNm)) continue;

                pstmt.setInt(1, lineNo);
                pstmt.setString(2, companyNm);
                pstmt.setString(3, this.countryCd);
                System.out.printf("\"%d\",\"%s\",\"%s\"\n", lineNo, companyNm, this.countryCd);

                rowsUpdated += pstmt.executeUpdate();
                lineNo++;
            }

            connection.commit();
        } catch (IOException ioe) {
            connection.rollback();
            throw ioe;
        } catch (SQLException sqle) {
            connection.rollback();
            throw sqle;
        } finally {
            br.close();
            pstmt.close();
            connection.close();
        }

        return rowsUpdated;
    }

    public String getCompanyNm(String line) {
        String companyNm = "";
        int temp = 0;

        if (!"".equals(line) && !line.startsWith("{{") && !line.startsWith("=")) {
            companyNm = line.substring(3, line.length() - 2);

            for (temp = 0; temp < companyNm.length() && companyNm.charAt(temp) == '['; temp++) ;

            companyNm = companyNm.substring(temp);
            temp = companyNm.indexOf('|');
            if (temp > 0) companyNm = companyNm.substring(temp + 1);

            temp = companyNm.indexOf(']');
            if (temp > 0) companyNm = companyNm.substring(0, temp);

            temp = companyNm.indexOf('(');
            if (temp > 0) companyNm = companyNm.substring(0, temp);
        }

        return companyNm;
    }
}
