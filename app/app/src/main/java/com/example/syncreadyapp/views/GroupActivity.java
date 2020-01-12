package com.example.syncreadyapp.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.GroupBinding;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.viewmodels.GroupActivityViewModel;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;

public class GroupActivity extends AppCompatActivity {
    protected Socket socket;
    private HomeActivityViewModel homeActivityViewModel;
    private GroupActivityViewModel groupActivityViewModel;
    private GroupBinding groupBinding;
    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);

        try {
            socket = IO.socket(RetrofitInstance.BASE_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

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

            if (responseUser.getData() != null) {
                homeActivityViewModel.userMutableLiveData.setValue(responseUser.getData().get(0));
                groupBinding = DataBindingUtil.setContentView(GroupActivity.this, R.layout.group);
                configureToolbar();
            }
        }
    };


    public void configureToolbar() {
        groupBinding.groupToolbarInclude.toolbarGroupTitle.setText(groupActivityViewModel.roomTitle.getValue());
        groupBinding.groupToolbarInclude.toolbarGroupUsersFormatted.setText("Bruno, Rafaela, Rafaela, Mafalda, Marta, Carolina");

        // set image
        Picasso.get()
                .load(RetrofitInstance.BASE_URL + "public/files/" + groupActivityViewModel.roomImage.getValue())
                .placeholder(R.drawable.loading)
                .into(groupBinding.groupToolbarInclude.groupImage);

        groupBinding.groupToolbarInclude.toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
