package kr.pe.ryudaewan.stiletto.util;

import kr.pe.ryudaewan.stiletto.util.entity.ColMetaData;
import kr.pe.ryudaewan.stiletto.util.entity.TableMetaData;
import oracle.jdbc.driver.OracleDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryudaewan on 2017-06-26.
 */
public class TableInfoHunter {
    private String ip = "112.217.239.173";
    private String port = "1521";
    private String sid = "SBORA";
    private String id = "cjl_kms";
    private String password = "coreplus01";
    private Connection conn;

    public TableInfoHunter() throws SQLException {
        connect();
    }

    private void connect() throws SQLException {
        if (null == this.conn || conn.isClosed()) {
            new OracleDriver();
            String url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid;

            this.conn = DriverManager.getConnection(url, id, password);
        }
    }

    private void disconnect() throws SQLException {
        if (null != this.conn && !conn.isClosed()) {
            conn.close();
        }
    }

    /**
     * DB 테이블에 대한 이름, 코멘트를 획득한다. 컬럼 정보는 획득 안 함.
     *
     * @return
     * @throws SQLException
     */
    private List<TableMetaData> getTNameAndComments() throws SQLException {
        List<TableMetaData> tblInfos = new ArrayList<>();
        TableMetaData tbl = null;

        String sql = "select utc.TABLE_NAME,\n" +
                "  utc.COMMENTS\n" +
                "from user_tab_comments utc\n" +
                "where utc.TABLE_TYPE = 'TABLE'";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                tbl = new TableMetaData();
                tbl.setTableNm(rs.getString("TABLE_NAME"));
                tbl.setComment(rs.getString("COMMENTS"));
                tblInfos.add(tbl);
            }
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            if (null != rs) rs.close();
            if (null != pstmt) pstmt.close();
        }

        return tblInfos;
    }

    /**
     * 특정 테이블의 컬럼 정보를 획득한다.
     * <p>
     * *@param pstmt
     *
     * @param tbl 컬럼 정보를 획득하고자 하는 테이블 정보
     * @return
     * @throws SQLException
     */
    private List<ColMetaData> getColInfosOfOneTable(PreparedStatement pstmt, TableMetaData tbl) throws SQLException {
        ResultSet rs = null;
        ColMetaData col = null;
        List<ColMetaData> cols = new ArrayList<>();

        try {
            pstmt.setString(1, tbl.getTableNm());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                col = new ColMetaData();
                col.setColumnNm(rs.getString("COLUMN_NAME"));
                col.setDataType(rs.getString("DATA_TYPE"));
                col.setLength(rs.getInt("DATA_LENGTH"));
                col.setComments(rs.getString("COMMENTS"));
                cols.add(col);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            rs.close();
        }

        return cols;
    }

    /**
     * @param tables 컬럼 정보를 획득할 테이블들.
     */
    private void getColumnInfos(List<TableMetaData> tables) throws SQLException {
        List<ColMetaData> colsOfTbl = new ArrayList<>();

        String sql = "select\n" +
                "  utc.COLUMN_NAME,\n" +
                "  utc.DATA_TYPE,\n" +
                "  utc.DATA_LENGTH,\n" +
                "  ucc.COMMENTS\n" +
                "from\n" +
                "  user_tab_columns utc,\n" +
                "  user_col_comments ucc\n" +
                "where 1=1 and\n" +
                "  ucc.TABLE_NAME = utc.TABLE_NAME and\n" +
                "  ucc.COLUMN_NAME = utc.COLUMN_NAME and\n" +
                "  utc.TABLE_NAME = ?";
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);

            for (TableMetaData tbl : tables) {
                tbl.setColMetaDatas(this.getColInfosOfOneTable(pstmt, tbl));
            }
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            if (null != pstmt) pstmt.close();
        }
    }

    public List<TableMetaData> hunt() throws SQLException {
        List<TableMetaData> tbls = null;

        try {
            tbls = this.getTNameAndComments();
            this.getColumnInfos(tbls);
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            this.disconnect();
        }

        return tbls;
    }

    public static void main(String... args) throws SQLException {
        List<TableMetaData> tbls = new TableInfoHunter().hunt();

        for (TableMetaData tbl : tbls) {
            System.out.println(tbl);
        }
    }
}
