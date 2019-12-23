package com.example.syncreadyapp.views;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.syncreadyapp.R;

public class RoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.room);

        configureToolbar();
    }

    public void configureToolbar() {
        Toolbar roomToolbar = findViewById(R.id.roomToolbar);

        roomToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        roomToolbar.setTitle("Conversas");
    }
}
