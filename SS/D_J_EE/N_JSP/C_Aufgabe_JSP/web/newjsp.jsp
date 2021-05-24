<%-- 
    Document   : newjsp
    Created on : 20-Apr-2021, 09:05:38
    Author     : jerem
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>ToDoListe</h1>
        <form method="post" action="NewServlet">
            <input type ="text" name="item">
            <input type ="submit" value="Hinzufügen">
            <% List<String> items = (List<String>)session.getAttribute("items");
                if (items.isEmpty()) {
            %>
            <h2>kein Eintrag</h2>
            <%} else {%>
            <h2>Auf die Liste:</h2>
            <table>
                <% for(String item : items) {%>
                <tr><td><%=item%></td></tr>
                <%}%>
            </table>
            <%}%>
        </form>
    </body>
</html>
