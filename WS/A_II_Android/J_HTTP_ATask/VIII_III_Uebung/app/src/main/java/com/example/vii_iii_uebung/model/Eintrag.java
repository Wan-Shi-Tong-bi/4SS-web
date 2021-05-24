package com.example.vii_iii_uebung.model;

import java.util.Date;

public class Eintrag {
    private String date;
    private String eintrag;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEintrag() {
        return eintrag;
    }

    public void setEintrag(String eintrag) {
        this.eintrag = eintrag;
    }

    public Eintrag(String date, String eintrag) {
        this.date = date;
        this.eintrag = eintrag;
    }

    @Override
    public String toString() {
        return "Eintrag{" + "date=" + date + ", eintrag=" + eintrag + '}';
    }


}
