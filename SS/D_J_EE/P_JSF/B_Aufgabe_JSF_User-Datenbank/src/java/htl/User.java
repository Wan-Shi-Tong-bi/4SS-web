/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jerem
 */
@ManagedBean
public class User {
    @Size(min = 1, message="Vorname darf nicht leer sein!")
    private String vorname;
    @Size(min = 1, message="Nachname darf nicht leer sein!")
    private String nachname;
    private int id;
    @NotNull(message="Es muss ein File ausgewählt sein!")
    private Part file;
    
    public String upload(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/B_Aufgabe_JSF_User-Datenbank", "db", "db");
            
            //ID herausfinden
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(id) FROM person");
            rs.next();
            int count = rs.getInt(1) + 1;
            rs.close();
            stmt.close();
            
            //in Datenbank speichern
            PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO person (id, vorname, nachname, image) VALUES (?,?,?,?)");
            
            prepStmt.setInt(1,count);
            prepStmt.setString(2, vorname);
            prepStmt.setString(3, nachname);
            prepStmt.setBlob(4, file.getInputStream());
            
            prepStmt.executeUpdate();
            conn.commit();
            conn.close();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "switch";
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    
}
