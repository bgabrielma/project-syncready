package com.example.syncreadyapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.syncreadyapp.models.homedata.ResponseHomeData;
import com.example.syncreadyapp.models.repositories.UserRepository;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.models.usermodel.User;

public class HomeActivityViewModel extends AndroidViewModel {
    private Application application;
    private UserRepository userRepository;

    public MutableLiveData<String> uuidMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> tokenAccessMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    // UI
    /* Home Content */
    public MutableLiveData<Integer> numInscricoes = new MutableLiveData<>();
    public MutableLiveData<Integer> mensagensEnvidas = new MutableLiveData<>();
    public MutableLiveData<Integer> gruposAtivos = new MutableLiveData<>();

    public HomeActivityViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        userRepository = new UserRepository(application);
    }

    public LiveData<ResponseUser> getUserData(String uuid, String bearerToken) {
        return userRepository.getUserDataByUuid(uuid, bearerToken);
    }

    public LiveData<ResponseHomeData> getHomeData(String uuid, String bearerToken) {
        return userRepository.getHomeData(uuid, bearerToken);
    }
}
