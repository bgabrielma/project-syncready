package com.example.syncreadyapp.viewmodels;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.syncreadyapp.models.alert.ResponseAlert;
import com.example.syncreadyapp.models.homedata.ResponseHomeData;
import com.example.syncreadyapp.models.repositories.AlertRepository;
import com.example.syncreadyapp.models.repositories.RoomRepository;
import com.example.syncreadyapp.models.repositories.UserRepository;
import com.example.syncreadyapp.models.room.ResponseRoom;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.models.usermodel.User;
import com.example.syncreadyapp.views.ScannerActivity;
import com.google.gson.JsonObject;

public class HomeActivityViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private RoomRepository roomRepository;
    private AlertRepository alertRepository;

    public MutableLiveData<String> uuidMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> tokenAccessMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> roomCodeAccessMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> roomUuidAccessMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    // UI
    /* Home Content */
    public MutableLiveData<Integer> numInscricoes = new MutableLiveData<>();
    public MutableLiveData<Integer> mensagensEnvidas = new MutableLiveData<>();
    public MutableLiveData<Integer> gruposAtivos = new MutableLiveData<>();

    public HomeActivityViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
        roomRepository = new RoomRepository();
        alertRepository = new AlertRepository();
    }

    public LiveData<ResponseUser> getUserData(String uuid, String bearerToken) {
        return userRepository.getUserDataByUuid(uuid, bearerToken);
    }

    public LiveData<ResponseHomeData> getHomeData(String uuid, String bearerToken) {
        return userRepository.getHomeData(uuid, bearerToken);
    }

    public LiveData<ResponseRoom> getRooms(String uuid, String bearerToken) {
        return roomRepository.getRooms(uuid, null, bearerToken);
    }

    public LiveData<ResponseRoom> getRoomByQR(String roomCode, String bearerToken) {
        return roomRepository.getRooms(null, roomCode, bearerToken);
    }

    public LiveData<JsonObject> validationUserInRoom(String userUUID, String roomUUID, String bearerToken) {
        return roomRepository.validationIsUserInRoom(userUUID, roomUUID, bearerToken);
    }

    public LiveData<ResponseRoom> addUserIntoRoom(String userUUID, String roomUUID, String bearerToken) {
        return roomRepository.addUserIntoRoom(userUUID, roomUUID, bearerToken);
    }

    public LiveData<JsonObject> getUpdateUserImage(String image, String userUUID, String bearerToken) {
        return userRepository.getUpdateUserImage(image, userUUID, bearerToken);
    }

    public LiveData<JsonObject> getUpdateRoomImage(String image, String userUUID, String bearerToken) {
        return roomRepository.getUpdateRoomImage(image, userUUID, bearerToken);
    }

    public LiveData<ResponseAlert> getAlertsByUuidTypeUsers(String uuidTypeUsers, String bearerToken) {
        return alertRepository.getAlertsByUuidTypeUsers(uuidTypeUsers, bearerToken);
    }

    public void OnEnterNewRoomButtonClick(View view) {
        Intent scannerIntent = new Intent(view.getContext(), ScannerActivity.class);
        Bundle userAuthInformations = new Bundle();

        userAuthInformations.putString("sycnready_user_uuid", uuidMutableLiveData.getValue());
        userAuthInformations.putString("syncready_user_token_access", tokenAccessMutableLiveData.getValue().split(" ")[1]);
        scannerIntent.putExtras(userAuthInformations);

        view.getContext().startActivity(scannerIntent);
    }
}
