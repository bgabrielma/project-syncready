package com.example.syncreadyapp.views;

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
        homeActivityViewModel.roomCodeAccessMutableLiveData.setValue(bundle.getString("syncready_room_code", null));
        homeActivityViewModel.tokenAccessMutableLiveData.setValue("Bearer " + bundle.getString("syncready_user_token_access", null));

        homeActivityViewModel.getRoomByQR(homeActivityViewModel.roomCodeAccessMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                .observe(this, getRoomsObserver);
    }

    private final Observer<ResponseRoom> getRoomsObserver = new Observer<ResponseRoom>() {
        @Override
        public void onChanged(ResponseRoom responseRoom) {
            Room room = responseRoom.getResponse().get(0);
            roompreviewBinding = DataBindingUtil.setContentView(RoomPreviewActivity.this, R.layout.roompreview);

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
}
