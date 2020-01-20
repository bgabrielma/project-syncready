package com.example.syncreadyapp.models.messagemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageModel {
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("pk_uuid")
    @Expose
    private String pkUuid;
    @SerializedName("pk_message")
    @Expose
    private Integer pkMessage;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("update_at")
    @Expose
    private String updateAt;
    @SerializedName("uuid_message")
    @Expose
    private String uuidMessage;
    @SerializedName("Status_Message_uuid_status_message")
    @Expose
    private String statusMessageUuidStatusMessage;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("sent_at")
    @Expose
    private String sentAt;

    public MessageModel() { }

    public MessageModel(String fullname, String pkUuid, Integer pkMessage, String content, String createdAt, String updateAt, String uuidMessage, String statusMessageUuidStatusMessage, String type, String sentAt) {
        this.fullname = fullname;
        this.pkUuid = pkUuid;
        this.pkMessage = pkMessage;
        this.content = content;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.uuidMessage = uuidMessage;
        this.statusMessageUuidStatusMessage = statusMessageUuidStatusMessage;
        this.type = type;
        this.sentAt = sentAt;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPkUuid() {
        return pkUuid;
    }

    public void setPkUuid(String pkUuid) {
        this.pkUuid = pkUuid;
    }

    public Integer getPkMessage() {
        return pkMessage;
    }

    public void setPkMessage(Integer pkMessage) {
        this.pkMessage = pkMessage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getUuidMessage() {
        return uuidMessage;
    }

    public void setUuidMessage(String uuidMessage) {
        this.uuidMessage = uuidMessage;
    }

    public String getStatusMessageUuidStatusMessage() {
        return statusMessageUuidStatusMessage;
    }

    public void setStatusMessageUuidStatusMessage(String statusMessageUuidStatusMessage) {
        this.statusMessageUuidStatusMessage = statusMessageUuidStatusMessage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }
}