package com.example.syncreadyapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.HomeBinding;
import com.example.syncreadyapp.models.homedata.ResponseHomeData;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;

public class HomeFragment extends Fragment {

    private View prevView;
    private HomeBinding homeBinding;
    private HomeActivityViewModel homeActivityViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (prevView != null) {
            return prevView;
        }

        homeActivityViewModel = ViewModelProviders.of(getActivity()).get(HomeActivityViewModel.class);

        homeBinding = DataBindingUtil.inflate(inflater, R.layout.home, container, false);
        homeBinding.setHomeActivityViewModel(homeActivityViewModel);

        homeBinding.setLifecycleOwner(getActivity());

        homeActivityViewModel.getHomeData(homeActivityViewModel.uuidMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                .observe(getViewLifecycleOwner(), getHomeDataBinding);

        prevView = homeBinding.getRoot();

        return homeBinding.getRoot();
    }

    private final Observer<ResponseHomeData> getHomeDataBinding = new Observer<ResponseHomeData>() {
        @Override
        public void onChanged(ResponseHomeData responseHomeData) {
            homeActivityViewModel.numInscricoes.setValue(responseHomeData.getGroups());
            homeActivityViewModel.mensagensEnvidas.setValue(responseHomeData.getMessages());
            homeActivityViewModel.gruposAtivos.setValue(responseHomeData.getActiveGroups());
        }
    };
}