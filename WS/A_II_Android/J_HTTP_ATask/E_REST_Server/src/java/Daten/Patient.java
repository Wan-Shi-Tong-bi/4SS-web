/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daten;

import java.util.LinkedList;

/**
 *
 * @author root
 */
public class Patient {
    private int iD;
    private String vorname;
    private String nachname;
    private LinkedList<Eintrag> liste = new LinkedList<Eintrag>();
    
    public Patient(int iD, String vorname, String nachname) {
        this.iD = iD;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
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

    public LinkedList<Eintrag> getListe() {
        return liste;
    }

    public void setListe(LinkedList<Eintrag> liste) {
        this.liste = liste;
    }

    public void addEintrag(Eintrag e) {
        liste.add(e);
    }
  

    @Override
    public String toString() {
        return "Patient{" + "iD=" + iD + ", vorname=" + vorname + ", nachname=" + nachname + ", liste=" + liste + '}';
    }
    
}
