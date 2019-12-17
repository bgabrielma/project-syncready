package com.example.syncreadyapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.syncreadyapp.LoginViewModel;
import com.example.syncreadyapp.Models.LoginModel;
import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.LoginBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private LoginBinding loginBinding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.login);

        loginBinding.setLifecycleOwner(this);

        loginBinding.setLoginViewModel(loginViewModel);

        loginViewModel.getUser().observe(this, new Observer<LoginModel>() {
            @Override
            public void onChanged(LoginModel loginModel) {
                if (TextUtils.isEmpty(Objects.requireNonNull(loginModel).getUsername())) {
                    loginBinding.editTextNameLogin.setError("Campo vazio!");
                    loginBinding.editTextNameLogin.requestFocus();
                }
                if (TextUtils.isEmpty(Objects.requireNonNull(loginModel).getPassword())) {
                    loginBinding.editTextPassLogin.setError("Campo vazio!");
                    loginBinding.editTextPassLogin.requestFocus();
                }
            }
        });
    }
}
