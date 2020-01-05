package com.example.syncreadyapp.models.room;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseRoom {
    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("response")
    @Expose
    private List<Room> response = null;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public List<Room> getResponse() {
        return response;
    }

    public void setResponse(List<Room> response) {
        this.response = response;
    }
}
