package com.example.syncreadyapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.GroupListItemMyMessageBinding;
import com.example.syncreadyapp.databinding.GroupListItemOtherMessageBinding;
import com.example.syncreadyapp.databinding.RoomListItemBinding;
import com.example.syncreadyapp.interfaces.OnRoomListClickListener;
import com.example.syncreadyapp.models.messagemodel.MessageModel;
import com.example.syncreadyapp.models.room.Room;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageListViewHolder> {

    private List<MessageModel> messagesDataset;
    private String uuidUserLogged;

    public MessageListAdapter(List<MessageModel> messagesDataset, String uuidUserLogged) {
        this.messagesDataset = messagesDataset;
        this.uuidUserLogged = uuidUserLogged;
    }

    @Override
    public int getItemViewType(int position) {
        return (messagesDataset.get(position).getPkUuid().equals(uuidUserLogged)) ? 0 : 1;
    }

    @NonNull
    @Override
    public MessageListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d("viewType", "Valor atual do view type: " + viewType);

        if (viewType == 0) {
            GroupListItemMyMessageBinding groupListItemMyMessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.group_list_item_my_message, parent, false);
            return new MessageListViewHolder(groupListItemMyMessageBinding);
        } else {
            GroupListItemOtherMessageBinding groupListItemOtherMessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.group_list_item_other_message, parent, false);
            return new MessageListViewHolder(groupListItemOtherMessageBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListViewHolder holder, int position) {
        MessageModel message = messagesDataset.get(position);

        if (messagesDataset.get(position).getPkUuid().equals(uuidUserLogged)) ((GroupListItemMyMessageBinding) holder.viewDataBinding).setMessage(message);
        else ((GroupListItemOtherMessageBinding) holder.viewDataBinding).setMessage(message);
    }

    @Override
    public int getItemCount() {
        return messagesDataset.size();
    }

    public class MessageListViewHolder extends RecyclerView.ViewHolder{
        private ViewDataBinding viewDataBinding;

        public MessageListViewHolder(@NonNull ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());

            this.viewDataBinding = viewDataBinding;
        }
    }
}
