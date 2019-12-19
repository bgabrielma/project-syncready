package com.example.syncreadyapp.viewmodels;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.syncreadyapp.models.LoginModel;

public class UserLoggedViewModel extends ViewModel {
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    private MutableLiveData<LoginModel> userMutableLiveData;

    public MutableLiveData<LoginModel> getUser() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return this.userMutableLiveData;
    }

    public void onClick(View view) {
        LoginModel loginUser = new LoginModel(email.getValue(), password.getValue());
        userMutableLiveData.setValue(loginUser);
    }
}
