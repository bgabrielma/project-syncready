package com.example.syncreadyapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.LoginBinding;
import com.example.syncreadyapp.models.loginModel.LoginModel;
import com.example.syncreadyapp.models.userLogged.UserLogged;
import com.example.syncreadyapp.viewmodels.MainActivityViewModel;
import com.example.syncreadyapp.views.HomeActivity;
import com.example.syncreadyapp.views.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private LoginBinding loginBinding;
    private MainActivityViewModel mainActivityViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        loginBinding = DataBindingUtil.inflate(inflater, R.layout.login, container, false);

        loginBinding.setLifecycleOwner(this);

        loginBinding.setMainActivityViewModel(mainActivityViewModel);

        mainActivityViewModel.validateUserFields().observe(getViewLifecycleOwner(), new Observer<LoginModel>() {
            @Override
            public void onChanged(LoginModel loginModel) {

                if (TextUtils.isEmpty(Objects.requireNonNull(loginModel).getEmail())) {
                    loginBinding.txtInputLayoutEmail.setError("Campo em falta");
                    loginBinding.txtInputLayoutEmail.requestFocus();
                } else loginBinding.txtInputLayoutEmail.setErrorEnabled(false);

                if (TextUtils.isEmpty(Objects.requireNonNull(loginModel).getPassword())) {
                    loginBinding.txtInputLayoutPassword.setError("Campo em falta");
                    loginBinding.txtInputLayoutPassword.requestFocus();
                } else loginBinding.txtInputLayoutPassword.setErrorEnabled(false);

                if (!TextUtils.isEmpty(Objects.requireNonNull(loginModel).getEmail()) && !TextUtils.isEmpty(Objects.requireNonNull(loginModel).getPassword())) {
                    mainActivityViewModel.getUserLogged().observe(getViewLifecycleOwner(), observer);
                }
            }
        });

        return loginBinding.getRoot();
    }

    final Observer<UserLogged> observer = new Observer<UserLogged>() {
        @Override
        public void onChanged(UserLogged userLogged) {
            if (userLogged != null) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            } else {
                Snackbar.make(loginBinding.getRoot(), "Email e/ou password inválido(s)", Snackbar.LENGTH_LONG).show();
            }
        }
    };
}
