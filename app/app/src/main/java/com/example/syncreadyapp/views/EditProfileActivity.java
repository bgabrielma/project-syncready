package com.example.syncreadyapp.views;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.EditProfileBinding;

public class EditProfileActivity extends AppCompatActivity {
    private EditProfileBinding editProfileBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editProfileBinding = DataBindingUtil.setContentView(this,R.layout.edit_profile);

        editProfileBinding.editProfileToolbarInclude.editToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
