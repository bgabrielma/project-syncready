package com.example.syncreadyapp.models.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.syncreadyapp.models.alert.ResponseAlert;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.services.SyncReadyMobileDataService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertRepository {

    private MutableLiveData<Boolean> isNetworkTroubleLiveData = new MutableLiveData<>();
    private MutableLiveData<RepositoryResponse> repositoryResponseMutableLiveData = new MutableLiveData<>();

    private SyncReadyMobileDataService syncReadyMobileDataService = RetrofitInstance.getService();

    public MutableLiveData<ResponseAlert> getAlertsByUuidTypeUsers(String uuidTypeUsers, String bearerToken) {
        final MutableLiveData<ResponseAlert> responseAlertMutableLiveData = new MutableLiveData<>();
        isNetworkTroubleLiveData.setValue(false);

        Call<ResponseAlert> call = syncReadyMobileDataService.getAlertsByUuidTypeUsers(uuidTypeUsers, bearerToken);

        call.enqueue(new Callback<ResponseAlert>() {
            @Override
            public void onResponse(Call<ResponseAlert> call, Response<ResponseAlert> response) {
                if (response.body() != null) {
                    responseAlertMutableLiveData.setValue(response.body());
                } else {
                    responseAlertMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseAlert> call, Throwable t) {
                responseAlertMutableLiveData.setValue(null);
            }
        });

        return responseAlertMutableLiveData;
    }
}
