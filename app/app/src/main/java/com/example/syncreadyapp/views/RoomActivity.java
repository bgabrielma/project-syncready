package com.example.syncreadyapp.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.Utils;
import com.example.syncreadyapp.adapters.RoomListAdapter;
import com.example.syncreadyapp.databinding.RoomBinding;
import com.example.syncreadyapp.interfaces.OnRoomListClickListener;
import com.example.syncreadyapp.models.room.ResponseRoom;
import com.example.syncreadyapp.models.room.Room;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;


public class RoomActivity extends AppCompatActivity implements OnRoomListClickListener {
    private HomeActivityViewModel homeActivityViewModel;
    private RoomBinding roomBinding;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(RetrofitInstance.BASE_URL);
        } catch (URISyntaxException e) {
            Utils.showInternalUnavailableConnectionToServerAlert(RoomActivity.this);
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // mSocket.connect();

        homeActivityViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);

        Bundle bundle = this.getIntent().getExtras();
        homeActivityViewModel.uuidMutableLiveData.setValue(bundle.getString("sycnready_user_uuid", null));
        homeActivityViewModel.tokenAccessMutableLiveData.setValue("Bearer " + bundle.getString("syncready_user_token_access", null));

        homeActivityViewModel.getRooms(homeActivityViewModel.uuidMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                .observe(this, getRoomsObserver);
    }

    private final Observer<ResponseRoom> getRoomsObserver = new Observer<ResponseRoom>() {
        @Override
        public void onChanged(ResponseRoom responseRoom) {

            roomBinding = DataBindingUtil.setContentView(RoomActivity.this, R.layout.room);
            roomBinding.setHomeActivityViewModel(homeActivityViewModel);

            configureToolbar();
            configureRoomAdapter(responseRoom);
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
    public void configureRoomAdapter(ResponseRoom responseRoom) {
        RecyclerView recyclerView = roomBinding.recyclerRooms;
        recyclerView.setLayoutManager(new LinearLayoutManager(RoomActivity.this));
        recyclerView.setAdapter(new RoomListAdapter(responseRoom.getResponse(), RoomActivity.this));
    }

    @Override
    public void onRoomClick(int position) {
        Toast.makeText(this, "Item clicked with position " + position, Toast.LENGTH_SHORT).show();
    }
}
