package com.example.syncreadyapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.GroupListItemMyMessageFileBinding;
import com.example.syncreadyapp.databinding.PictureActivityBinding;
import com.example.syncreadyapp.databinding.RoomListItemBinding;
import com.example.syncreadyapp.interfaces.OnRoomDetailsFileClickListener;
import com.example.syncreadyapp.interfaces.OnRoomListClickListener;
import com.example.syncreadyapp.models.messagemodel.MessageModel;
import com.example.syncreadyapp.models.room.Room;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RoomDetailsFilesAdapter extends RecyclerView.Adapter<RoomDetailsFilesAdapter.RoomDetailsFileViewHolder> {

    private List<MessageModel> messagesDataset;
    private String roomTitle;
    private OnRoomDetailsFileClickListener onMessageFileClickListener;

    public RoomDetailsFilesAdapter(List<MessageModel> messagesDataset, String roomTitle, OnRoomDetailsFileClickListener onMessageFileClickListener) {
        this.messagesDataset = messagesDataset;
        this.roomTitle = roomTitle;
        this.onMessageFileClickListener = onMessageFileClickListener;
    }

    @NonNull
    @Override
    public RoomDetailsFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GroupListItemMyMessageFileBinding groupListItemMyMessageFileBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.group_list_item_my_message_file, parent, false);
        return new RoomDetailsFileViewHolder(groupListItemMyMessageFileBinding, onMessageFileClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomDetailsFileViewHolder holder, int position) {

        MessageModel currentRoom = messagesDataset.get(position);

        // set image
        Picasso.get()
            .load(RetrofitInstance.BASE_URL + "public/files/" + currentRoom.getContent())
            .placeholder(R.drawable.loading)
            .into(holder.groupListItemMyMessageFileBinding.groupListItemMyMessageFile );

        holder.groupListItemMyMessageFileBinding.getRoot().setBackgroundColor(Color.WHITE);
        holder.groupListItemMyMessageFileBinding.groupListMyMessageLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public int getItemCount() {
        return messagesDataset.size();
    }

    public class RoomDetailsFileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public GroupListItemMyMessageFileBinding groupListItemMyMessageFileBinding;
        private OnRoomDetailsFileClickListener onMessageFileClickListener;

        public RoomDetailsFileViewHolder(@NonNull GroupListItemMyMessageFileBinding groupListItemMyMessageFileBinding, @NonNull  OnRoomDetailsFileClickListener onMessageFileClickListener) {
            super(groupListItemMyMessageFileBinding.getRoot());
            this.groupListItemMyMessageFileBinding = groupListItemMyMessageFileBinding;
            this.onMessageFileClickListener = onMessageFileClickListener;
            groupListItemMyMessageFileBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMessageFileClickListener.onRoomDetailsFileClickListener(getAdapterPosition());
        }
    }
}
