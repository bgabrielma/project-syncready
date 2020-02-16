package com.example.syncreadyapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.syncreadyapp.models.messagemodel.ResponseMessage;
import com.example.syncreadyapp.models.repositories.MessageRepository;
import com.example.syncreadyapp.models.repositories.UserRepository;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.models.usermodel.User;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class GroupActivityViewModel extends AndroidViewModel {
    public MutableLiveData<String> roomUuid = new MutableLiveData<>();
    public MutableLiveData<String> roomTitle = new MutableLiveData<>();
    public MutableLiveData<String> roomImage = new MutableLiveData<>();
    public MutableLiveData<String> roomDesignation = new MutableLiveData<>();

    public UserRepository userRepository;
    public MessageRepository messageRepository;

    public GroupActivityViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
        messageRepository = new MessageRepository();
    }

    public LiveData<ResponseUser> getUsersByRoom(String roomUUID, String bearerToken) {
        return userRepository.getUsersByRoom(roomUUID, bearerToken);
    }

    public LiveData<ResponseMessage> getMessagesByRoom(String roomUUID, String bearerToken, boolean isLast) {
        return messageRepository.getMessagesByRoom(roomUUID, bearerToken, isLast);
    }

    public LiveData<JsonObject> uploadImage(MultipartBody.Part file, RequestBody description, String bearerToken) {
        return messageRepository.uploadImage(file, description, bearerToken);
    }
}
