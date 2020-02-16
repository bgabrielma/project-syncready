package com.example.syncreadyapp.models.alert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAlert {
    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("response")
    @Expose
    private List<Alert> response = null;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public List<Alert> getResponse() {
        return response;
    }

    public void setResponse(List<Alert> response) {
        this.response = response;
    }
}
