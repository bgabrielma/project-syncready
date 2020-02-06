package com.example.syncreadyapp.models.userupdate;

public class UserUpdate {
    private String fullname;
    private String address;
    private String email;
    private String contacto;
    private String cc;
    private String password;
    private String type;
    private String secretUUIDToUpdate;

    public UserUpdate(String fullname, String address, String email, String contacto, String cc, String password, String type, String secretUUIDToUpdate) {
        this.fullname = fullname;
        this.address = address;
        this.email = email;
        this.contacto = contacto;
        this.cc = cc;
        this.password = password;
        this.type = type;
        this.secretUUIDToUpdate = secretUUIDToUpdate;
    }

    public UserUpdate() { }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSecretUUIDToUpdate() {
        return secretUUIDToUpdate;
    }

    public void setSecretUUIDToUpdate(String secretUUIDToUpdate) {
        this.secretUUIDToUpdate = secretUUIDToUpdate;
    }
}
