package com.example.syncreadyapp.userregistervalidate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseValidateRegister {
    @SerializedName("isCCValid")
    @Expose
    private Boolean isCCValid;
    @SerializedName("isEmailValid")
    @Expose
    private Boolean isEmailValid;
    @SerializedName("isUsernameValid")
    @Expose
    private Boolean isUsernameValid;

    public Boolean getIsCCValid() {
        return isCCValid;
    }

    public void setIsCCValid(Boolean isCCValid) {
        this.isCCValid = isCCValid;
    }

    public Boolean getIsEmailValid() {
        return isEmailValid;
    }

    public void setIsEmailValid(Boolean isEmailValid) {
        this.isEmailValid = isEmailValid;
    }

    public Boolean getIsUsernameValid() {
        return isUsernameValid;
    }

    public void setIsUsernameValid(Boolean isUsernameValid) {
        this.isUsernameValid = isUsernameValid;
    }
}
