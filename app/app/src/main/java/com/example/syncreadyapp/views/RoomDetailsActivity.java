package com.example.syncreadyapp.views;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.RoomDetailsBinding;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.viewmodels.GroupActivityViewModel;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;

public class RoomDetailsActivity extends AppCompatActivity {
    private RoomDetailsBinding detailsBinding;
    private Bundle bundle;
    private HomeActivityViewModel homeActivityViewModel;
    private GroupActivityViewModel groupActivityViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = DataBindingUtil.setContentView(this, R.layout.room_details);

        homeActivityViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
        groupActivityViewModel = ViewModelProviders.of(this).get(GroupActivityViewModel.class);

        bundle = this.getIntent().getExtras();
        homeActivityViewModel.uuidMutableLiveData.setValue(bundle.getString("sycnready_user_uuid", null));
        homeActivityViewModel.tokenAccessMutableLiveData.setValue("Bearer " + bundle.getString("syncready_user_token_access", null));
        groupActivityViewModel.roomUuid.setValue(bundle.getString("syncready_room_uuid", null));
        groupActivityViewModel.roomTitle.setValue(bundle.getString("syncready_room_title", null));
        groupActivityViewModel.roomImage.setValue(bundle.getString("syncready_room_image", null));

        // loading user data
        homeActivityViewModel.getUserData(homeActivityViewModel.uuidMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                .observe(this, getUserDataObserver);
    }

    private final Observer<ResponseUser> getUserDataObserver = new Observer<ResponseUser>() {
        @Override
        public void onChanged(ResponseUser responseUser) {
            homeActivityViewModel.userMutableLiveData.setValue(responseUser.getData().get(0));

            configureToolbar();
            configurePermissions();
        }
    };

    public void configureToolbar() {
        detailsBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void configurePermissions() {
        if (!homeActivityViewModel.userMutableLiveData.getValue().getType().equalsIgnoreCase("Cliente")) {
            detailsBinding.toolbar.getMenu().getItem(0).setVisible(false);
            detailsBinding.buttonUploadImage.setVisibility(View.INVISIBLE);
        }
    }
}
