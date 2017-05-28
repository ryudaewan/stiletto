<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.util.Properties" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="en"><head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
    .mytable { border-collapse:collapse; }
    .mytable th, .mytable td { border:1px solid black; }
</style>
<title>DB 연결 시험</title></head><body><%
    Context cxt = new InitialContext();
    DataSource ds = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/stiletto" );
        conn = ds.getConnection();
        pstmt = conn.prepareStatement("SELECT 'SUCCESS' FROM DUAL");
        rs = pstmt.executeQuery();

        while (rs.next()) { %>
            <p><h1><%= rs.getString(1) %></p></h1>
<%      }
    } catch (SQLException sqle) {
        sqle.printStackTrace();
    } finally {
        if (null != rs) rs.close();
        if (null != pstmt) pstmt.close();
        if (null != conn) conn.close();
    }

    Properties props = System.getProperties(); %>
    <p><table class="mytable"><tr><th>key</th><th>property value</th></tr><%

    for (String key: props.stringPropertyNames()) { %>
        <tr><td><%= key %></td><td><%= props.getProperty(key) %></td></tr><%
    } %></table></p>
</body></html>