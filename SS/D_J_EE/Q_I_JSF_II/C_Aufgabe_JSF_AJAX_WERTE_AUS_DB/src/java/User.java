
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.json.Json;
import javax.json.JsonObject;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jerem
 */

@ManagedBean
@SessionScoped
public class User {
    private int id;
    private String vorname;
    private String nachname;
    
    private String value;
    
    public void valueSetzen(int id){
        try {
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/kuk", "kuk", "kuk");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT param1, value1, param2, value2, param3, value3 FROM test where benutzer_id = " + id);
            
            rs.next();
            JsonObject empObj = Json.createObjectBuilder()
                    .add(rs.getString(1), rs.getInt(2))
                    .add(rs.getString(3), rs.getInt(4))
                    .add(rs.getString(5), rs.getInt(6))
                    .build();
            value = empObj.toString();
            
            stmt.close();
            conn.commit();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
    
}
