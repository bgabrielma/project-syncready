package com.example.syncreadyapp.views;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.PictureActivityBinding;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.squareup.picasso.Picasso;

public class PictureActivity extends AppCompatActivity {

    private PictureActivityBinding pictureActivityBinding;
    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pictureActivityBinding = DataBindingUtil.setContentView(this, R.layout.picture_activity);

        bundle = getIntent().getExtras();
        loadData();
    }

    public void loadData() {
        Picasso.get()
                .load(RetrofitInstance.BASE_URL + "public/files/" + bundle.getString("groupImage", ""))
                .placeholder(R.drawable.loading)
                .into(pictureActivityBinding.groupImage);

        pictureActivityBinding.toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pictureActivityBinding.toolbarGroupTitle.setText(bundle.getString("groupTitle", ""));
        pictureActivityBinding.toolbarGroupUsersFormatted.setText(bundle.getString("groupUsersFormatted", ""));
        Picasso.get()
                .load(RetrofitInstance.BASE_URL + "public/files/" + bundle.getString("groupImageFile", ""))
                .placeholder(R.drawable.loading)
                .into(pictureActivityBinding.touchImageFileView);
    }
}
