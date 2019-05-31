package com.coba.jstore_android_reja.Models;

public class Customer {
    private String name;
    private String email;
    private String username;
    private String password;
    private int id;
    private String birthdate;

    public Customer(String name, String email, String username, String password, int id, String birthdate) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.id = id;
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
