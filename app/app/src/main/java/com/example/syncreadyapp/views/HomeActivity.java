package com.example.syncreadyapp.views;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.Utils;
import com.example.syncreadyapp.databinding.HomeBinding;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.zip.Inflater;

public class HomeActivity extends AppCompatActivity {
    private HomeActivityViewModel homeActivityViewModel;
    private HomeBinding homeBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeActivityViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
        homeBinding = DataBindingUtil.setContentView(this, R.layout.home);

        // set click listeners for toolbars7
        setToolbarClickListeners();

        // set click listeners for bottom navigation's item
        setBottomNavClickListeners();
    }

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
