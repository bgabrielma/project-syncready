package com.example.syncreadyapp.services;

import com.example.syncreadyapp.models.homedata.ResponseHomeData;
import com.example.syncreadyapp.models.messagemodel.ResponseMessage;
import com.example.syncreadyapp.models.room.ResponseRoom;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.userregistervalidate.ResponseValidateRegister;
import com.example.syncreadyapp.models.loginmodel.LoginModel;
import com.example.syncreadyapp.models.registermodel.RegisterModel;
import com.example.syncreadyapp.models.userinsert.ResponseUserInsert;
import com.example.syncreadyapp.models.userlogged.ResponseUserLogged;
import com.example.syncreadyapp.userregistervalidate.ValidateRegisterModel;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface SyncReadyMobileDataService {
    @POST("/authApi")
    Call<ResponseUserLogged> login(@Body LoginModel body);

    @POST("/user")
    Call<ResponseUserInsert> register(@Body RegisterModel body);

    @POST("/mobileValidateRegister")
    Call<ResponseValidateRegister> mobileValidateRegister(@Body ValidateRegisterModel body);

    @GET("/user")
    Call<ResponseUser> getUser(@Query("uuid") String uuid, @Header("Authorization") String authHeader);

    @GET("/mobile/home")
    Call<ResponseHomeData> getMobileHome(@Query("uuid") String uuid, @Header("Authorization") String authHeader);

    @GET("/room")
    Call<ResponseRoom> getRooms(@Query("userUUID") String uuid, @Header("Authorization") String authHeader);

    @GET("/room")
    Call<ResponseRoom> getRoomByQR(@Query("roomCode") String roomCode, @Header("Authorization") String authHeader);

    @GET("/user/room")
    Call<ResponseUser> getUsersByRoom(@Query("roomUUID") String roomUUID, @Header("Authorization") String authHeader);

    @GET("/message")
    Call<ResponseMessage> getMessagesByRoom(@Query("roomUUID") String roomUUID, @Header("Authorization") String authHeader);

    @GET("/verify/room")
    Call<JsonObject> validationIsUserInRoom(@Query("userUUID") String userUUID, @Query("roomUUID") String roomUUID, @Header("Authorization") String authHeader);

    @GET("/user/room/add")
    Call<ResponseRoom> addUserIntoRoom(@Query("userUUID") String userUUID, @Query("roomUUID") String roomUUID, @Header("Authorization") String authHeader);

    @Multipart
    @POST("/mobile/upload")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file, @Part("newFile") RequestBody requestBody, @Header("Authorization") String authHeader);
}