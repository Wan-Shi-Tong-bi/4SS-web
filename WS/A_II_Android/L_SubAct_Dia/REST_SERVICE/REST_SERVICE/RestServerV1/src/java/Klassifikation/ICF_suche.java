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
 * @author mdemelmayr
 */
@Path("ICF/Suche")
public class ICF_suche {

    /**
     * Gibt sämtliche Codes zurück welche in der Beschreibung das gesuchte Wort,
     * Suchwort, enthalten.
     * Pfad:http://localhost:8080/RestServerV1/resources/ICF/Suche/Suchwort/J
     *
     * @param code
     * @return JsonArray
     * @throws SQLException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{Code}/J")
    public synchronized JsonArray ICFbeschrJ(@PathParam("Code") String code) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/ICF_DATABASE", "PROJEKTGRUPPE_A", "PROJEKTGRUPPE_A");
        PreparedStatement s = c.prepareStatement("Select CODE, Beschreibung  FROM ICF_B WHERE BESCHREIBUNG LIKE '%" + code + "%'");
        PreparedStatement s1 = c.prepareStatement("Select CODE, Beschreibung  FROM ICF_D WHERE BESCHREIBUNG LIKE '%" + code + "%'");
        PreparedStatement s2 = c.prepareStatement("Select CODE, Beschreibung  FROM ICF_E WHERE BESCHREIBUNG LIKE '%" + code + "%'");
        PreparedStatement s3 = c.prepareStatement("Select CODE, Beschreibung  FROM ICF_S WHERE BESCHREIBUNG LIKE '%" + code + "%'");
        JsonArrayBuilder ab = Json.createArrayBuilder();
        ResultSet rs = s.executeQuery();
        ResultSet rs1 = s1.executeQuery();
        ResultSet rs2 = s2.executeQuery();
        ResultSet rs3 = s3.executeQuery();
        //zur Überprüfung ob ein Ergebnis zurückkam
        boolean found = false;

        while (rs.next()) {
            String CODE = rs.getString(1);
            String beschreibung = rs.getString(2);
            ab.add(beschreibung);
            found = true;
            ab.add(CODE);

        }

        while (rs1.next()) {
            String CODE = rs1.getString(1);
            found = true;
            ab.add(CODE);
            String beschreibung = rs.getString(2);
            ab.add(beschreibung);

        }

        while (rs2.next()) {
            String CODE = rs2.getString(1);
            found = true;
            ab.add(CODE);
            String beschreibung = rs.getString(2);
            ab.add(beschreibung);
        }

        while (rs3.next()) {
            String CODE = rs3.getString(1);
            found = true;
            ab.add(CODE);
            String beschreibung = rs.getString(2);
            ab.add(beschreibung);

        }
        if (!found) {
            ab.add("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!");

        }
        return ab.build();
    }
 /**
     * Gibt sämtliche Codes zurück welche in der Beschreibung das gesuchte Wort,
     * Suchwort, enthalten.
     * Pfad:http://localhost:8080/RestServerV1/resources/ICF/Suche/Suchwort/X
     *
     * @param code
     * @return XMLfile
     * @throws SQLException
     * @throws java.io.FileNotFoundException
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/{Code}/X")
    public synchronized File ICFbeschrXML(@PathParam("Code") String code) throws SQLException, FileNotFoundException {
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/ICF_DATABASE", "PROJEKTGRUPPE_A", "PROJEKTGRUPPE_A");
        PreparedStatement s = c.prepareStatement("SELECT CODE, Beschreibung FROM ICF_B WHERE BESCHREIBUNG LIKE '%" + code + "%'");
        PreparedStatement s1 = c.prepareStatement("SELECT CODE, Beschreibung FROM ICF_D WHERE BESCHREIBUNG LIKE '%" + code + "%'");
        PreparedStatement s2 = c.prepareStatement("SELECT CODE, Beschreibung FROM ICF_E WHERE BESCHREIBUNG LIKE '%" + code + "%'");
        PreparedStatement s3 = c.prepareStatement("SELECT CODE, Beschreibung FROM ICF_S WHERE BESCHREIBUNG LIKE '%" + code + "%'");

        ResultSet rs = s.executeQuery();
        ResultSet rs1 = s1.executeQuery();
        ResultSet rs2 = s2.executeQuery();
        ResultSet rs3 = s3.executeQuery();

        //zur Überprüfung ob ein Ergebnis zurückkam
        boolean found = false;
        File file = new File("xml.xml");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        XMLEncoder e = new XMLEncoder(bos);
        while (rs.next()) {
            String CODE = rs.getString(1);
            found = true;
            e.writeObject("Code: " + CODE);
            String beschreibung = rs.getString(2);
            e.writeObject("Beschreibung: "+ beschreibung);
        }
        while (rs1.next()) {
            String CODE = rs1.getString(1);
            found = true;
            e.writeObject("Code: " + CODE);
            String beschreibung = rs.getString(2);
            e.writeObject("Beschreibung: "+ beschreibung);
        }
        while (rs2.next()) {
            String CODE = rs2.getString(1);
            found = true;
            e.writeObject("Code: " + CODE);
            String beschreibung = rs.getString(2);
            e.writeObject("Beschreibung: "+ beschreibung);
        }
        while (rs3.next()) {
            String CODE = rs3.getString(1);
            found = true;
            e.writeObject("Code: " + CODE);
            String beschreibung = rs.getString(2);
            e.writeObject("Beschreibung: "+ beschreibung);
        }
        if (!found) {
            e.writeObject("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!");

        }
        e.close();
        return file;
    }
}
