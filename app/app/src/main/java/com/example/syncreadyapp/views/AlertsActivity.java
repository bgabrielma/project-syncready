package com.example.syncreadyapp.views;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.AlertsBinding;

public class AlertsActivity extends AppCompatActivity {
    private AlertsBinding alertsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertsBinding = DataBindingUtil.setContentView(this, R.layout.alerts);
    }
}
