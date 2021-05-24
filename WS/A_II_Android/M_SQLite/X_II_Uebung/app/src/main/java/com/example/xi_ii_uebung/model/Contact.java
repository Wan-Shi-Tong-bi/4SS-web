package com.example.xi_ii_uebung.model;

public class Contact {
    private String ID;
    private String street;
    private String city;
    private String zipcode;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Contact(String ID, String street, String city, String zipcode) {
        this.ID = ID;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }
}
