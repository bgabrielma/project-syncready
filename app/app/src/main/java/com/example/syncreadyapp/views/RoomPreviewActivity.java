package com.example.syncreadyapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.RoompreviewBinding;
import com.example.syncreadyapp.models.room.ResponseRoom;
import com.example.syncreadyapp.models.room.Room;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.squareup.picasso.Picasso;

public class RoomPreviewActivity extends AppCompatActivity {
    private RoompreviewBinding roompreviewBinding;
    private HomeActivityViewModel homeActivityViewModel;
    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeActivityViewModel = ViewModelProviders.of(RoomPreviewActivity.this).get(HomeActivityViewModel.class);

        bundle = this.getIntent().getExtras();
        homeActivityViewModel.uuidMutableLiveData.setValue(bundle.getString("sycnready_user_uuid", null));
        homeActivityViewModel.roomCodeAccessMutableLiveData.setValue(bundle.getString("syncready_room_code", null));
        homeActivityViewModel.tokenAccessMutableLiveData.setValue("Bearer " + bundle.getString("syncready_user_token_access", null));
        homeActivityViewModel.roomUuidAccessMutableLiveData.setValue(bundle.getString("syncready_room_uuid", null));

        homeActivityViewModel.getRoomByQR(homeActivityViewModel.roomCodeAccessMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                .observe(this, getRoomsObserver);
    }

    private final Observer<ResponseRoom> getRoomsObserver = new Observer<ResponseRoom>() {
        @Override
        public void onChanged(ResponseRoom responseRoom) {
            Room room = responseRoom.getResponse().get(0);
            roompreviewBinding = DataBindingUtil.setContentView(RoomPreviewActivity.this, R.layout.roompreview);

            roompreviewBinding.roomPreviewJoinGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    homeActivityViewModel.addUserIntoRoom(homeActivityViewModel.uuidMutableLiveData.getValue(), homeActivityViewModel.roomUuidAccessMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                            .observe(RoomPreviewActivity.this, addUserIntoRoomObserver);
                }
            });

            roompreviewBinding.roomPreviewToolbarInclude.roomToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            // set image
            Picasso.get()
                    .load(RetrofitInstance.BASE_URL + "public/files/" + room.getImage())
                    .placeholder(R.drawable.loading)
                    .into(roompreviewBinding.groupImage);

            roompreviewBinding.roomPreviewRoomTitle.setText(room.getNameRoom());
            roompreviewBinding.roomPreviewRoomId.setText(room.getRoomCode());
            roompreviewBinding.roomPreviewRoomDescription.setText(room.getDescription());
        }
    };

    private final Observer<ResponseRoom> addUserIntoRoomObserver = new Observer<ResponseRoom>() {
        @Override
        public void onChanged(ResponseRoom responseRoom) {
            Intent roomActivity = new Intent(RoomPreviewActivity.this, RoomActivity.class);

            roomActivity.putExtras(bundle);
            startActivity(roomActivity);
        }
    };
}
