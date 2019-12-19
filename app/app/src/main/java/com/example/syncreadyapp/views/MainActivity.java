package com.example.syncreadyapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.LoginBinding;
import com.example.syncreadyapp.models.LoginModel;
import com.example.syncreadyapp.models.userLogged.ResponseUserLogged;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.services.SyncReadyMobileDataService;
import com.example.syncreadyapp.viewmodels.UserLoggedViewModel;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private LoginBinding loginBinding;
    private UserLoggedViewModel userLoggedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userLoggedViewModel = ViewModelProviders.of(this).get(UserLoggedViewModel.class);

        loginBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.login);
        loginBinding.setLifecycleOwner(this);

        loginBinding.setUserLoggedViewModel(userLoggedViewModel);

        userLoggedViewModel.getUser().observe(this, new Observer<LoginModel>() {
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
                    // login phase

                    SyncReadyMobileDataService syncReadyMobileDataService = RetrofitInstance.getService();

                    Call<ResponseUserLogged> call = syncReadyMobileDataService.login(userLoggedViewModel.getUser().getValue());

                    call.enqueue(new Callback<ResponseUserLogged>() {
                        @Override
                        public void onResponse(Call<ResponseUserLogged> call, Response<ResponseUserLogged> response) {
                            Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseUserLogged> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
