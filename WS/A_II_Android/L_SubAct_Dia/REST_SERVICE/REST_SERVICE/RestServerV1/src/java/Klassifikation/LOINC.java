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
//ergänzen des Pfades: http://localhost:8080/RestServerV1/resources/LOINC
@Path("LOINC")
public class LOINC {

    /**
     * gibt eine Übersicht aller LOINC Codes zurück Pfad:
     * ttp://localhost:8080/RestServerV1/resources/LOINC/J Achtung! sehr
     * speicherintensiv(ca 7400 Datensätze)
     *
     * @return JsonObject
     * @throws SQLException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("J")
    public synchronized JsonArray getLOINCJAll() throws SQLException {
        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/loinc", "loinc", "loinc");
        //Statement erstellen
        PreparedStatement s = c.prepareStatement("SELECT Code From LOINC");
        //ins Statement einsetzen
        ResultSet rs = s.executeQuery();

        JsonArrayBuilder a = Json.createArrayBuilder();
        while (rs.next()) {
            JsonObjectBuilder e = Json.createObjectBuilder();
            //Komponente,Eigenschaft,Zeit,Art,Skala,Methode,Klasse
            e.add("Code", rs.getString(1));
            //e.add("Komponente", rs.getString(2));
            //e.add("Eigenschaft",rs.getString(3));
            //e.add("Zeit", rs.getString(4));
            //e.add("Art", rs.getString(5));
            //e.add("Skala", rs.getString(6));
            //e.add("Methode", rs.getString(7));
            //e.add("Klasse", rs.getString(8));
            a.add(e.build());
        }
        return a.build();
    }

    /**
     * gibt eine Übersicht aller LOINC Codes zurück Pfad:
     * ttp://localhost:8080/RestServerV1/resources/LOINC/X Achtung! sehr
     * speicherintensiv(ca 7400 Datensätze)
     *
     * @return File(XML)
     * @throws SQLException
     * @throws FileNotFoundException
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("X")
    public synchronized File getLOINCXAll() throws SQLException, FileNotFoundException {
        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/loinc", "loinc", "loinc");
        //Statement erstellen
        PreparedStatement s = c.prepareStatement("SELECT Code  From Loinc");
        //ins Statement einsetzen
        ResultSet rs = s.executeQuery();
        //File erstellen
        File file = new File("test.xml");
        //Stream und Encoder erstellen
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        XMLEncoder e = new XMLEncoder(bos);
        //hinausschreiben der Ergebnisse in die XML Datei

        while (rs.next()) {
            e.writeObject(rs.getString(1));
            //e.writeObject(rs.getString(2));
            //e.writeObject(rs.getString(3));
            //e.writeObject(rs.getString(4));
            //e.writeObject(rs.getString(5));
            //e.writeObject(rs.getString(6));
            //e.writeObject(rs.getString(7));
            //e.writeObject(rs.getString(8));

        }
        e.close();
        long i = file.length();
        return file;
    }

    /**
     * Gibt die Beschreibung des eingegeben Codes zurück
     * http://localhost:8080/RestServerV1/resources/LOINC/LOINCCode/J
     *
     * @param code
     * @return JsonObject
     * @throws SQLException
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{Code}/J")
    public synchronized JsonObject getLOINCJ(@PathParam("Code") String code) throws SQLException {

        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/loinc", "loinc", "loinc");
        //Statement erstellen
        PreparedStatement s = c.prepareStatement("SELECT Komponente,Eigenschaft,Zeit,Art,Skala,Methode,Klasse FROM LOINC WHERE code=?");
        //ins Statement einsetzen
        s.setString(1, code);
        //Statement ausführen und Ergebnis abfangen
        ResultSet rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang auf -1
        //Überprüfen ob ein Ergebnis zurückkam
        if (rs.next()) {

            //Ergebnis in Strings ablegen      
            String komponente = rs.getString(1);
            String eigenschaft = rs.getString(2);
            String zeit = rs.getString(3);
            String art = rs.getString(4);
            String skala = rs.getString(5);
            String methode = rs.getString(6);
            String klasse = rs.getString(7);

            //JSON Object erstellen
            JsonObjectBuilder e = Json.createObjectBuilder();
            //Daten einfügen
            e.add("Code", code);
            e.add("Komponente", komponente);
            e.add("Eigenschaft", eigenschaft);
            e.add("Zeit", zeit);
            e.add("Art", art);
            e.add("Skala", skala);
            e.add("Methode", methode);
            e.add("Klasse", klasse);

            //Rückgabe
            return e.build();
        } else {
            JsonObjectBuilder e = Json.createObjectBuilder();
            e.add("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!", "");
            return e.build();
        }
    }

    /**
     * Gibt die Beschreibung des eingegeben Codes zurück
     * http://localhost:8080/RestServerV1/resources/LOINC/LOINCCode/X
     *
     * @param code
     * @return File (XML)
     * @throws SQLException
     * @throws FileNotFoundException
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("{Code}/X")
    public synchronized File getLOINCXML(@PathParam("Code") String code) throws SQLException, FileNotFoundException {

        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/loinc", "loinc", "loinc");
        //Statement erstellen
        PreparedStatement s = c.prepareStatement("SELECT Komponente,Eigenschaft,Zeit,Art,Skala,Methode,Klasse FROM LOINC WHERE code=?");
        //ins Statement einsetzen
        s.setString(1, code);
        //Statement auführen und Ergebnis abfangen
        ResultSet rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang auf -1
        //Überprüfen ob ein Ergebnis zurückkam
        if (rs.next()) {
            //Ergebnis in Strings ablegen
            String komponente = rs.getString(1);
            String eigenschaft = rs.getString(2);
            String zeit = rs.getString(3);
            String art = rs.getString(4);
            String skala = rs.getString(5);
            String methode = rs.getString(6);
            String klasse = rs.getString(7);

            //File erstellen
            File file = new File("xml.xml");
            //Stream und Encoder erstellen
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            XMLEncoder e = new XMLEncoder(bos);

            //Daten in XML file einlesen
            e.writeObject("Code: " + code);
            e.writeObject("Komponente: " + komponente);
            e.writeObject("Eigenschaft: " + eigenschaft);
            e.writeObject("Zeit: " + zeit);
            e.writeObject("Art: " + art);
            e.writeObject("Skala: " + skala);
            e.writeObject("Methode: " + methode);
            e.writeObject("Klasse: " + klasse);
            e.close();

            //Rückgabe
            return file;
        } else {
            File file = new File("xml.xml");
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            XMLEncoder e = new XMLEncoder(bos);
            e.writeObject("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!");
            e.close();
            return file;
        }
    }
}
