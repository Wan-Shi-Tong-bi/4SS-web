package com.example.vi_ii_aufgabe_medikamentenverwaltung;

public class Produkt {
    String hersteller;
    String produkt;
    int menge;
    String datum;

    public Produkt(String hersteller, String produkt, int menge, String datum) {
        this.hersteller = hersteller;
        this.produkt = produkt;
        this.menge = menge;
        this.datum = datum;
    }

    public String getHersteller() {
        return hersteller;
    }

    public void setHersteller(String hersteller) {
        this.hersteller = hersteller;
    }

    public String getProdukt() {
        return produkt;
    }

    public void setProdukt(String produkt) {
        this.produkt = produkt;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    @Override
    public String toString() {
        return "" + produkt;
    }
}
