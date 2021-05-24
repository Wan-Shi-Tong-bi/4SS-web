/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author jerem
 */
@ManagedBean(name = "personen")
@RequestScoped
public class DataTable {
    public List<User> personenList;
    
    public List<User> getPersonenList(){
        personenList = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/B_Aufgabe_JSF_User-Datenbank", "db", "db");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, vorname, nachname FROM person");
            while(rs.next()){
                User u = new User();
                u.setId(rs.getInt(1));
                u.setVorname(rs.getString(2));
                u.setNachname(rs.getString(3));
                personenList.add(u);
            }
            stmt.close();
            conn.commit();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return personenList;
    }
}
