package com.example.syncreadyapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.MainBinding;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.models.usermodel.User;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.example.syncreadyapp.views.fragments.HomeFragment;
import com.example.syncreadyapp.views.fragments.LoginFragment;
import com.example.syncreadyapp.views.fragments.RegisterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private HomeActivityViewModel homeActivityViewModel;
    private MainBinding mainBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeActivityViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);

        Bundle bundle = this.getIntent().getExtras();
        homeActivityViewModel.uuidMutableLiveData.setValue(bundle.getString("sycnready_user_uuid", null));
        homeActivityViewModel.tokenAccessMutableLiveData.setValue("Bearer " + bundle.getString("syncready_user_token_access", null));

        // loading user data
        homeActivityViewModel.getUserData(homeActivityViewModel.uuidMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                .observe(this, getUserDataObserver);
    }

    /* Observers */
    private final Observer<ResponseUser> getUserDataObserver = new Observer<ResponseUser>() {
        @Override
        public void onChanged(ResponseUser responseUser) {

            if (responseUser.getData() != null) {
                User user = responseUser.getData().get(0);

                mainBinding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.main);

                // set click listeners for toolbars
                setToolbarClickListeners();

                // set click listeners for bottom navigation's item
                setBottomNavClickListeners();

                // configure fragments
                changeFragments(R.id.HomeIcon);
            }
        }
    };

    private void setToolbarClickListeners() {

        mainBinding.ToolbarHome.toolbarMain.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.toolbar_menu_action_logout: {
                        Toast.makeText(getApplicationContext(), "Logout option clicked!", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                return false;
            }
        });

        mainBinding.ToolbarHome.toolbarQrcode.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.toolbar_qrcode_action_scan: {
                        Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
                        startActivity(intent);
                        break;
                    }
                }

                return false;
            }
        });
    }

    private void setBottomNavClickListeners() {
        // do stuff on future
        mainBinding.BottomNavHome.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.ChatIcon: {
                        Intent roomIntent = new Intent(getApplicationContext(), RoomActivity.class);
                        startActivity(roomIntent);
                        break;
                    }
                    case R.id.HomeIcon: {
                        changeFragments(R.id.HomeIcon);
                        break;
                    }
                    default: {
                        Toast.makeText(getApplicationContext(), "You clicked on " + item.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });
    }

    private void changeFragments(int layoutId) {
        Fragment selectedFragment = null;

        switch (layoutId) {
            case R.id.HomeIcon:
                selectedFragment = new HomeFragment();
                break;
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainContent, selectedFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .show(selectedFragment)
                    .commit();
        }
    }
}
