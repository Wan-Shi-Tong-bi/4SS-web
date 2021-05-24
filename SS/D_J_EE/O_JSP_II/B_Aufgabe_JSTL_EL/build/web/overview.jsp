<%-- 
    Document   : overview
    Created on : 27-Apr-2021, 11:23:56
    Author     : jerem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Personen Ansicht</title>
        <link rel="stylesheet" href="bootstrap.css">
    </head>
    <body>
        <div class="container-fluid">
        <h1>Ansicht der Tabelle Person:</h1>
        <form class="form-inline" action="addPerson.jsp">
            <input style="margin: 20px 0px" class="btn btn-primary" type="submit" value="Hinzufuegen">
        </form>
        
        <sql:setDataSource var="db" driver="org.apache.derby.jdbc.ClientDriver"
                           url="jdbc:derby://localhost:1527/B_Uebung_JSTL_EL"
                           user="db" password="db"/>
        <sql:query dataSource="${db}" var="rs">
            SELECT * from personen
        </sql:query>
        
            <table class="table" width="100%">
                <tr>
                    <th>ID</th>
                    <th>Vorname</th>
                    <th>Nachname</th>
                    <th>SV-Nummer</th>
                </tr>
                <c:forEach var="table" items="${rs.rows}">
                    <tr>
                        <td><c:out value="${table.id}"/></td>
                        <td><c:out value="${table.vorname}"/></td>
                        <td><c:out value="${table.nachname}"/></td>
                        <td><c:out value="${table.sb_nummer}"/></td>
                    </tr>
                </c:forEach>
            </table>
            </div>
    </body>
</html>
