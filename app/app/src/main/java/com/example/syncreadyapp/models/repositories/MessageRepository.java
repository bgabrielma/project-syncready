package com.example.syncreadyapp.models.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.syncreadyapp.models.messagemodel.ResponseMessage;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.services.SyncReadyMobileDataService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
}
