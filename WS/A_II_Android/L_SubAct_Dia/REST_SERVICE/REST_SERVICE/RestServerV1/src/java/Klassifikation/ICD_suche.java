/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klassifikation;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author mdemelmayr, jhiegelsberger
 */
@Path("ICD/Suche")
public class ICD_suche {

    /**
     * Gibt sämtliche Codes zurück welche in der Beschreibung das gesuchte Wort,
     * Suchwort, enthalten. Pfad:
     * http://localhost:8080/RestServerV1/resources/ICD/Suche/Suchwort/J
     *
     * @param code
     * @return JsonArray
     * @throws SQLException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{Code}/J")
    public synchronized JsonArray ICDbeschrJ(@PathParam("Code") String code) throws SQLException {

        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/ICD", "projektgruppea", "projektgruppea");
        PreparedStatement s = c.prepareStatement("Select CODE, Bezeichnung  FROM KRANKHEITEN WHERE BEZEICHNUNG LIKE '%" + code + "%'");
        JsonArrayBuilder ab = Json.createArrayBuilder();
        ResultSet rs = s.executeQuery();
        boolean found = false;
        while (rs.next()) {
            String CODE = rs.getString(1);
            String beschreibung = rs.getString(2);
            found = true;
            ab.add(CODE);
            ab.add(beschreibung);
        }
        if (!found) {
            ab.add("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!");
        }
        return ab.build();
    }

    /**
     * Gibt sämtliche Codes zurück welche in der Beschreibung das gesuchte Wort,
     * Suchwort, enthalten. Pfad:
     * http://localhost:8080/RestServerV1/resources/ICD/Suche/Suchwort/X
     *
     * @param code
     * @return XMLfile
     * @throws SQLException
     * @throws FileNotFoundException
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/{Code}/X")
    public synchronized File ICDbeschrXML(@PathParam("Code") String code) throws SQLException, FileNotFoundException {
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/ICD", "projektgruppea", "projektgruppea");
        PreparedStatement s = c.prepareStatement("SELECT CODE, Bezeichnung FROM KRANKHEITEN WHERE BEZEICHNUNG LIKE '%" + code + "%'");
      
        ResultSet rs = s.executeQuery();
        boolean found = false;

        File file = new File("xml.xml");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        XMLEncoder e = new XMLEncoder(bos);
        while (rs.next()) {
            String CODE = rs.getString(1);
            String beschreibung = rs.getString(2);
            e.writeObject("Code: " + CODE);
            e.writeObject("Beschreibung: " + beschreibung);
            found = true;
        }
        if (!found) {
            e.writeObject("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!");
        }
        e.close();
        return file;
    }
}
