package com.example.syncreadyapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.views.fragments.LoginFragment;
import com.example.syncreadyapp.views.fragments.RegisterFragment;

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
