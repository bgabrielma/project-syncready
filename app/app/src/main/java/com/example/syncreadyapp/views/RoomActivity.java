package com.example.syncreadyapp.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.Utils;
import com.example.syncreadyapp.adapters.RoomListAdapter;
import com.example.syncreadyapp.databinding.RoomBinding;
import com.example.syncreadyapp.databinding.RoomListItemBinding;
import com.example.syncreadyapp.interfaces.OnRoomListClickListener;
import com.example.syncreadyapp.models.messagemodel.ResponseMessage;
import com.example.syncreadyapp.models.room.ResponseRoom;
import com.example.syncreadyapp.models.room.Room;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.viewmodels.GroupActivityViewModel;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class RoomActivity extends AppCompatActivity implements OnRoomListClickListener {

    private HomeActivityViewModel homeActivityViewModel;
    private GroupActivityViewModel groupActivityViewModel;
    private RoomBinding roomBinding;
    private Bundle bundle;
    private List<Room> rooms;
    private Room currentRoom;

    private Socket mSocket;
    private RoomListAdapter roomListAdapter;
    {
        try {
            mSocket = IO.socket(RetrofitInstance.BASE_URL);
        } catch (URISyntaxException e) {
            Utils.showInternalUnavailableConnectionToServerAlert(RoomActivity.this).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.ApplicationLogout(RoomActivity.this);
                }
            }).show();
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSocket.connect(); // connect to socket
        // mSocket.emit("joinRoomList", 1, 2); // trigger server's event

        homeActivityViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
        groupActivityViewModel = ViewModelProviders.of(this).get(GroupActivityViewModel.class);

        bundle = this.getIntent().getExtras();
        homeActivityViewModel.uuidMutableLiveData.setValue(bundle.getString("sycnready_user_uuid", null));
        homeActivityViewModel.tokenAccessMutableLiveData.setValue("Bearer " + bundle.getString("syncready_user_token_access", null));

        homeActivityViewModel.getRooms(homeActivityViewModel.uuidMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                .observe(this, getRoomsObserver);
    }

    private final Observer<ResponseRoom> getRoomsObserver = new Observer<ResponseRoom>() {
        @Override
        public void onChanged(ResponseRoom responseRoom) {

            if (responseRoom != null) {
                roomBinding = DataBindingUtil.setContentView(RoomActivity.this, R.layout.room);
                roomBinding.setHomeActivityViewModel(homeActivityViewModel);
                configureRoomAdapter(responseRoom);
                setLastMessageForRooms();
                configureToolbar();
            } else {
                Utils.showInternalUnavailableConnectionToServerAlert(RoomActivity.this).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Utils.ApplicationLogout(RoomActivity.this);
                    }
                }).show();
            }
        }
    };

    private void setLastMessageForRooms() {
        for (Room room : rooms) {
            currentRoom = room;
            groupActivityViewModel.getMessagesByRoom(room.getUuidRoom(), homeActivityViewModel.tokenAccessMutableLiveData.getValue(), true)
                    .observe(RoomActivity.this, getMessagesByRoomObserver);
        }
    }

    private final Observer<ResponseMessage> getMessagesByRoomObserver = new Observer<ResponseMessage>() {
        @Override
        public void onChanged(ResponseMessage responseMessage) {
            String lastMessage = null;
            RoomListAdapter.RoomListViewHolder holder = null;
            if (responseMessage != null ) {
                int positionId = roomListAdapter.getItemPosition(currentRoom.getUuidRoom());

                holder = (RoomListAdapter.RoomListViewHolder) roomBinding.recyclerRooms.findViewHolderForAdapterPosition(positionId);

                if (!responseMessage.getData().isEmpty()) {
                    if (responseMessage.getData().get(0).getIsImage() == 1) lastMessage = "Fotografia";
                    else lastMessage = responseMessage.getData().get(0).getContent();
                }
                else {
                    lastMessage = "";
                }

                holder.roomListItemBinding.groupLastMessage.setText(lastMessage);
            }
        }
    };

    public void configureToolbar() {
        roomBinding.roomToolbarInclude.roomToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        roomBinding.roomToolbarInclude.roomToolbar.setTitle("Conversas");
        MenuItem menuItem = roomBinding.roomToolbarInclude.roomToolbar.getMenu().findItem(R.id.toolbar_room_menu_action_search);
        SearchView searchView = (SearchView)  menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                roomListAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    public void configureRoomAdapter(ResponseRoom responseRoom) {
        if (responseRoom != null) {
            RecyclerView recyclerView = roomBinding.recyclerRooms;
            recyclerView.setLayoutManager(new LinearLayoutManager(RoomActivity.this));
            roomListAdapter = new RoomListAdapter(rooms = responseRoom.getResponse(), RoomActivity.this);
            recyclerView.setAdapter(roomListAdapter);
        } else {
            Utils.showInternalUnavailableConnectionToServerAlert(RoomActivity.this).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.ApplicationLogout(RoomActivity.this);
                }
            }).show();
        }
    }

    @Override
    public void onRoomClick(int position) {

        Room room = rooms.get(position);

        Calendar calendar = new GregorianCalendar();
        Calendar now = new GregorianCalendar();
        calendar.setTime(Utils.getDateFromString(room.getUntilAt()));

        int days = (int) ((calendar.getTimeInMillis() - now.getTimeInMillis())/ (1000*60*60*24));

        if(days < 0) {
            Utils.showGroupExpired(RoomActivity.this)
                    .setPositiveButton("Ok", null)
                    .show();
            return;
        }

        Intent groupActivity = new Intent(this, GroupActivity.class);
        bundle.putString("syncready_room_uuid", room.getUuidRoom());
        bundle.putString("syncready_room_title", room.getNameRoom());
        bundle.putString("syncready_room_image", room.getImage());
        groupActivity.putExtras(bundle);

        mSocket.emit("joinRoomList", homeActivityViewModel.uuidMutableLiveData.getValue(), room.getUuidRoom());

        startActivity(groupActivity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Socket state", "" + mSocket.connected());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (rooms != null && roomListAdapter.getItemCount() != 0)
            setLastMessageForRooms();
    }
}
