package com.example.syncreadyapp.services;

import com.example.syncreadyapp.userregistervalidate.ResponseValidateRegister;
import com.example.syncreadyapp.models.loginmodel.LoginModel;
import com.example.syncreadyapp.models.registermodel.RegisterModel;
import com.example.syncreadyapp.models.userinsert.ResponseUserInsert;
import com.example.syncreadyapp.models.userlogged.ResponseUserLogged;
import com.example.syncreadyapp.userregistervalidate.ValidateRegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SyncReadyMobileDataService {
    @POST("/authApi")
    Call<ResponseUserLogged> login(@Body LoginModel body);

    @POST("/user")
    Call<ResponseUserInsert> register(@Body RegisterModel body);

    @POST("/mobileValidateRegister")
    Call<ResponseValidateRegister> mobileValidateRegister(@Body ValidateRegisterModel body);
}