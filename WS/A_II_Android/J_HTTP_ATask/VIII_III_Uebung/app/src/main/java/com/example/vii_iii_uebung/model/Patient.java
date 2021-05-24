package com.example.vii_iii_uebung.model;

import java.io.Serializable;
import java.util.LinkedList;

public class Patient implements Serializable {
    private int iD;
    private String vorname;
    private String nachname;
    private LinkedList<Eintrag> liste = new LinkedList<Eintrag>();

    public Patient(int iD, String vorname, String nachname) {
        this.iD = iD;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public Patient(){

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
        return  ""+  iD + "\n" + vorname + " " + nachname;
    }

}
