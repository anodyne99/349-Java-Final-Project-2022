package com.example.exerciseapp;

public class UserInfo {

    private String firstName;
    private String lastName;
    private String zipCode;
    private String weight;
    public UserInfo() {

    }

    public UserInfo(String firstname, String lastname, String wt, String zip){
        firstName = firstname;
        lastName = lastname;
        weight = wt;
        zipCode = zip;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) { this.zipCode = zipCode;}
}
