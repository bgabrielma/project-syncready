package com.example.syncreadyapp.models.messagemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMessage {
    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("data")
    @Expose
    private List<MessageModel> data = null;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public List<MessageModel> getData() {
        return data;
    }

    public void setData(List<MessageModel> data) {
        this.data = data;
    }
}
