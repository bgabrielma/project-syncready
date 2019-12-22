package com.example.syncreadyapp.models.repositories;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.syncreadyapp.models.loginmodel.LoginModel;
import com.example.syncreadyapp.models.registermodel.RegisterModel;
import com.example.syncreadyapp.models.repositoryresponse.RepositoryResponse;
import com.example.syncreadyapp.models.userinsert.ResponseUserInsert;
import com.example.syncreadyapp.models.userlogged.ResponseUserLogged;
import com.example.syncreadyapp.models.userlogged.UserLogged;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.services.SyncReadyMobileDataService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private MutableLiveData<UserLogged> userLoggedMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ResponseUserInsert> userInsertMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNetworkTroubleLiveData = new MutableLiveData<>();
    private Application application;

    private MutableLiveData<RepositoryResponse> repositoryResponseMutableLiveData = new MutableLiveData<>();

    public UserRepository(@NonNull Application application) {
        this.application = application;
    }

    public MutableLiveData<RepositoryResponse> getUserLogged(LoginModel loginModelInstance) {
        SyncReadyMobileDataService syncReadyMobileDataService = RetrofitInstance.getService();

        isNetworkTroubleLiveData.setValue(false);

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

                repositoryResponseMutableLiveData.setValue(new RepositoryResponse(userLoggedMutableLiveData, isNetworkTroubleLiveData));
            }

            @Override
            public void onFailure(Call<ResponseUserLogged> call, Throwable t) {
                userLoggedMutableLiveData.setValue(null);
                isNetworkTroubleLiveData.setValue(true);

                repositoryResponseMutableLiveData.setValue(new RepositoryResponse(userLoggedMutableLiveData, isNetworkTroubleLiveData));
            }
        });
        return repositoryResponseMutableLiveData;
    }

    public MutableLiveData<RepositoryResponse> getUserInsert(RegisterModel registerModel) {
        SyncReadyMobileDataService syncReadyMobileDataService = RetrofitInstance.getService();

        isNetworkTroubleLiveData.setValue(false);

        Call<ResponseUserInsert> call = syncReadyMobileDataService.register(registerModel);

        call.enqueue(new Callback<ResponseUserInsert>() {
            @Override
            public void onResponse(Call<ResponseUserInsert> call, Response<ResponseUserInsert> response) {
                ResponseUserInsert responseUserInsert = response.body();

                if(responseUserInsert != null) {
                    userInsertMutableLiveData.setValue(responseUserInsert);
                } else {
                    userInsertMutableLiveData.setValue(null);
                }
                repositoryResponseMutableLiveData.setValue(new RepositoryResponse(userInsertMutableLiveData, isNetworkTroubleLiveData));
            }

            @Override
            public void onFailure(Call<ResponseUserInsert> call, Throwable t) {
                userInsertMutableLiveData.setValue(null);
                isNetworkTroubleLiveData.setValue(true);

                repositoryResponseMutableLiveData.setValue(new RepositoryResponse(userInsertMutableLiveData, isNetworkTroubleLiveData));
            }
        });

        return repositoryResponseMutableLiveData;
    }
}
