package com.example.sqlmvvm.models;

public class UserModel {
    private String id;
    private String firstNanme;
    private String lastName;
    private String time;

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

    public String getFirstNanme() { return firstNanme; }
    public void setFirstNanme(String value) { this.firstNanme = value; }

    public String getLastName() { return lastName; }
    public void setLastName(String value) { this.lastName = value; }

    public String getTime() { return time; }
    public void setTime(String value) { this.time = value; }

    public UserModel(String id, String firstNanme, String lastName, String time){
        this.id = id;
        this.firstNanme = firstNanme;
        this.lastName = lastName;
        this.time = time;
    }
    public UserModel(String firstNanme,String lastName,String time){
        this.firstNanme = firstNanme;
        this.lastName = lastName;
        this.time = time;
    }
    public UserModel(){}
    public UserModel(String id){}
}

