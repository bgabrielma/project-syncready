package com.example.syncreadyapp.models.userlogged;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLogged {
    @SerializedName("pk_uuid")
    @Expose
    private String pkUuid;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("newToken")
    @Expose
    private String newToken;

    public String getPkUuid() {
        return pkUuid;
    }

    public void setPkUuid(String pkUuid) {
        this.pkUuid = pkUuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewToken() {
        return newToken;
    }

    public void setNewToken(String newToken) {
        this.newToken = newToken;
    }
}
