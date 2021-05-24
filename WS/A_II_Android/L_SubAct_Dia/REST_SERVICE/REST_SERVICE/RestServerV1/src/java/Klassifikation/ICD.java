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
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Anna-Sophie
 */
//ergänzen des Pfades http://localhost:8080/RestServerV1/resources/ICD
@Path("ICD")

public class ICD {
/**
 * gibt eine Übersicht aller ICD Codes zurück Pfad: http://localhost:8080/RestServerV1/resources/ICD/J
 * @return JsonObject
 * @throws SQLException 
 */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("J")
    public synchronized JsonArray getICDJAll() throws SQLException {
        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/ICD", "projektgruppea", "projektgruppea");
        //Statement erstellen
        PreparedStatement s = c.prepareStatement("SELECT * From Krankheiten");
        //ins Statement einsetzen
        ResultSet rs = s.executeQuery();
        //JsonArray erstellen
        JsonArrayBuilder a = Json.createArrayBuilder();
        //Ergebnisse durchlaufen
        while (rs.next()) {
            //JsonObject erstellen und in Array einfügen
            JsonObjectBuilder e = Json.createObjectBuilder();
            e.add("Code", rs.getString(1));
            e.add("Beschreibung", rs.getString(2));
            a.add(e.build());
        }
        //Rückgabe
        return a.build();
    }
/**
 * Gibt eine Übersicht aller ICD Codes zurück Pfad: http://localhost:8080/RestServerV1/resources/ICD/X
 * @return File (XML)
 * @throws SQLException
 * @throws FileNotFoundException 
 */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("X")
    public synchronized File getICDXAll() throws SQLException, FileNotFoundException {
        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/ICD", "projektgruppea", "projektgruppea");
        //Statement erstellen
        PreparedStatement s = c.prepareStatement("SELECT * From Krankheiten");
        //ins Statement einsetzen
        ResultSet rs = s.executeQuery();
        //File erstellen
        File file = new File("xml.xml");
        //Stream und Encoder erstellen
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        XMLEncoder e = new XMLEncoder(bos);
        
        //durchgehen der Ergebnisse
        while (rs.next()) {
            //in File schreiben
            e.writeObject(rs.getString(1));
            e.writeObject(rs.getString(2));
        }
        e.close();
        //Rückgabe
        return file;
    }

    /**
     * Gibt die Beschreibung des eingegeben Codes zurück
     * http://localhost:8080/RestServerV1/resources/ICD/ICDCode/J 
     *
     * @param ICDkey
     * @return JsonObject
     * @throws FileNotFoundException
     * @throws SQLException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{ICDkey}/J")
    public synchronized JsonObject getICDJson(@PathParam("ICDkey") String ICDkey) throws FileNotFoundException, SQLException {

        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/ICD", "projektgruppea", "projektgruppea");
        //Statement erstellen
        PreparedStatement s = c.prepareStatement("SELECT Bezeichnung From Krankheiten WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, ICDkey);
        //Ausführung des Statements und Rückgabe des Ergebnisses
        ResultSet rs = s.executeQuery();
        
        //Zeiger um eins verschieben, ist am Anfang auf -1
        if (rs.next()) {
            //Ergebniss in String umwandeln
            String ergebnis = rs.getString(1);

            //JsonObject erstellen
            JsonObjectBuilder e = Json.createObjectBuilder();
            //Ergebnis hinzufügen
            e.add("ICD Code", ICDkey);
            e.add("Beschreibung", ergebnis);

            //Ergebnis zurückgeben
            return e.build();
        } else {
            JsonObjectBuilder e = Json.createObjectBuilder();
            e.add("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!", "");
            return e.build();
        }

    }

    /**
     * Gibt die Beschreibung des eingegeben Codes zurück
     * http://localhost:8080/RestServerV1/resources/ICD/ICDCode/X
     * @param ICDkey
     * @return File(XML)
     * @throws FileNotFoundException
     * @throws SQLException
     */

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("{ICDkey}/X")
    public synchronized File getICDXML(@PathParam("ICDkey") String ICDkey) throws FileNotFoundException, SQLException {

        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/ICD", "projektgruppea", "projektgruppea");
        //Statement erstellen
        PreparedStatement s = c.prepareStatement("SELECT Bezeichnung From Krankheiten WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, ICDkey);
        //Ausführung des Statements und Rückgabe des Ergebnisses
        ResultSet rs = s.executeQuery();
        //Zeiger um eins verschieben; ist am Anfang auf -1
        
        if (rs.next()) {
            //einspeichern des Ergebnisses als String
            String ergebnis = rs.getString(1);
            //File erstellen
            File file = new File("xml.xml");
            //Stream und Encoder erstellen
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            XMLEncoder e = new XMLEncoder(bos);
            //hinausschreiben der Ergebnisse in die XML Datei
            e.writeObject(ICDkey);
            e.writeObject(ergebnis);
            e.close();

            //Rückgabe
            return file;
        } else {
            //File erstellen
            File file = new File("xml.xml");
            //Stream und Encoder erstellen
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            XMLEncoder e = new XMLEncoder(bos);
            //hinausschreiben der Ergebnisse in die XML Datei
            e.writeObject("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!");
            e.close();

            //Rückgabe
            return file;
        }

    }
}
