package com.example.syncreadyapp.models.alert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Alert {
    @SerializedName("pk_alerts")
    @Expose
    private Integer pkAlerts;
    @SerializedName("uuid_alerts")
    @Expose
    private String uuidAlerts;
    @SerializedName("name_alerts")
    @Expose
    private String nameAlerts;
    @SerializedName("alert_text")
    @Expose
    private String alertText;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("until_at")
    @Expose
    private String untilAt;
    @SerializedName("Type_Of_Alert_uuid_type_of_alert")
    @Expose
    private String typeOfAlertUuidTypeOfAlert;
    @SerializedName("type_of_alert_designation")
    @Expose
    private String typeOfAlertDesignation;

    public Integer getPkAlerts() {
        return pkAlerts;
    }

    public void setPkAlerts(Integer pkAlerts) {
        this.pkAlerts = pkAlerts;
    }

    public String getUuidAlerts() {
        return uuidAlerts;
    }

    public void setUuidAlerts(String uuidAlerts) {
        this.uuidAlerts = uuidAlerts;
    }

    public String getNameAlerts() {
        return nameAlerts;
    }

    public void setNameAlerts(String nameAlerts) {
        this.nameAlerts = nameAlerts;
    }

    public String getAlertText() {
        return alertText;
    }

    public void setAlertText(String alertText) {
        this.alertText = alertText;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUntilAt() {
        return untilAt;
    }

    public void setUntilAt(String untilAt) {
        this.untilAt = untilAt;
    }

    public String getTypeOfAlertUuidTypeOfAlert() {
        return typeOfAlertUuidTypeOfAlert;
    }

    public void setTypeOfAlertUuidTypeOfAlert(String typeOfAlertUuidTypeOfAlert) {
        this.typeOfAlertUuidTypeOfAlert = typeOfAlertUuidTypeOfAlert;
    }

    public String getTypeOfAlertDesignation() {
        return typeOfAlertDesignation;
    }

    public void setTypeOfAlertDesignation(String typeOfAlertDesignation) {
        this.typeOfAlertDesignation = typeOfAlertDesignation;
    }
}
