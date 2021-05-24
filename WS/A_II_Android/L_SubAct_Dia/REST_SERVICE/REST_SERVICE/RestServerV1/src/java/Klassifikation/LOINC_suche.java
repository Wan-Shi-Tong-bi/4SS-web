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
@Path("LOINC/Suche")
public class LOINC_suche {
     /**
     * Gibt sämtliche Codes zurück welche in der Beschreibung das gesuchte Wort,
     * Suchwort, enthalten.
     * Pfad:http://localhost:8080/RestServerV1/resources/LOINC/Suche/Suchwort/J
     *
     * @param code
     * @return JsonArray
     * @throws SQLException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{Code}/J")
    public synchronized JsonArray LOINCbeschrJ(@PathParam("Code") String code) throws SQLException{
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/loinc", "loinc", "loinc");
        PreparedStatement s = c.prepareStatement("Select CODE, KOMPONENTE, EIGENSCHAFT, ZEIT, ART, SKALA, METHODE, KLASSE   FROM LOINC WHERE KOMPONENTE LIKE '%"+code+"%'");
      //  s.setString(1, code);
        JsonArrayBuilder ab = Json.createArrayBuilder();
        //zum Überprüfen ob ein Ergebnis zurückkam
        boolean found = false;
        ResultSet rs = s.executeQuery();
        while(rs.next()){
        String CODE = rs.getString(1);
        String komp = rs.getString(2);
        String eigen = rs.getString(3);
        String zeit = rs.getString(4);
        String art = rs.getString(5);
        String skala = rs.getString(6);
        String meth = rs.getString(7);
        String klas = rs.getString(8);
        found = true;
        
        ab.add(CODE);
        ab.add(komp);
        ab.add(eigen);
        ab.add(zeit);
        ab.add(art);
        ab.add(skala);
        ab.add(meth);
        ab.add(klas);
        
        }
        if(!found){
            ab.add("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!");
        }
        return  ab.build();
    }
     /**
     * Gibt sämtliche Codes zurück welche in der Beschreibung das gesuchte Wort,
     * Suchwort, enthalten.
     * Pfad:http://localhost:8080/RestServerV1/resources/LOINC/Suche/Suchwort/X
     *
     * @param code
     * @return XMLfile
     * @throws SQLException
     * @throws java.io.FileNotFoundException
     */
  @GET
  @Produces(MediaType.APPLICATION_XML)
  @Path("/{Code}/X")
  public synchronized File LOINCbeschrXML(@PathParam("Code") String code) throws SQLException, FileNotFoundException{
       Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/loinc", "loinc", "loinc");
        PreparedStatement s = c.prepareStatement("SELECT CODE, KOMPONENTE, EIGENSCHAFT, ZEIT, ART, SKALA, METHODE, KLASSE FROM LOINC WHERE KOMPONENTE LIKE '%"+code+"%'");
       // s.setString(1, code);
        ResultSet rs = s.executeQuery();
        //zum Überprüfen ob ein Ergebnis zurückkam
        boolean found = false;
        File file = new File("xml.xml");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        XMLEncoder e = new XMLEncoder(bos);
        while(rs.next()){
        String CODE = rs.getString(1);
        String komp = rs.getString(2);
        String eigen = rs.getString(3);
        String zeit = rs.getString(4);
        String art = rs.getString(5);
        String skala = rs.getString(6);
        String meth = rs.getString(7);
        String klas = rs.getString(8);
        
        e.writeObject("Code: " + CODE);
        e.writeObject("Komponente: " + komp);
        e.writeObject("Eigenschaft: " + eigen);
        e.writeObject("Zeit: " + zeit);
        e.writeObject("Art: " + art);
        e.writeObject("Skala: " + skala);
        e.writeObject("Methode: " + meth);
        e.writeObject("Klasse: " + klas);
        found = true;
        }
        if(!found){
            e.writeObject("BEI DIESEM SUCHBEGRIFF WURDE NICHTS GEFUNDEN!");
        }
        e.close();
        return file;
    }
    
}
