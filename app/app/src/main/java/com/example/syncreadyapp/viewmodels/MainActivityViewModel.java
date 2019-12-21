package com.example.syncreadyapp.viewmodels;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.models.loginModel.LoginModel;
import com.example.syncreadyapp.models.repositories.UserLoggedRepository;
import com.example.syncreadyapp.models.userLogged.UserLogged;
import com.example.syncreadyapp.views.fragments.RegisterFragment;

public class MainActivityViewModel extends AndroidViewModel {
    // UI Properties
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    // Observables
    public MutableLiveData<LoginModel> loginUserMutableLiveData;

    // Repository declaration
    public UserLoggedRepository userLoggedRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        userLoggedRepository = new UserLoggedRepository(application);
    }

    /* Observables */
    public MutableLiveData<LoginModel> validateUserFields() {
        if (loginUserMutableLiveData == null) {
            loginUserMutableLiveData = new MutableLiveData<>();
        }

        return loginUserMutableLiveData;
    }

    public LiveData<UserLogged> getUserLogged() {
        return userLoggedRepository.getUserLogged(loginUserMutableLiveData.getValue());
    }
    /* --- */

    // UI Click event property
    public void loginButtonClick(View view) {
        LoginModel loginUser = new LoginModel(email.getValue(), password.getValue());
        loginUserMutableLiveData.setValue(loginUser);
    }

    public void registerButtonClick(View view) {

        Fragment fragment = new RegisterFragment();

        ((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.loginRegisterFragmentZone, fragment)
                .addToBackStack("registerFragment")
                .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
                .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_CLOSE )
                .show(fragment)
                .commit();
    }
}
