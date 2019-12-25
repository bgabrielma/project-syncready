package com.example.syncreadyapp.models.repositories;

import androidx.lifecycle.MutableLiveData;

public class RepositoryResponse {
    private MutableLiveData<?> genericMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNetworkLiveData = new MutableLiveData<>();

    public RepositoryResponse(MutableLiveData<?> genericMutableLiveData, MutableLiveData<Boolean> isNetworkLiveData) {
        this.genericMutableLiveData = genericMutableLiveData;
        this.isNetworkLiveData = isNetworkLiveData;
    }

    public MutableLiveData<?> getGenericMutableLiveData() {
        return genericMutableLiveData;
    }

    public void setGenericMutableLiveData(MutableLiveData<?> genericMutableLiveData) {
        this.genericMutableLiveData = genericMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsNetworkLiveData() {
        return isNetworkLiveData;
    }

    public void setIsNetworkLiveData(MutableLiveData<Boolean> isNetworkLiveData) {
        this.isNetworkLiveData = isNetworkLiveData;
    }
}
