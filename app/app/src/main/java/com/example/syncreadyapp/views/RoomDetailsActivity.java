package com.example.syncreadyapp.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.adapters.MessageListAdapter;
import com.example.syncreadyapp.databinding.RoomDetailsBinding;
import com.example.syncreadyapp.models.messagemodel.ResponseMessage;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.viewmodels.GroupActivityViewModel;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.squareup.picasso.Picasso;

public class RoomDetailsActivity extends AppCompatActivity {
    private RoomDetailsBinding detailsBinding;
    private Bundle bundle;
    private HomeActivityViewModel homeActivityViewModel;
    private GroupActivityViewModel groupActivityViewModel;
    private String groupUsersFormatted = null;

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
        groupUsersFormatted = bundle.getString("groupUsersFormatted", null);

        // loading user data
        homeActivityViewModel.getUserData(homeActivityViewModel.uuidMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                .observe(this, getUserDataObserver);
    }

    private final Observer<ResponseUser> getUserDataObserver = new Observer<ResponseUser>() {
        @Override
        public void onChanged(ResponseUser responseUser) {
            homeActivityViewModel.userMutableLiveData.setValue(responseUser.getData().get(0));
            groupActivityViewModel.getMessagesByRoom(groupActivityViewModel.roomUuid.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue(), false)
                    .observe(RoomDetailsActivity.this, getMessagesByRoomObserver);
        }
    };

    private final Observer<ResponseMessage> getMessagesByRoomObserver = new Observer<ResponseMessage>() {
        @Override
        public void onChanged(ResponseMessage responseMessage) {
            if (responseMessage != null) {
                configureToolbar();
                configurePermissions();

                detailsBinding.roomDetailsGroupUsersFormatted.setText(groupUsersFormatted);

                detailsBinding.btnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(RoomDetailsActivity.this, SyncReadyPDFViewerActivity.class));
                    }
                });

                detailsBinding.expandedImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(RoomDetailsActivity.this, PictureActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("groupImage", groupActivityViewModel.roomImage.getValue());
                        bundle.putString("groupTitle", groupActivityViewModel.roomTitle.getValue());
                        bundle.putString("groupImageFile", groupActivityViewModel.roomImage.getValue());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        }
    };

    public void configureToolbar() {

        detailsBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        detailsBinding.toolbar.setTitle(groupActivityViewModel.roomTitle.getValue());
        Picasso.get()
                .load(RetrofitInstance.BASE_URL + "public/files/" + groupActivityViewModel.roomImage.getValue())
                .placeholder(R.drawable.loading)
                .into(detailsBinding.expandedImage);
    }

    public void configurePermissions() {
        if (homeActivityViewModel.userMutableLiveData.getValue().getType().equalsIgnoreCase("Cliente")) {
            detailsBinding.toolbar.getMenu().getItem(0).setVisible(false);
            detailsBinding.buttonUploadImage.setVisibility(View.INVISIBLE);
        }
    }
}
