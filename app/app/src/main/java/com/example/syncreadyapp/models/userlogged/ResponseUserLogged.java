package com.example.syncreadyapp.models.userlogged;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUserLogged {
    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("response")
    @Expose
    private UserLogged userLogged;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public UserLogged getUserLogged() {
        return userLogged;
    }

    public void setUserLogged(UserLogged userLogged) {
        this.userLogged = userLogged;
    }
}
