package com.example.vi_i_aufgabe_unfallbericht;

import java.io.Serializable;

public class Witness implements Serializable {
    private String vorname;
    private String nachname;
    private String tel;

    public Witness(String vorname, String nachname, String tel) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.tel = tel;
    }

    public Witness() {
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "" + vorname + ';' +
                nachname + ';' +
                tel + ';';
    }

    public static Witness deserialize(String zeile){
        String[] arr = zeile.split(";");
        return  new Witness(arr[0], arr[1], arr[2]);
    }
}
