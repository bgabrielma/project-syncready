package com.example.syncreadyapp.models.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.syncreadyapp.models.homedata.ResponseHomeData;
import com.example.syncreadyapp.models.loginmodel.LoginModel;
import com.example.syncreadyapp.models.registermodel.RegisterModel;
import com.example.syncreadyapp.models.userinsert.ResponseUserInsert;
import com.example.syncreadyapp.models.userlogged.ResponseUserLogged;
import com.example.syncreadyapp.models.userlogged.UserLogged;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.models.userupdate.UserUpdate;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.services.SyncReadyMobileDataService;
import com.example.syncreadyapp.userregistervalidate.ResponseValidateRegister;
import com.example.syncreadyapp.userregistervalidate.ValidateRegisterModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private MutableLiveData<Boolean> isNetworkTroubleLiveData = new MutableLiveData<>();
    private MutableLiveData<RepositoryResponse> repositoryResponseMutableLiveData = new MutableLiveData<>();

    private SyncReadyMobileDataService syncReadyMobileDataService = RetrofitInstance.getService();

    public MutableLiveData<RepositoryResponse> getUserLogged(LoginModel loginModelInstance) {
        final MutableLiveData<UserLogged> userLoggedMutableLiveData = new MutableLiveData<>();
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

    public MutableLiveData<ResponseUserInsert> getUserInsert(RegisterModel registerModel) {
        final MutableLiveData<ResponseUserInsert> userInsertMutableLiveData = new MutableLiveData<>();
        isNetworkTroubleLiveData.setValue(false);

        Call<ResponseUserInsert> call = syncReadyMobileDataService.register(registerModel);

        call.enqueue(new Callback<ResponseUserInsert>() {
            @Override
            public void onResponse(Call<ResponseUserInsert> call, Response<ResponseUserInsert> response) {
                ResponseUserInsert responseUserInsert = response.body();

                if (responseUserInsert != null) {
                    userInsertMutableLiveData.setValue(responseUserInsert);
                } else {
                    userInsertMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseUserInsert> call, Throwable t) {
                userInsertMutableLiveData.setValue(null);
            }
        });

        return userInsertMutableLiveData;
    }

    public MutableLiveData<JsonObject> getUserUpdate(UserUpdate userUpdate) {
        final MutableLiveData<JsonObject> jsonObjectMutableLiveData = new MutableLiveData<>();
        isNetworkTroubleLiveData.setValue(false);

        Call<JsonObject> call = syncReadyMobileDataService.update(userUpdate);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject responseUserInsert = response.body();

                if (responseUserInsert != null) {
                    jsonObjectMutableLiveData.setValue(responseUserInsert);
                } else {
                    jsonObjectMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                jsonObjectMutableLiveData.setValue(null);
            }
        });

        return jsonObjectMutableLiveData;
    }

    public MutableLiveData<RepositoryResponse> getValidateRegister(ValidateRegisterModel validateRegisterModel) {
        final MutableLiveData<ResponseValidateRegister> validateRegisterModelMutableLiveData = new MutableLiveData<>();
        isNetworkTroubleLiveData.setValue(false);

        Call<ResponseValidateRegister> call = syncReadyMobileDataService.mobileValidateRegister(validateRegisterModel);

        call.enqueue(new Callback<ResponseValidateRegister>() {
            @Override
            public void onResponse(Call<ResponseValidateRegister> call, Response<ResponseValidateRegister> response) {
                ResponseValidateRegister responseValidateRegister = response.body();

                if (responseValidateRegister != null) {
                    validateRegisterModelMutableLiveData.setValue(responseValidateRegister);
                } else {
                    validateRegisterModelMutableLiveData.setValue(null);
                }
                repositoryResponseMutableLiveData.setValue(new RepositoryResponse(validateRegisterModelMutableLiveData, isNetworkTroubleLiveData));
            }

            @Override
            public void onFailure(Call<ResponseValidateRegister> call, Throwable t) {
                validateRegisterModelMutableLiveData.setValue(null);
                isNetworkTroubleLiveData.setValue(true);

                repositoryResponseMutableLiveData.setValue(new RepositoryResponse(validateRegisterModelMutableLiveData, isNetworkTroubleLiveData));
            }
        });

        return repositoryResponseMutableLiveData;
    }

    public MutableLiveData<ResponseUser> getUserDataByUuid(String uuid, String bearerToken) {
        isNetworkTroubleLiveData.setValue(false);
        return fetchUsers(syncReadyMobileDataService.getUser(uuid, bearerToken));
    }

    public MutableLiveData<ResponseHomeData> getHomeData(String uuid, String bearerToken) {
        final MutableLiveData<ResponseHomeData> responseHomeDataMutableLiveData = new MutableLiveData<>();
        isNetworkTroubleLiveData.setValue(false);
        Call<ResponseHomeData> call = syncReadyMobileDataService.getMobileHome(uuid, bearerToken);

        call.enqueue(new Callback<ResponseHomeData>() {
            @Override
            public void onResponse(Call<ResponseHomeData> call, Response<ResponseHomeData> response) {
                ResponseHomeData responseHomeData = response.body();

                if (responseHomeData != null) {
                    responseHomeDataMutableLiveData.setValue(responseHomeData);
                } else {
                    responseHomeDataMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseHomeData> call, Throwable t) {
                responseHomeDataMutableLiveData.setValue(null);
            }
        });

        return responseHomeDataMutableLiveData;
    }

    public MutableLiveData<ResponseUser> getUsersByRoom(String roomUUID, String bearerToken) {
        isNetworkTroubleLiveData.setValue(false);
        return fetchUsers(syncReadyMobileDataService.getUsersByRoom(roomUUID, bearerToken));
    }

    private MutableLiveData<ResponseUser> fetchUsers(Call<ResponseUser> call) {
        final MutableLiveData<ResponseUser> responseUserMutableLiveData = new MutableLiveData<>();

        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                ResponseUser responseUser = response.body();

                if (responseUser != null) {
                    responseUserMutableLiveData.setValue(responseUser);
                } else {
                    responseUserMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                responseUserMutableLiveData.setValue(null);
            }
        });

        return responseUserMutableLiveData;
    }

    public MutableLiveData<JsonObject> getUpdateUserImage(String image, String uuid, String bearerToken) {
        final MutableLiveData<JsonObject> jsonObjectMutableLiveData = new MutableLiveData<>();
        isNetworkTroubleLiveData.setValue(false);

        Call<JsonObject> call = syncReadyMobileDataService.updateUserImage(image, uuid, bearerToken);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject responseUser = response.body();

                if (responseUser != null) {
                    jsonObjectMutableLiveData.setValue(responseUser);
                } else {
                    jsonObjectMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                jsonObjectMutableLiveData.setValue(null);
            }
        });

        return jsonObjectMutableLiveData;
    }
}
