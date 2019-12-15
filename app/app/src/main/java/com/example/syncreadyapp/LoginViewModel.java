package com.example.syncreadyapp;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.syncreadyapp.Models.LoginModel;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    private MutableLiveData<LoginModel> userMutableLiveData;

    public MutableLiveData<LoginModel> getUser() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return this.userMutableLiveData;
    }

    public void onClick(View view) {

        LoginModel loginUser = new LoginModel(username.getValue(), password.getValue());

        userMutableLiveData.setValue(loginUser);
    }
}
