<%-- 
    Document   : newjsp
    Created on : 27-Apr-2021, 11:08:26
    Author     : jerem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ToDoListe</title>
    </head>
    <body>
        <h1>ToDoListe</h1>
        <form method="post" action="NewServlet">
            <input type ="text" name="item">
            <input type ="submit" value="Hinzufügen">
            <c:choose>
                <c:when test="${empty items}">
                    <h2>kein Eintrag</h2>
                </c:when>
                <c:otherwise>
                    <h2>Auf die Liste:</h2>
                    <table>
                        <c:forEach items="${items}" var="it">
                            <tr><td> ${it} </td></tr>  
                        </c:forEach>                           
                    </table>
                </c:otherwise>
            </c:choose>
        </form>
    </body>
</html>
