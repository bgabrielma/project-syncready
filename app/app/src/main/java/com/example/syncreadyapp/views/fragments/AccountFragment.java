package com.example.syncreadyapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.AccountBinding;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.squareup.picasso.Picasso;

public class AccountFragment extends Fragment {

    private HomeActivityViewModel homeActivityViewModel;
    private AccountBinding accountBinding;
    private View prevView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (prevView != null) {
            return prevView;
        }

        homeActivityViewModel = ViewModelProviders.of(getActivity()).get(HomeActivityViewModel.class);
        accountBinding = DataBindingUtil.inflate(inflater, R.layout.account, container, false);

        accountBinding.setUser(homeActivityViewModel.userMutableLiveData.getValue());
        accountBinding.setLifecycleOwner(getActivity());
        configureUserImage();

        prevView = accountBinding.getRoot();

        return accountBinding.getRoot();
    }

    public void configureUserImage() {

        // set image
        Picasso.get()
                .load(RetrofitInstance.BASE_URL + "public/files/" + homeActivityViewModel.userMutableLiveData.getValue().getImage())
                .placeholder(R.drawable.loading)
                .into(accountBinding.groupImage);

    }
}
