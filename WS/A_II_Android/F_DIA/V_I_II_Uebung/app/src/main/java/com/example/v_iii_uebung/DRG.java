package com.example.v_iii_uebung;

public class DRG {
    private String name;
    private String bezeichnung;
    private Double vwd;
    private int abschlag;
    private int zusatz;
    private int wert1;
    private int wert2;

    public int getWert1() {
        return wert1;
    }

    public void setWert1(int wert1) {
        this.wert1 = wert1;
    }

    public int getWert2() {
        return wert2;
    }

    public void setWert2(int wert2) {
        this.wert2 = wert2;
    }

    public DRG(String name, String bezeichnung, Double vwd, int abschlag, int zusatz, int wert1, int wert2) {
        this.name = name;
        this.bezeichnung = bezeichnung;
        this.vwd = vwd;
        this.abschlag = abschlag;
        this.zusatz = zusatz;
        this.wert1 = wert1;
        this.wert2 = wert2;
    }

    public DRG(String name, String bezeichnung, Double vwd, int abschlag, int zusatz) {
        this.name = name;
        this.bezeichnung = bezeichnung;
        this.vwd = vwd;
        this.abschlag = abschlag;
        this.zusatz = zusatz;
    }

    public DRG() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Double getVwd() {
        return vwd;
    }

    public void setVwd(Double vwd) {
        this.vwd = vwd;
    }

    public int getAbschlag() {
        return abschlag;
    }

    public void setAbschlag(int abschlag) {
        this.abschlag = abschlag;
    }

    public int getZusatz() {
        return zusatz;
    }

    public void setZusatz(int zusatz) {
        this.zusatz = zusatz;
    }

    @Override
    public String toString() {
        return "" + name;
    }
}
