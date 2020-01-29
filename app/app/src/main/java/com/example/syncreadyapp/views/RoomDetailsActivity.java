package com.example.syncreadyapp.views;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.RoomDetailsBinding;

public class RoomDetailsActivity extends AppCompatActivity {
    private RoomDetailsBinding detailsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = DataBindingUtil.setContentView(this, R.layout.room_details);
    }
}
