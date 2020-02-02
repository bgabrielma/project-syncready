package com.example.syncreadyapp.models.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.syncreadyapp.models.messagemodel.ResponseMessage;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.services.SyncReadyMobileDataService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class MessageRepository {
    private MutableLiveData<Boolean> isNetworkTroubleLiveData = new MutableLiveData<>();

    private SyncReadyMobileDataService syncReadyMobileDataService = RetrofitInstance.getService();

    public MutableLiveData<ResponseMessage> getMessagesByRoom(String roomUUID, String bearerCode) {
        final MutableLiveData<ResponseMessage> responseMessageMutableLiveData = new MutableLiveData<>();
        Call<ResponseMessage> call = syncReadyMobileDataService.getMessagesByRoom(roomUUID, bearerCode);

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.body() != null) {
                    responseMessageMutableLiveData.setValue(response.body());
                } else {
                    responseMessageMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                responseMessageMutableLiveData.setValue(null);
            }
        });

        return responseMessageMutableLiveData;
    }

    public MutableLiveData<ResponseBody> uploadImage(MultipartBody.Part file, RequestBody description, String bearerToken) {
        final MutableLiveData<ResponseBody> responseBodyMutableLiveData = new MutableLiveData<>();

        Call<ResponseBody> responseBodyCall = syncReadyMobileDataService.uploadImage(file, description, bearerToken);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    Log.d("response", response.message());
                } else {
                    responseBodyMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                responseBodyMutableLiveData.setValue(null);
            }
        });

        return responseBodyMutableLiveData;
    }
}
