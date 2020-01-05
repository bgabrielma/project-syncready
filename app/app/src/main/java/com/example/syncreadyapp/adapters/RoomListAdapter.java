package com.example.syncreadyapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.RoomListItemBinding;
import com.example.syncreadyapp.interfaces.OnRoomListClickListener;
import com.example.syncreadyapp.models.room.Room;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.RoomListViewHolder> {

    private List<Room> roomsDataset;
    private OnRoomListClickListener onRoomListClickListener;

    public RoomListAdapter(List<Room> roomsDataset, OnRoomListClickListener onRoomListClickListener) {
        this.roomsDataset = roomsDataset;
        this.onRoomListClickListener = onRoomListClickListener;
    }

    @NonNull
    @Override
    public RoomListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RoomListItemBinding roomListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.room_list_item, parent, false);
        return new RoomListViewHolder(roomListItemBinding, onRoomListClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomListViewHolder holder, int position) {
        Room currentRoom = roomsDataset.get(position);
        holder.roomListItemBinding.setRoom(currentRoom);

        // set image
        Picasso.get()
            .load(RetrofitInstance.BASE_URL + "public/files/" + currentRoom.getImage())
            .placeholder(R.drawable.loading)
            .into(holder.roomListItemBinding.groupImage );
    }

    @Override
    public int getItemCount() {
        return roomsDataset.size();
    }

    public class RoomListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RoomListItemBinding roomListItemBinding;
        private OnRoomListClickListener onRoomListClickListener;

        public RoomListViewHolder(@NonNull RoomListItemBinding roomListItemBinding, OnRoomListClickListener onRoomListClickListener) {
            super(roomListItemBinding.getRoot());
            this.roomListItemBinding = roomListItemBinding;
            this.onRoomListClickListener = onRoomListClickListener;
            roomListItemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRoomListClickListener.onRoomClick(getAdapterPosition());
        }
    }
}
