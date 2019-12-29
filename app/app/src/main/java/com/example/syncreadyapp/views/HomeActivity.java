package com.example.syncreadyapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.HomeBinding;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.models.usermodel.User;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private HomeActivityViewModel homeActivityViewModel;
    private HomeBinding homeBinding;

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

            if(responseUser.getData() != null) {
               User user = responseUser.getData().get(0);

                homeBinding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.home);

                // set click listeners for toolbars
                setToolbarClickListeners();

                // set click listeners for bottom navigation's item
                setBottomNavClickListeners();
            }
        }
    };

    private void setToolbarClickListeners() {
        homeBinding.ToolbarHome.toolbarMain.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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

        homeBinding.ToolbarHome.toolbarQrcode.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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
        homeBinding.BottomNavHome.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.ChatIcon: {
                        Intent roomIntent = new Intent(getApplicationContext(), RoomActivity.class);
                        startActivity(roomIntent);
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
}
