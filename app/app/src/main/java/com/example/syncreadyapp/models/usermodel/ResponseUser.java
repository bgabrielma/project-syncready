package com.example.syncreadyapp.models.usermodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseUser {
    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("data")
    @Expose
    private List<User> data = null;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
