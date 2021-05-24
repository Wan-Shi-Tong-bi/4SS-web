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
@Path("ICF")
public class ICF {
/**
 * gibt eine Übersicht aller ICF Codes zurück Pfad: http://localhost:8080/RestServerV1/resources/ICF/J
 * @return JsonObject
 * @throws SQLException 
 */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("J")
    public synchronized JsonArray getICFJAll() throws SQLException {
        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/ICF_DATABASE", "PROJEKTGRUPPE_A", "PROJEKTGRUPPE_A");
        //Statement erstellen
        //es gibt bei ICF mehrere Tabellen geordnet nach dem ersten Buchstaben des Codes
        PreparedStatement s = c.prepareStatement("SELECT * From ICF_B");
        //ins Statement einsetzen
        ResultSet rs = s.executeQuery();

        JsonArrayBuilder a = Json.createArrayBuilder();
        while (rs.next()) {
            JsonObjectBuilder e = Json.createObjectBuilder();

            e.add("Code", rs.getString(1));
            e.add("Beschreibung", rs.getString(2));
            a.add(e.build());
        }
        //es gibt bei ICF mehrere Tabellen geordnet nach dem ersten Buchstaben des Codes
        s = c.prepareStatement("SELECT * From ICF_E");
        //ins Statement einsetzen
        rs = s.executeQuery();

        while (rs.next()) {
            JsonObjectBuilder e = Json.createObjectBuilder();

            e.add("Code", rs.getString(1));
            e.add("Beschreibung", rs.getString(2));
            a.add(e.build());
        }
        //es gibt bei ICF mehrere Tabellen geordnet nach dem ersten Buchstaben des Codes
        s = c.prepareStatement("SELECT * From ICF_D");
        //ins Statement einsetzen
        rs = s.executeQuery();

        while (rs.next()) {
            JsonObjectBuilder e = Json.createObjectBuilder();

            e.add("Code", rs.getString(1));
            e.add("Beschreibung", rs.getString(2));
            a.add(e.build());
        }
        //es gibt bei ICF mehrere Tabellen geordnet nach dem ersten Buchstaben des Codes
        s = c.prepareStatement("SELECT * From ICF_S");
        //ins Statement einsetzen
        rs = s.executeQuery();

        while (rs.next()) {
            JsonObjectBuilder e = Json.createObjectBuilder();

            e.add("Code", rs.getString(1));
            e.add("Beschreibung", rs.getString(2));
            a.add(e.build());
        }

        return a.build();
    }
/**
 *  gibt eine Übersicht aller ICD Codes zurück Pfad: http://localhost:8080/RestServerV1/resources/ICF/X
 * @return File(XML)
 * @throws SQLException
 * @throws FileNotFoundException 
 */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("X")
    public synchronized File getICFXAll() throws SQLException, FileNotFoundException {
        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/ICF_DATABASE", "PROJEKTGRUPPE_A", "PROJEKTGRUPPE_A");
        //Statement erstellen
        //es gibt bei ICF mehrere Tabellen geordnet nach dem ersten Buchstaben des Codes
        PreparedStatement s = c.prepareStatement("SELECT * From ICF_B");
        //ins Statement einsetzen
        ResultSet rs = s.executeQuery();
        //File erstellen
        File file = new File("xml.xml");
        //Stream und Encoder erstellen
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        XMLEncoder e = new XMLEncoder(bos);
        //hinausschreiben der Ergebnisse in die XML Datei

        while (rs.next()) {

            e.writeObject(rs.getString(1));
            e.writeObject(rs.getString(2));
        }
        //es gibt bei ICF mehrere Tabellen geordnet nach dem ersten Buchstaben des Codes
        s = c.prepareStatement("SELECT * From ICF_E");
        //ins Statement einsetzen
        rs = s.executeQuery();
        while (rs.next()) {

            e.writeObject(rs.getString(1));
            e.writeObject(rs.getString(2));
        }
        //es gibt bei ICF mehrere Tabellen geordnet nach dem ersten Buchstaben des Codes
        s = c.prepareStatement("SELECT * From ICF_D");
        //ins Statement einsetzen
        rs = s.executeQuery();
        while (rs.next()) {

            e.writeObject(rs.getString(1));
            e.writeObject(rs.getString(2));
        }
        //es gibt bei ICF mehrere Tabellen geordnet nach dem ersten Buchstaben des Codes
        s = c.prepareStatement("SELECT * From ICF_S");
        //ins Statement einsetzen
        rs = s.executeQuery();
        while (rs.next()) {

            e.writeObject(rs.getString(1));
            e.writeObject(rs.getString(2));
        }

        e.close();
        return file;
    }

