package com.example.xi_ii_uebung.model;

public class Person {
    private String id;
    private String vorname;
    private String nachname;
    private String alter;

    public Person(String id, String vorname, String nachname, String alter) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.alter = alter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getAlter() {
        return alter;
    }

    public void setAlter(String alter) {
        this.alter = alter;
    }

    @Override
    public String toString() {
        return "" + vorname + " " + nachname;
    }
}
