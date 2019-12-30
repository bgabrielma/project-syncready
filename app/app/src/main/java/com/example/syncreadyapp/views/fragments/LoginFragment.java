package com.example.syncreadyapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.syncreadyapp.Utils;
import com.example.syncreadyapp.databinding.LoginBinding;
import com.example.syncreadyapp.models.loginmodel.LoginModel;
import com.example.syncreadyapp.models.repositories.RepositoryResponse;
import com.example.syncreadyapp.models.userlogged.UserLogged;
import com.example.syncreadyapp.viewmodels.MainActivityViewModel;
import com.example.syncreadyapp.views.HomeActivity;
import com.example.syncreadyapp.views.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private LoginBinding loginBinding;
    private MainActivityViewModel mainActivityViewModel;

    private View prevView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // prevent fragment to creating again the whole process. Prevent also execute "last operation made before switching fragments"
        if (prevView != null) {
            return prevView;
        }

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        loginBinding = DataBindingUtil.inflate(inflater, R.layout.login, container, false);
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
                    loginBinding.btnEntrar.setEnabled(false);
                    mainActivityViewModel.getUserLogged().observe(loginBinding.getLifecycleOwner(), observer);
                }
            }
        });

        // store "actual" preview for future backs in this fragment
        prevView = loginBinding.getRoot();

        return loginBinding.getRoot();
    }

    final Observer<RepositoryResponse> observer = new Observer<RepositoryResponse>() {
        @Override
        public void onChanged(RepositoryResponse userLoggedRepositoryResponse) {

            UserLogged userLogged = (UserLogged) userLoggedRepositoryResponse.getGenericMutableLiveData().getValue();
            Boolean isNetworkTrouble = userLoggedRepositoryResponse.getIsNetworkLiveData().getValue();

            if (userLogged != null) {
                // define userLoggedMutableLiveData as system storage
                mainActivityViewModel.userLoggedMutableLiveData.setValue(userLogged);

                Intent intent = new Intent(getActivity(), HomeActivity.class);
                Bundle userAuthInformations = new Bundle();

                userAuthInformations.putString("sycnready_user_uuid", userLogged.getPkUuid());
                userAuthInformations.putString("syncready_user_token_access", userLogged.getNewToken());

                intent.putExtras(userAuthInformations);
                startActivity(intent);

                // prevent user to access again this activity
                getActivity().finish();

            } else if (!isNetworkTrouble.booleanValue()){
                Snackbar.make(loginBinding.getRoot(), "Email e/ou password inválido(s)", Snackbar.LENGTH_LONG).show();
            }

            if(isNetworkTrouble.booleanValue()) {
                Utils.showInternalUnavailableConnectionToServerAlert(getActivity());
            }
            loginBinding.btnEntrar.setEnabled(true);
        }
    };

    @Override
    public void onPause() {
        super.onPause();

        Log.d("onPause", "onPaused method called!");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("onCreate", "onCreate method called!");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume", "onResume method called!");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onDestroy", "asdasdasdas");
    }
}
