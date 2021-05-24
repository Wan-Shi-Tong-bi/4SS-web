/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Pascal
 */
@Path("res")
public class Class {

   
     // http://localhost:8081/REST_Server_Patientenakte/res/test           
  @POST
  @Produces(MediaType.TEXT_HTML)
  @Path("test")
  public String sayPlainTextHello( @FormParam("id") int id,
          @FormParam("vorname") String vorname, 
          @FormParam("nachname") String nachname, 
          @FormParam("geschlecht") String geschlecht, 
          @FormParam("geburtsdatum") String geb, 
          @FormParam("anmerkung") String anmerkung) {
   
      String temp =  "<!DOCTYPE html>" +
"<html>" +
"    <head>" +
"        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
"        <title></title>" 
 + "vorname = " + vorname + "<br>" + "nachname=" + nachname + "<br>" +
              "geschlecht = " + geschlecht + "<br>" + "geburtsdatum=" + geb 
              + "<br>" +"anmerkung=" + anmerkung +
"    </head>" +
"    <body>";      
           
            temp += "    </body>" +
"</html>";
    return temp;
  }

    
}
