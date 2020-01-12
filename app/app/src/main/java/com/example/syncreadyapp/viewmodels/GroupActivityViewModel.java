package com.example.syncreadyapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.syncreadyapp.models.usermodel.User;

public class GroupActivityViewModel extends AndroidViewModel {
    public MutableLiveData<String> roomUuid = new MutableLiveData<>();
    public MutableLiveData<String> roomTitle = new MutableLiveData<>();
    public MutableLiveData<String> roomImage = new MutableLiveData<>();
    public GroupActivityViewModel(@NonNull Application application) {
        super(application);
    }
}
