package com.example.syncreadyapp.userregistervalidate;

public class ValidateRegisterModel {
    private String email;
    private String cc;
    private String username;

    public ValidateRegisterModel(String email, String cc, String username) {
        this.email = email;
        this.cc = cc;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
