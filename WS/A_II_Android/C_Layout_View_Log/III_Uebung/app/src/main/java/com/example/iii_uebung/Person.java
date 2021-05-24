package com.example.iii_uebung;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private String vorname;
    private String nachname;
    private Typ typ;


    public Person() {
    }

    public Person(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public Person(String vorname, String nachname, Typ typ) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.typ = typ;
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

    public Typ getTyp() {
        return typ;
    }

    public void setTyp(Typ typ) {
        this.typ = typ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vorname);
        dest.writeString(nachname);
        dest.writeValue(typ);
    }
}
