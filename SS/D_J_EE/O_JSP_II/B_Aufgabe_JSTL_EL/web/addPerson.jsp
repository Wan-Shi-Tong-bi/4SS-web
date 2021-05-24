<%-- 
    Document   : addPerson
    Created on : 27-Apr-2021, 11:24:16
    Author     : jerem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Person hinzufuegen</title>
        <link rel="stylesheet" href="bootstrap.css">
    </head>
    <body>
        <div class="container-fluid">
            <h1>Neue Person hinzufuegen</h1>
            <form action="NewServlet" method="POST">
                <div class="form-group">
                <label for="id">ID</label>
                <input class="form-control" type="text" name="id" id="id">
                </div>

                <div class="form-group">
                <label for="vorname">Vorname</label>
                <input class="form-control" type="text" name="vorname" id="vorname">
                </div>

                <div class="form-group">
                <label for="id">Nachname</label>
                <input class="form-control" type="text" name="nachname" id="nachname">
                </div>

                <div class="form-group" >
                <label for="svnr">SV-Nummer</label>
                <input class="form-control" type="text" name="svnr" id="svnr">
                <div>

                <input style="margin-top: 20px" class="btn btn-primary" type="submit" value="Entsenden">
            </form>
        </div>
    </body>
</html>
