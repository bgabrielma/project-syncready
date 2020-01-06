package com.example.syncreadyapp.models.room;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Room {
    @SerializedName("pk_rooms")
    @Expose
    private Integer pkRooms;
    @SerializedName("uuid_room")
    @Expose
    private String uuidRoom;
    @SerializedName("room_code")
    @Expose
    private String roomCode;
    @SerializedName("name_room")
    @Expose
    private String nameRoom;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("until_at")
    @Expose
    private String untilAt;
    @SerializedName("Datasheet_uuid_datasheet")
    @Expose
    private String datasheetUuidDatasheet;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("ticket_option_designation")
    @Expose
    private String ticketOptionDesignation;
    @SerializedName("uuid_ticket_options")
    @Expose
    private String uuidTicketOptions;

    public Integer getPkRooms() {
        return pkRooms;
    }

    public void setPkRooms(Integer pkRooms) {
        this.pkRooms = pkRooms;
    }

    public String getUuidRoom() {
        return uuidRoom;
    }

    public void setUuidRoom(String uuidRoom) {
        this.uuidRoom = uuidRoom;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUntilAt() {
        return untilAt;
    }

    public void setUntilAt(String untilAt) {
        this.untilAt = untilAt;
    }

    public String getDatasheetUuidDatasheet() {
        return datasheetUuidDatasheet;
    }

    public void setDatasheetUuidDatasheet(String datasheetUuidDatasheet) {
        this.datasheetUuidDatasheet = datasheetUuidDatasheet;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTicketOptionDesignation() {
        return ticketOptionDesignation;
    }

    public void setTicketOptionDesignation(String ticketOptionDesignation) {
        this.ticketOptionDesignation = ticketOptionDesignation;
    }

    public String getUuidTicketOptions() {
        return uuidTicketOptions;
    }

    public void setUuidTicketOptions(String uuidTicketOptions) {
        this.uuidTicketOptions = uuidTicketOptions;
    }
}
