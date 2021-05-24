package com.example.vii_ii_uebung_cda_viewer;

public class Person {
    private String vor;
    private String nach;
    private String str;
    private String nr;
    private String plz;
    private String ort;

    public Person() {

    }

    public Person(String vor, String nach, String str, String nr, String plz, String ort) {
        this.vor = vor;
        this.nach = nach;
        this.str = str;
        this.nr = nr;
        this.plz = plz;
        this.ort = ort;
    }

    public String getVor() {
        return vor;
    }

    public void setVor(String vor) {
        this.vor = vor;
    }

    public String getNach() {
        return nach;
    }

    public void setNach(String nach) {
        this.nach = nach;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }
}
