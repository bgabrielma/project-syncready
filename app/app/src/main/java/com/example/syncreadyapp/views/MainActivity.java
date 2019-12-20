package com.example.syncreadyapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.LoginBinding;
import com.example.syncreadyapp.models.loginModel.LoginModel;
import com.example.syncreadyapp.models.userLogged.ResponseUserLogged;
import com.example.syncreadyapp.models.userLogged.UserLogged;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.services.SyncReadyMobileDataService;
import com.example.syncreadyapp.viewmodels.MainActivityViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private LoginBinding loginBinding;
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        loginBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.login);
        loginBinding.setLifecycleOwner(this);

        loginBinding.setMainActivityViewModel(mainActivityViewModel);

        final Observer<UserLogged> observer = new Observer<UserLogged>() {
            @Override
            public void onChanged(UserLogged userLogged) {
                if (userLogged != null) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(loginBinding.getRoot(), "Email e/ou password inválido(s)", Snackbar.LENGTH_LONG).show();
                }
            }
        };

        mainActivityViewModel.validateUserFields().observe(this, new Observer<LoginModel>() {
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

                    mainActivityViewModel.getUserLogged().observe(loginBinding.getLifecycleOwner(), observer);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
