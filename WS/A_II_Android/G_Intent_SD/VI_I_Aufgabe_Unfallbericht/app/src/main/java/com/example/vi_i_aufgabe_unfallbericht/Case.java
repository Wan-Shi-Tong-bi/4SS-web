package com.example.vi_i_aufgabe_unfallbericht;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Case implements Serializable {
    private String date;
    private String time;
    private String ort;
    private String plz;
    private int nr;
    private boolean verletzte;
    private boolean schaeden;

    private ArrayList<Witness> witnesses;

    public Case() {

    }

    public Case(String date, String time, String ort, String plz, int nr, boolean verletzte, boolean schaeden) {
        this.date = date;
        this.time = time;
        this.ort = ort;
        this.plz = plz;
        this.nr = nr;
        this.verletzte = verletzte;
        this.schaeden = schaeden;
        this.witnesses = new ArrayList<>();
    }

    public Case(String date, String time, String ort, String plz, int nr, boolean verletzte, boolean schaeden, ArrayList<Witness> witnesses) {
        this.date = date;
        this.time = time;
        this.ort = ort;
        this.plz = plz;
        this.nr = nr;
        this.verletzte = verletzte;
        this.schaeden = schaeden;
        this.witnesses = witnesses;
    }

    public void addWitnesses(Witness w){
        this.witnesses.add(w);
    }

    public ArrayList<Witness> getWitnesses() {
        return witnesses;
    }

    public void setWitnesses(ArrayList<Witness> witnesses) {
        this.witnesses = witnesses;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public boolean isVerletzte() {
        return verletzte;
    }

    public void setVerletzte(boolean verletzte) {
        this.verletzte = verletzte;
    }

    public boolean isSchaeden() {
        return schaeden;
    }

    public void setSchaeden(boolean schaeden) {
        this.schaeden = schaeden;
    }

    @Override
    public String toString() {
        String wit = "";
        for(Witness w : witnesses){
            wit += "%" + w.toString() ;
        }

        return  ""+ date + ';' +
                time + ';' +
                ort + ';' +
                plz + ';' +
                nr + ';' +
                verletzte + ';' +
                schaeden + wit;
    }

    public static Case desarialize(String zeile){
        String[] arr1 = zeile.split("%");
        ArrayList<Witness> wit = new ArrayList<>();
        if(arr1.length > 1){
            for(int i = 1; i < arr1.length; i++){
                wit.add(Witness.deserialize(arr1[i]));
            }
        }

        String[] arr2 = arr1[0].split(";");
        return new Case(arr2[0],
                arr2[1],
                arr2[2],
                arr2[3],
                Integer.parseInt(arr2[4]),
                Boolean.parseBoolean(arr2[5]),
                Boolean.parseBoolean(arr2[6]), wit);

        }
}
