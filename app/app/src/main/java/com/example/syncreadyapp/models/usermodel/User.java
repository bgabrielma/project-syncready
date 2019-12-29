package com.example.syncreadyapp.models.usermodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("pk_user")
    @Expose
    private Integer pkUser;
    @SerializedName("pk_uuid")
    @Expose
    private String pkUuid;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("citizencard")
    @Expose
    private String citizencard;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("update_at")
    @Expose
    private String updateAt;
    @SerializedName("Type_Of_User_uuid_type_of_users")
    @Expose
    private String typeOfUserUuidTypeOfUsers;
    @SerializedName("type")
    @Expose
    private String type;

    public Integer getPkUser() {
        return pkUser;
    }

    public void setPkUser(Integer pkUser) {
        this.pkUser = pkUser;
    }

    public String getPkUuid() {
        return pkUuid;
    }

    public void setPkUuid(String pkUuid) {
        this.pkUuid = pkUuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCitizencard() {
        return citizencard;
    }

    public void setCitizencard(String citizencard) {
        this.citizencard = citizencard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getTypeOfUserUuidTypeOfUsers() {
        return typeOfUserUuidTypeOfUsers;
    }

    public void setTypeOfUserUuidTypeOfUsers(String typeOfUserUuidTypeOfUsers) {
        this.typeOfUserUuidTypeOfUsers = typeOfUserUuidTypeOfUsers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
