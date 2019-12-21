package com.example.syncreadyapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.syncreadyapp.views.fragments.LoginFragment;
import com.example.syncreadyapp.views.fragments.RegisterFragment;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginregister);
        changeFragment(R.layout.login);
    }
   public void changeFragment(int layoutId) {
       Fragment selectedFragment = null;

       switch (layoutId) {
           case R.layout.login:
               selectedFragment = new LoginFragment();
               break;
           case R.layout.register:
               selectedFragment = new RegisterFragment();
               break;
       }

       if(selectedFragment != null) {
           getSupportFragmentManager().beginTransaction()
                   .replace(R.id.loginRegisterFragmentZone, selectedFragment)
                   .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE )
                   .show(selectedFragment)
                   .commit();
       }
   }
}
