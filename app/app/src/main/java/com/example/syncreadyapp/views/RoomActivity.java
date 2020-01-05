package com.example.syncreadyapp.views;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.models.room.ResponseRoom;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;

public class RoomActivity extends AppCompatActivity {
    private HomeActivityViewModel homeActivityViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeActivityViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);

        Bundle bundle = this.getIntent().getExtras();
        homeActivityViewModel.uuidMutableLiveData.setValue(bundle.getString("sycnready_user_uuid", null));
        homeActivityViewModel.tokenAccessMutableLiveData.setValue("Bearer " + bundle.getString("syncready_user_token_access", null));

        setContentView(R.layout.room);

        homeActivityViewModel.getRooms(homeActivityViewModel.uuidMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                .observe(this, getRoomsObserver);
    }

    private final Observer<ResponseRoom> getRoomsObserver = new Observer<ResponseRoom>() {
        @Override
        public void onChanged(ResponseRoom responseRoom) {
            configureToolbar();
        }
    };

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
