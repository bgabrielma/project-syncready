package com.example.syncreadyapp.models.userinsert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUserInsert {
    @SerializedName("lastIdInserted")
    @Expose
    private Integer lastIdInserted;
    @SerializedName("ok")
    @Expose
    private Boolean ok;

    public Integer getLastIdInserted() {
        return lastIdInserted;
    }

    public void setLastIdInserted(Integer lastIdInserted) {
        this.lastIdInserted = lastIdInserted;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }
}
