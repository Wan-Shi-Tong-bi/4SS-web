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
//Pfad ergänzen http://localhost:8080/resources/TNM
@Path("TNM")
public class TNM {
/**
 * gibt eine Übersicht aller TNM Codes zurück Pfad: http://localhost:8080/RestServerV1/resources/TNM/J
 * @return JsonObject
 * @throws SQLException 
 */
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("J")
    public synchronized JsonArray getTNMJ() throws SQLException{
        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/dandonovTNM", "dandonov", "dandonov");
        //Statement erstellen
        PreparedStatement s = c.prepareStatement("SELECT * From TNM WHERE Code IS NOT NULL");
        //ins Statement einsetzen
        ResultSet rs = s.executeQuery();
        
        JsonArrayBuilder a = Json.createArrayBuilder();
        while(rs.next()){
            JsonObjectBuilder e = Json.createObjectBuilder();
            
            e.add("Beschreibung",rs.getString(1));
            e.add("Code", rs.getString(2));
            a.add(e.build());
        }
        return a.build();
    }
    
    /**
     *  gibt eine Übersicht aller TNM Codes zurück Pfad: http://localhost:8080/RestServerV1/resources/TNM/X
     * @return File (XML)
     * @throws SQLException
     * @throws FileNotFoundException 
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("X")
    public synchronized File getTNMX() throws SQLException, FileNotFoundException{
        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/dandonovTNM", "dandonov", "dandonov");
        //Statement erstellen
        PreparedStatement s = c.prepareStatement("SELECT * From TNM WHERE Code IS NOT NULL");
        //ins Statement einsetzen
        ResultSet rs = s.executeQuery();
        //File erstellen
        File file = new File("xml.xml");
        //Stream und Encoder erstellen
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        XMLEncoder e = new XMLEncoder(bos);
        //hinausschreiben der Ergebnisse in die XML Datei
       
        
        while(rs.next()){
            e.writeObject(rs.getString(1));
            e.writeObject(rs.getString(2));
        }
        e.close();
        return file;
    }
    /**
     * Gibt die Beschreibung des eingegeben Codes zurück
     * http://localhost:8080/resources/TNM/Präfix/Größe/CGröße/Lymphkonten/CLymphknoten/Metastasen/CMetastasen/J
     *
     * @param praefix Wie wurde die Diagnose herausgefunden? (a,y,r,p)
     * @param t Größe des Tumors z.B.(Range von T0-4, Tis, Ta und Tx)
     * @param tc Wie wurde die Größe des Tumors festgestellt? (C1-C5)
     * @param n Anzahl der Lymphknoten (N0-3, Nx)
     * @param nc Wie wurde die Anzahl der Lymphkonten festgestellt? (C1-C5)
     * @param m Gibt es Metastasen? (M0,M1)
     * @param mc Wie wurden die Metastasen untersucht? (C1-C5)
     * @return JsonObject
     * @throws FileNotFoundException
     * @throws SQLException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{Praefix}/{T}/{TC}/{N}/{NC}/{M}/{MC}/J")
    public synchronized JsonObject getTNMCJSON(@PathParam("Praefix") String praefix, @PathParam("T") String t, @PathParam("TC") String tc, @PathParam("N") String n, @PathParam("NC") String nc, @PathParam("M") String m, @PathParam("MC") String mc) throws FileNotFoundException, SQLException {

        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/dandonovTNM", "dandonov", "dandonov");
        //Statement erstellen
        PreparedStatement s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, praefix);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        ResultSet rs = s.executeQuery();
        String ergebnis = null;
        JsonObjectBuilder e = Json.createObjectBuilder();
        //Zeiger um eins verschieben, ist am Anfang auf -1
        //Überprüfen ob ein Ergebnis zurückkam
        
        if(rs.next()){
             
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //JsonObject erstellen
        
        //Ergebnis hinzufügen
        e.add("Praefix", ergebnis);
        }else{
            e.add("Präfix konnte nicht gefunden werden!", "");
        }
       
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, t);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang auf -1
       //Überprüfen ob ein Ergebnis zurückkam
        
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.add("Groesse des Tumors", ergebnis);
         }else{
            e.add("Groesse des Tumors konnte nicht gefunden werden!", "");
        }
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, tc);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang auf -1
       //Überprüfen ob ein Ergebnis zurückkam
        
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.add("Sicherheit von T", ergebnis);
        }else{
            e.add("Sicherheit von T konnte nicht gefunden werden!", "");
        }
        
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, n);
        //Statement ausführen und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben
        //Überprüfen ob ein Ergebnis zurückkam
        
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.add("Anzahl der Lymphknoten", ergebnis);
        }else{
            e.add("Anzahl der Lymphknoten konnte nicht gefunden werden!", "");
        }
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, nc);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang -1
        //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.add("Sicherheit von N", ergebnis);
        }else{
            e.add("Sicherheit von N konnte nicht gefunden werden!", "");
        }
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, m);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang auf -1
        //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.add("Metastasen", ergebnis);
        }else{
            e.add("Anzahl der Metastasen konnte nicht gefunden werden!", "");
        }
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, mc);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang auf -1
        //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.add("Sicherheit von M", ergebnis);
        }else{
            e.add("Sicherheit der Metastasen konnte nicht gefunden werden!", "");
        }
        //Rückgabe
        return e.build();
        

    }

    /**
     * Gibt die Beschreibung des eingegeben Codes zurück
     * http://localhost:8080/resources/TNM/Präfix/Größe/CGröße/Lymphkonten/CLymphknoten/Metastasen/CMetastasen/X
     *
     * @param praefix Wie wurde die Diagnose herausgefunden? (a,y,r,p)
     * @param t Größe des Tumors z.B.(Range von T0-4, Tis, Ta und Tx)
     * @param tc Wie wurde die Größe des Tumors festgestellt? (C1-C5)
     * @param n Anzahl der Lymphknoten (N0-3, Nx)
     * @param nc Wie wurde die Anzahl der Lymphkonten festgestellt? (C1-C5)
     * @param m Gibt es Metastasen? (M0,M1)
     * @param mc Wie wurden die Metastasen untersucht? (C1-C5)
     * @return File (XML)
     * @throws FileNotFoundException
     * @throws SQLException
     */

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("{Praefix}/{T}/{TC}/{N}/{NC}/{M}/{MC}/X")
    public synchronized File getTNMCXML(@PathParam("Praefix") String praefix, @PathParam("T") String t, @PathParam("TC") String tc, @PathParam("N") String n, @PathParam("NC") String nc, @PathParam("M") String m, @PathParam("MC") String mc) throws FileNotFoundException, SQLException {
        //File erstellen
        File file = new File("xml.xml");
        //Stream und Encoder erstellen
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        XMLEncoder e = new XMLEncoder(bos);
        String ergebnis = null;
        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/dandonovTNM", "dandonov", "dandonov");
        //Statement erstellen
        PreparedStatement s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, praefix);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        ResultSet rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang -1
        //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);

       
        //hinausschreiben der Ergebnisse in die XML Datei
        e.writeObject("Praefix: " + ergebnis);
        }else{
                e.writeObject("Praefix konnte nicht gefunden werden!");
                }
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, t);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang -1
        //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.writeObject("Groesse des Tumors: " + ergebnis);
        }else{
                e.writeObject("Groesse des Tumors konnte nicht gefunden werden!");
             }
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, tc);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang -1
        //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.writeObject("Sicherheit von T: " + ergebnis);
        }else{
                e.writeObject("Sicherheit der Groesse des Tumors konnte nicht gefunden werden!");
             }
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, n);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang -1
        //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ausführen des Statements und Rückgabe des Ergebnisses
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.writeObject("Anzahl der Lymphknoten: " + ergebnis);
        }else{
                e.writeObject("Anzahl der Lymphknoten des Tumors konnte nicht gefunden werden!");
             }
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, nc);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang -1
        //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.writeObject("Sicherheit von N: " + ergebnis);
        }else{
                e.writeObject("Sicherheit der Anzahl der Lymphknoten des Tumors konnte nicht gefunden werden!");
             }
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, m);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang -1
       //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.writeObject("Metastasen: " + ergebnis);
        }else{
                e.writeObject("Anzahl der Metastasen konnte nicht gefunden werden!");
             }
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, mc);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang -1
       //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.writeObject("Sicherheit von M: " + ergebnis);
         }
        else{
                e.writeObject("Sicherheit der Anzahl der Metastasen konnte nicht gefunden werden!");
             }
        e.close();

        //Rückgabe
        return file;
       
    }

    /**
     * Einfachere Version der TNM Abfrage, ohne Untersuchungsarten und Präfix 
     * Gibt die Beschreibung des eingegeben Codes zurück
     * http://localhost:8080/RestServerV1/resources/TNM/Groesse/Lymphknoten/Metastasen/J
     *
     * @param t Größe des Tumors z.B.(Range von T0-4, Tis, Ta und Tx)
     * @param n Anzahl der Lymphknoten (N0-3, Nx)
     * @param m Gibt es Metastasen? (M0,M1)
     * @return JsonObject
     * @throws FileNotFoundException
     * @throws SQLException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{T}/{N}/{M}/J")
    public synchronized JsonObject getTNMJSON(@PathParam("T") String t, @PathParam("N") String n, @PathParam("M") String m) throws FileNotFoundException, SQLException {

        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/dandonovTNM", "dandonov", "dandonov");
        //Statement erstellen 
        PreparedStatement s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, t);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        ResultSet rs = s.executeQuery();
        //JsonObject erstellen
        JsonObjectBuilder e = Json.createObjectBuilder();
        String ergebnis = null;
        //Zeiger um eins verschieben, ist am Anfang -1
        //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ergebnis in String ablegen
         ergebnis = rs.getString(1);
        
        //Ergebnis hinzufügen
        e.add("Groesse des Tumors", ergebnis);
        }else{
                e.add("Groesse des Tumors konnte nicht gefunden werden!","");
             }
        
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, n);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang -1
        //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.add("Anzahl der Lymphknoten", ergebnis);
        }else{
                e.add("Anzahl der Lymphknoten konnte nicht gefunden werden!","");
             }
        
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, m);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang -1
        //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.add("Metastasen", ergebnis);
        }else{
                e.add("Anzahl der Metastasen konnte nicht gefunden werden!","");
             }
        //Rückgabe
        return e.build();
        }

    

    
    /**
     * Einfachere Version der TNM Abfrage, ohne Untersuchungsarten und Präfix
     * Gibt die Beschreibung des eingegeben Codes zurück
     * http://localhost:8080/RestServerV1/resources/TNM/Groesse/Lymphknoten/Metastasen/X
     * @param t Größe des Tumors z.B.(Range von T0-4, Tis, Ta und Tx)
     * @param n Anzahl der Lymphknoten (N0-3, Nx)
     * @param m Gibt es Metastasen? (M0,M1)
     * @return File (XML)
     * @throws FileNotFoundException
     * @throws SQLException 
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("{T}/{N}/{M}/X")
    public synchronized File getTNMCXML(@PathParam("T") String t, @PathParam("N") String n, @PathParam("M") String m) throws FileNotFoundException, SQLException {

        //Verbindung zur DB herstellen
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/dandonovTNM", "dandonov", "dandonov");
        
        //File, Stream und Encoder erstellen
        File file = new File("xml.xml");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        XMLEncoder e = new XMLEncoder(bos);
        String ergebnis = null;
        //Statement erstellen
        PreparedStatement s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, t);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        ResultSet rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang -1
        //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.writeObject("Groesse des Tumors: " + ergebnis);
        }else{
                e.writeObject("Groesse des Tumors konnte nicht gefunden werden");
                }
        
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, n);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben, ist am Anfang -1
        //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.writeObject("Anzahl der Lymphknoten: " + ergebnis);
        }else{
                e.writeObject("Anzahl der Lymphknoten konnte nicht gefunden werden");
                }
        
        //Statement neu schreiben
        s = c.prepareStatement("SELECT Beschreibung From TNM WHERE Code=?");
        //ins Statement einsetzen
        s.setString(1, m);
        //Ausführen des Statements und Rückgabe des Ergebnisses
        rs = s.executeQuery();
        //Zeiger um eins verschieben
        //Überprüfen ob ein Ergebnis zurückkam
        if(rs.next()){
        //Ergebnis in String ablegen
        ergebnis = rs.getString(1);
        //Ergebnis hinzufügen
        e.writeObject("Metastasen: " + ergebnis);
        }else{
                e.writeObject("Anzahl der Metastasen konnte nicht gefunden werden");
                }
        
        e.close();
        
        //Rückgabe
        return file;
       
    }
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("{code}/J")
    public synchronized String getFalseURLJ(){
    return "Bitte trennen Sie die einzelnen Parameter durch einen / !";
}
 @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("{code}/X")
    public synchronized String getFalseURLX(){
    return "Bitte trennen Sie die einzelnen Parameter durch einen / !";
}   
}
