package com.example.syncreadyapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;

public class AccountFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // testing observers xD
       HomeActivityViewModel homeActivityViewModel = ViewModelProviders.of(getActivity()).get(HomeActivityViewModel.class);
        homeActivityViewModel.numInscricoes.setValue(69);

        return inflater.inflate(R.layout.account, container, false);
    }
}
