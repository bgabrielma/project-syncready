package com.example.syncreadyapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.LoginBinding;
import com.example.syncreadyapp.models.loginModel.LoginModel;
import com.example.syncreadyapp.models.userLogged.UserLogged;
import com.example.syncreadyapp.viewmodels.MainActivityViewModel;

import java.util.Objects;

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

                    // disable temporarily button
                    loginBinding.btnEntrar.setEnabled(false);

                    mainActivityViewModel.getUserLogged().observe(MainActivity.this, new Observer<UserLogged>() {
                        @Override
                    public void onChanged(UserLogged userLogged) {
                            if(userLogged == null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Email e/ou password incorreto(s)");
                                builder.setMessage("Verifique as credencias e tente novamente.");
                                builder.setPositiveButton("123", null).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Bem vindo " + userLogged.getPkUuid());
                                builder.setMessage("Chave de acesso: " + userLogged.getNewToken());
                                builder.setPositiveButton("123", null).show();
                            }
                            loginBinding.btnEntrar.setEnabled(true);
                        }
                    });
                }
            }
        });
    }
}
