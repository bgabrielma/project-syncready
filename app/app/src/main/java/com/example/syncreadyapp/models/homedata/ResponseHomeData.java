package com.example.syncreadyapp.models.homedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseHomeData {
    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("messages")
    @Expose
    private Integer messages;
    @SerializedName("groups")
    @Expose
    private Integer groups;
    @SerializedName("activeGroups")
    @Expose
    private Integer activeGroups;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Integer getMessages() {
        return messages;
    }

    public void setMessages(Integer messages) {
        this.messages = messages;
    }

    public Integer getGroups() {
        return groups;
    }

    public void setGroups(Integer groups) {
        this.groups = groups;
    }

    public Integer getActiveGroups() {
        return activeGroups;
    }

    public void setActiveGroups(Integer activeGroups) {
        this.activeGroups = activeGroups;
    }
}
