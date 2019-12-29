package com.example.syncreadyapp.services;

import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.userregistervalidate.ResponseValidateRegister;
import com.example.syncreadyapp.models.loginmodel.LoginModel;
import com.example.syncreadyapp.models.registermodel.RegisterModel;
import com.example.syncreadyapp.models.userinsert.ResponseUserInsert;
import com.example.syncreadyapp.models.userlogged.ResponseUserLogged;
import com.example.syncreadyapp.userregistervalidate.ValidateRegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    @GET("/mobile/home}")
    Call<ResponseUser> getMobileHome(@Query("uuid") String uuid, @Header("Authorization") String authHeader);
}