package com.example.v_iii_uebung;

public class DRG {
    private String name;
    private String bezeichnung;
    private Double vwd;
    private int abschlag;
    private int zusatz;

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