    /**
     * Gibt die Beschreibung des eingegeben Codes zurück
     * http://localhost:8080/RestServerV1/resources/ICF/ICFCode/J
     *
     * @param code
     * @return JsonObject
     * @throws SQLException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{Code}/J")
    public synchronized JsonObject getICFJSON(@PathParam("Code") String code) throws SQLException {

        //herstellen der Verbindung zur DB
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/ICF_DATABASE", "PROJEKTGRUPPE_A", "PROJEKTGRUPPE_A");

        //auslesen in welcher Tabelle gesucht werden soll; ist abhängig vom ersten Buchstaben des Codes
        String vor = code.substring(0, 1);
        if (vor.equals("b")) {
            //Statement erstellen
            PreparedStatement s = c.prepareStatement("SELECT Beschreibung FROM ICF_B WHERE CODE=?");
            //ins Statement einsetzen
            s.setString(1, code);
            //Anfrage ausführen und Ergebnis abfangen
            ResultSet rs = s.executeQuery();
            //JSON Objekt erstellen
            JsonObjectBuilder o = Json.createObjectBuilder();
            //Zeiger um eins verschieben, steht am Anfang auf -1
            //ueberpruefen ob ein Ergebnis zurueckkam
            if(rs.next()){
            //Ergebnis in String ablegen
            String ergebnis = rs.getString(1);
            //Ergebnis übertragen
            o.add("ICF code", code);
            o.add("ICF Beschreibung", ergebnis);
            } else{
                o.add("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!", "");
            }
            //Rückgabe 
            return o.build();
            //andere Tabelle in DB ICF
        } else if (vor.equals("d")) {
            //Statement erstellen
            PreparedStatement s = c.prepareStatement("SELECT Beschreibung FROM ICF_D WHERE CODE=?");
            //ins Statement einsetzen
            s.setString(1, code);
            //Statement ausführen und Ergebnis abfangen
            ResultSet rs = s.executeQuery();
             //JSON Object erstellen
            JsonObjectBuilder o = Json.createObjectBuilder();
            //Zeiger um eins verschieben, ist am Anfang auf -1
            //ueberpruefen ob ein Ergebnis zurueckkam
            if(rs.next()){
            //Ergebnis in String ablegen
            String ergebnis = rs.getString(1);
            //Daten einfügen
            o.add("ICF code", code);
            o.add("ICF Beschreibung", ergebnis);
             } else{
                o.add("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!", "");
            }
            //Rückgabe
            return o.build();
            //andere Tabelle in DB ICF   
        } else if (vor.equals("e")) {
            //Statement erstellen
            PreparedStatement s = c.prepareStatement("SELECT Beschreibung FROM ICF_E WHERE CODE=?");
            //ins Statement einsetzen
            s.setString(1, code);
            //Statement ausführen und Ergebnis abfangen
            ResultSet rs = s.executeQuery();
            //JSON Object erstellen
            JsonObjectBuilder o = Json.createObjectBuilder();
            //Zeiger um eins verschieben, ist am Anfang auf -1
            //ueberpruefen ob ein Ergebnis zurueckkam
            if(rs.next()){
            //Ergebnis in String ablegen
            String ergebnis = rs.getString(1);
            //Daten einfügen
            o.add("ICF code", code);
            o.add("ICF Beschreibung", ergebnis);
             } else{
                o.add("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!", "");
            }
            //Rückgabe
            return o.build();
            //andere Tabelle in DB ICF
        } else if (vor.equals("s")) {

            //Statement erstellen
            PreparedStatement s = c.prepareStatement("SELECT Beschreibung FROM ICF_S WHERE CODE=?");
            //ins Statement einsetzen
            s.setString(1, code);
            //Statement ausführen und Ergebnis abfangen
            ResultSet rs = s.executeQuery();
            //JSON Object erstellen
            JsonObjectBuilder o = Json.createObjectBuilder();
            //Zeiger um eins verschieben, ist am Anfang -1
            //ueberpruefen ob ein Ergebnis zurueckkam
            if(rs.next()){
            //Ergebnis in String ablegen
            String ergebnis = rs.getString(1);

            
            //Daten einfügen
            o.add("ICF code", code);
            o.add("ICF Beschreibung", ergebnis);
            } else{
                o.add("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!", "");
            }
            //Rückgabe
            return o.build();

            //falls kein richtiger Wert eingegeben wurde    
        } else {
            //JSON Object erstellen
            JsonObjectBuilder o = Json.createObjectBuilder();
            //Fehlermeldung einfügen
            o.add("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!", "");
            //Rückgabe
            return o.build();
        }
    }

    /**
     * Gibt die Beschreibung des eingegeben Codes zurück
     * http://localhost:8080/RestServerV1/resources/ICF/ICFCode/X
     *
     * @param code
     * @return File (XML)
     * @throws SQLException
     * @throws FileNotFoundException
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("{Code}/X")
    public synchronized File getICFXML(@PathParam("Code") String code) throws SQLException, FileNotFoundException {

        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/ICF_DATABASE", "PROJEKTGRUPPE_A", "PROJEKTGRUPPE_A");
        //erster Buchstabe entscheidet welche Tabelle der DB ICF benutzt wird
        String vor = code.substring(0, 1);

        if (vor.equals("b")) {

            //Statement erstellen
            PreparedStatement s = c.prepareStatement("SELECT Beschreibung FROM ICF_B WHERE CODE=?");
            //ins Statement einsetzen
            s.setString(1, code);
            //Statement ausführen und Ergebnis abfangen
            ResultSet rs = s.executeQuery();
            //File erstellen
            File file = new File("xml.xml");
            //Stream und Encoder erstellen
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            XMLEncoder e = new XMLEncoder(bos);
            //Zeiger um eins verschieben, ist am Anfang auf -1
            //ueberpruefen ob ein Ergebnis zurueckkam
            if(rs.next()){
            //Ergebnis in String ablegen
            String ergebnis = rs.getString(1);

            
            //hinausschreiben der Ergebnisse in die XML Datei
            e.writeObject(code);
            e.writeObject(ergebnis);
             } else{
                e.writeObject("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!");
            }
            e.close();

            //Rückgabe
            return file;

            //andere Tabelle in DB ICF    
        } else if (vor.equals("d")) {

            //Statement erstellen
            PreparedStatement s = c.prepareStatement("SELECT Beschreibung FROM ICF_D WHERE CODE=?");
            //ins Statement einsetzen
            s.setString(1, code);
            //Statement ausführen und Ergebnis abfangen
            ResultSet rs = s.executeQuery();
            //File erstellen
            File file = new File("xml.xml");
            //Stream und Encoder erstellen
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            XMLEncoder e = new XMLEncoder(bos);
            //Zeiger um eins verschieben, ist am Anfang auf -1
            //ueberpruefen ob ein Ergebnis zurueckkam
            if(rs.next()){
            //Ergebnis in String ablegen
            String ergebnis = rs.getString(1);

            
            //hinausschreiben der Ergebnisse in die XML Datei
            e.writeObject(code);
            e.writeObject(ergebnis);
            } else{
                e.writeObject("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!");
            }
            e.close();

            //Rückgabe
            return file;

            //andere Tabelle in DB ICF    
        } else if (vor.equals("e")) {

            //Statement erstellen
            PreparedStatement s = c.prepareStatement("SELECT Beschreibung FROM ICF_E WHERE CODE=?");
            //ins Statement einsetzen
            s.setString(1, code);
            //Statement ausführen und Ergebnis abfangen
            ResultSet rs = s.executeQuery();
            //File erstellen
            File file = new File("xml.xml");
            //Stream und Encoder erstellen
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            XMLEncoder e = new XMLEncoder(bos);
            //Zeiger um eins verschieben, ist am Anfang -1
            //ueberpruefen ob ein Ergebnis zurueckkam
            if(rs.next()){
            //Ergebnis in String ablegen
            String ergebnis = rs.getString(1);
            //hinausschreiben der Ergebnisse in die XML Datei
            e.writeObject(code);
            e.writeObject(ergebnis);
            } else{
                e.writeObject("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!");
            }
            e.close();

            //Rückgabe
            return file;

            // andere Tabelle in DB ICF
        } else if (vor.equals("s")) {

            //Statement erstellen
            PreparedStatement s = c.prepareStatement("SELECT Beschreibung FROM ICF_S WHERE CODE=?");
            //ins Statement einfügen
            s.setString(1, code);
            //Statement ausführen und Ergebnis abfangen
            ResultSet rs = s.executeQuery();
            //File erstellen
            File file = new File("xml.xml");
            //Stream und Encoder erstellen
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            XMLEncoder e = new XMLEncoder(bos);
            //Zeiger um eins verschieben, ist am Anfang -1
             //ueberpruefen ob ein Ergebnis zurueckkam
            if(rs.next()){
            //Ergebnis in String ablegen
            String ergebnis = rs.getString(1);
            //hinausschreiben der Ergebnisse in die XML Datei
            e.writeObject(code);
            e.writeObject(ergebnis);
             } else{
                e.writeObject("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!");
            }
            e.close();

            //Rückgabe
            return file;

            //falls kein richtiger Wert eingegeben wurde  
        } else {

            //File erstellen
            File file = new File("xml.xml");
            //Stream und Encoder erstellen
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            XMLEncoder e = new XMLEncoder(bos);
            //hinausschreiben der Fehlermeldung
            e.writeObject("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!");
            e.close();

            //Rückgabe
            return file;
        }
    }
}
