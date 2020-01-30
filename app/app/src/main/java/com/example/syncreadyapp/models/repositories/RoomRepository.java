package com.example.syncreadyapp.models.repositories;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.syncreadyapp.models.room.ResponseRoom;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.services.SyncReadyMobileDataService;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomRepository {

    private MutableLiveData<Boolean> isNetworkTroubleLiveData = new MutableLiveData<>();

    private SyncReadyMobileDataService syncReadyMobileDataService = RetrofitInstance.getService();

    public MutableLiveData<ResponseRoom> getRooms(String uuid, String roomCode, String bearerToken) {
        final MutableLiveData<ResponseRoom> responseRoomMutableLiveData = new MutableLiveData<>();
        isNetworkTroubleLiveData.setValue(false);

        Call<ResponseRoom> call = null;

        if(uuid != null && roomCode != null) throw new Error("Cannot query with both parameters");

        if (uuid != null) call = syncReadyMobileDataService.getRooms(uuid, bearerToken);
        else if (roomCode != null) call = syncReadyMobileDataService.getRoomByQR(roomCode, bearerToken);

        call.enqueue(new Callback<ResponseRoom>() {
            @Override
            public void onResponse(Call<ResponseRoom> call, Response<ResponseRoom> response) {
                if (response != null) {
                    responseRoomMutableLiveData.setValue(response.body());
                }
                else {
                    responseRoomMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseRoom> call, Throwable t) {
                responseRoomMutableLiveData.setValue(null);
            }
        });

        return responseRoomMutableLiveData;
    }

    public MutableLiveData<JsonObject> validationIsUserInRoom(String userUUID, String roomUUID, String bearerToken) {
        final MutableLiveData<JsonObject> responseRoomMutableLiveData = new MutableLiveData<>();
        isNetworkTroubleLiveData.setValue(false);

        Call<JsonObject> call = syncReadyMobileDataService.validationIsUserInRoom(userUUID, roomUUID, bearerToken);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    responseRoomMutableLiveData.setValue(response.body());
                } else {
                    responseRoomMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseRoomMutableLiveData.setValue(null);
            }
        });

        return responseRoomMutableLiveData;
    }

    public MutableLiveData<ResponseRoom> addUserIntoRoom(String userUUID, String roomUUID, String bearerToken) {
        final MutableLiveData<ResponseRoom> responseRoomMutableLiveData = new MutableLiveData<>();
        isNetworkTroubleLiveData.setValue(null);

        Call<ResponseRoom> call = syncReadyMobileDataService.addUserIntoRoom(userUUID, roomUUID, bearerToken);

        call.enqueue(new Callback<ResponseRoom>() {
            @Override
            public void onResponse(Call<ResponseRoom> call, Response<ResponseRoom> response) {
                if (response.body() != null) {
                    responseRoomMutableLiveData.setValue(response.body());
                } else {
                    responseRoomMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseRoom> call, Throwable t) {
                responseRoomMutableLiveData.setValue(null);
            }
        });

        return responseRoomMutableLiveData;
    }
}
