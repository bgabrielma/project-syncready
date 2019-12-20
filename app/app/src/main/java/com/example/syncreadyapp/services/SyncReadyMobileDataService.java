package com.example.syncreadyapp.services;

import com.example.syncreadyapp.models.loginModel.LoginModel;
import com.example.syncreadyapp.models.userLogged.ResponseUserLogged;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SyncReadyMobileDataService {
    @POST("/authApi")
    Call<ResponseUserLogged> login(@Body LoginModel body);
}
