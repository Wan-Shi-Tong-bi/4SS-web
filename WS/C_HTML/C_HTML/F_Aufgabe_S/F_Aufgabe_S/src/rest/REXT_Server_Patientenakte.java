/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Pascal
 */
public class REXT_Server_Patientenakte {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            HttpServer server = HttpServerFactory.create("http://localhost:8081/REST_Server_Patientenakte");
            server.start();
            JOptionPane.showConfirmDialog(null, "Abbrechen");
            server.stop(0);
        } catch (IOException ex) {
            Logger.getLogger(REXT_Server_Patientenakte.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(REXT_Server_Patientenakte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
