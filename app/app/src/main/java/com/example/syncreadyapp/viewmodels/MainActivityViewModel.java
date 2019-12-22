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

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.models.loginmodel.LoginModel;
import com.example.syncreadyapp.models.registermodel.RegisterModel;
import com.example.syncreadyapp.models.repositories.UserRepository;
import com.example.syncreadyapp.models.repositoryresponse.RepositoryResponse;
import com.example.syncreadyapp.views.fragments.RegisterFragment;

public class MainActivityViewModel extends AndroidViewModel {
    // UI Properties - Login
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    // UI Properties - Register
    public MutableLiveData<String> fullname = new MutableLiveData<>();
    public MutableLiveData<String> address = new MutableLiveData<>();
    public MutableLiveData<String> contacto = new MutableLiveData<>();
    public MutableLiveData<String> cc = new MutableLiveData<>();
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> confirmPassword = new MutableLiveData<>();

    // Observables
    public MutableLiveData<LoginModel> loginUserMutableLiveData;
    public MutableLiveData<RegisterModel> registerUserMutableLiveData;

    // Repository declaration
    public UserRepository userRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    /* Observables */
    public MutableLiveData<LoginModel> validateUserFields() {
        if (loginUserMutableLiveData == null) {
            loginUserMutableLiveData = new MutableLiveData<>();
        }

        return loginUserMutableLiveData;
    }

    public MutableLiveData<RegisterModel> validadeRegisterFields() {
        if (registerUserMutableLiveData == null) {
            registerUserMutableLiveData = new MutableLiveData<>();
        }

        return registerUserMutableLiveData;
    }

    public LiveData<RepositoryResponse> getUserLogged() {
        return userRepository.getUserLogged(loginUserMutableLiveData.getValue());
    }

    public LiveData<RepositoryResponse> getUserInsert() {
        return userRepository.getUserInsert(registerUserMutableLiveData.getValue());
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

    public void registerOperationClick(View view) {

        Log.d("Username", "" + username.getValue());
        Log.d("confirmPassword", "" + confirmPassword.getValue());

        RegisterModel registerModel = new RegisterModel(
                fullname.getValue(), address.getValue(), email.getValue(), contacto.getValue(), cc.getValue(), username.getValue(), password.getValue(), confirmPassword.getValue()
        );

        registerUserMutableLiveData.setValue(registerModel);
    }
}
