package com.example.syncreadyapp.models.repositories;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.syncreadyapp.models.loginModel.LoginModel;
import com.example.syncreadyapp.models.userLogged.ResponseUserLogged;
import com.example.syncreadyapp.models.userLogged.UserLogged;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.services.SyncReadyMobileDataService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoggedRepository {
    private MutableLiveData<UserLogged> userLoggedMutableLiveData = new MutableLiveData<>();
    private Application application;

    public UserLoggedRepository(@NonNull Application application) {
        this.application = application;
    }

    public MutableLiveData<UserLogged> getUserLogged(LoginModel loginModelInstance) {
        SyncReadyMobileDataService syncReadyMobileDataService = RetrofitInstance.getService();

        Call<ResponseUserLogged> call = syncReadyMobileDataService.login(loginModelInstance);
        call.enqueue(new Callback<ResponseUserLogged>() {
            @Override
            public void onResponse(Call<ResponseUserLogged> call, Response<ResponseUserLogged> response) {
                ResponseUserLogged responseUserLogged = response.body();
                if (responseUserLogged != null) {
                    userLoggedMutableLiveData.setValue(responseUserLogged.getUserLogged());
                } else {
                    userLoggedMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseUserLogged> call, Throwable t) {
            }
        });
        return userLoggedMutableLiveData;
    }
}
