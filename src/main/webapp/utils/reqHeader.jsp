<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en"><head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
    .mytable { border-collapse:collapse; }
    .mytable th, .mytable td { border:1px solid black; }
</style>
<title>HTTP Header Infos</title></head>
</head>
<body>
<p><h1>HTTP Request Header</h1><br/>
<table class="mytable">
    <tr><th>name</th><th>value</th></tr><%
    String headerName = "";
    Enumeration<String> reqHeaderNames = request.getHeaderNames();

    while (reqHeaderNames.hasMoreElements()) {
        headerName = reqHeaderNames.nextElement(); %>
        <tr><td><%= headerName %></td><td><%= request.getHeader(headerName) %></td></tr><%
    } %>

</table></p>
<p><h1>Locales in HTTP Request</h1><br/>
<table class="mytable">
    <tr><th>language</th><th>country</th><th>variance</th></tr><%
    Locale locale = null;
    Enumeration<Locale> locales = request.getLocales();

    while (locales.hasMoreElements()) {
        locale = locales.nextElement(); %>
        <tr><td><%= locale.getLanguage() %></td><td><%= locale.getCountry() %></td>
        <td><%= locale.getVariant() %></td></tr><%
    } %>

</table></p>
</body>
</html>