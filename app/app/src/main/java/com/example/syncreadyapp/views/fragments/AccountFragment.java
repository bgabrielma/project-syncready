package com.example.syncreadyapp.views.fragments;

import android.content.Intent;
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
import com.example.syncreadyapp.databinding.AccountBinding;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.models.usermodel.User;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.example.syncreadyapp.views.EditProfileActivity;
import com.squareup.picasso.Picasso;

public class AccountFragment extends Fragment {

    private HomeActivityViewModel homeActivityViewModel;
    private AccountBinding accountBinding;
    private View prevView;
    private boolean allowRefresh = false;

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
        configureExtendFloatingActionButtonEditUserInfoData();

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
    public void configureExtendFloatingActionButtonEditUserInfoData() {
        accountBinding.extendFloatingActionButtonEditUserInfoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = AccountFragment.this.getActivity().getIntent().getExtras();

                Intent editProfileActivity = new Intent(AccountFragment.this.getActivity(), EditProfileActivity.class);
                editProfileActivity.putExtras(bundle);
                allowRefresh = true;
                startActivity(editProfileActivity);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (allowRefresh)
        {
            allowRefresh = false;
            homeActivityViewModel.getUserData(homeActivityViewModel.uuidMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue()).observe(this, new Observer<ResponseUser>() {
                @Override
                public void onChanged(ResponseUser responseUser) {
                    User user = responseUser.getData().get(0);
                    accountBinding.setUser(user);
                    Picasso.get()
                            .load(RetrofitInstance.BASE_URL + "public/files/" + user.getImage())
                            .placeholder(R.drawable.loading)
                            .into(accountBinding.groupImage);
                }
            });
        }
    }
}
