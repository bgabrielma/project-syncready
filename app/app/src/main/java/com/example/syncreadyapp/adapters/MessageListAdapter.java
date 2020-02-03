package com.example.syncreadyapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.GroupListItemMyMessageBinding;
import com.example.syncreadyapp.databinding.GroupListItemMyMessageFileBinding;
import com.example.syncreadyapp.databinding.GroupListItemOtherMessageBinding;
import com.example.syncreadyapp.databinding.GroupListItemOtherMessageFileBinding;
import com.example.syncreadyapp.models.messagemodel.MessageModel;
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

        /**
         *
         * 0 -> my message without file
         * 1 -> my message with file
         * 2 -> other message without file
         * 3-> other message with file
         */

        int _viewType = 0;

        if (messagesDataset.get(position).getPkUuid().equals(uuidUserLogged)) {
            _viewType = messagesDataset.get(position).getIsImage() == 0 ? 0 : 1;
        } else {
            _viewType = messagesDataset.get(position).getIsImage() == 0 ? 2 : 3;
        }

        return  _viewType;
    }

    @NonNull
    @Override
    public MessageListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d("viewType", "Valor atual do view type: " + viewType);

        switch (viewType) {
            case 0: {
                GroupListItemMyMessageBinding groupListItemMyMessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.group_list_item_my_message, parent, false);
                return new MessageListViewHolder(groupListItemMyMessageBinding);
            }
            case 1: {
                GroupListItemMyMessageFileBinding groupListItemMyMessageFileBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.group_list_item_my_message_file, parent, false);
                return new MessageListViewHolder(groupListItemMyMessageFileBinding);
            }
            case 2: {
                GroupListItemOtherMessageBinding groupListItemOtherMessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.group_list_item_other_message, parent, false);
                return new MessageListViewHolder(groupListItemOtherMessageBinding);
            }
            case 3: {
                GroupListItemOtherMessageFileBinding groupListItemOtherMessageFileBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.group_list_item_other_message_file, parent, false);
                return new MessageListViewHolder(groupListItemOtherMessageFileBinding);
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListViewHolder holder, int position) {
        MessageModel message = messagesDataset.get(position);

        if (message.getIsImage() == 0) {
            if (messagesDataset.get(position).getPkUuid().equals(uuidUserLogged))
                ((GroupListItemMyMessageBinding) holder.viewDataBinding).setMessage(message);
            else
                ((GroupListItemOtherMessageBinding) holder.viewDataBinding).setMessage(message);
        } else {
            if (messagesDataset.get(position).getPkUuid().equals(uuidUserLogged)) {
                GroupListItemMyMessageFileBinding _binding = (GroupListItemMyMessageFileBinding) holder.viewDataBinding;

                _binding.setMessage(message);

                // set image
                Picasso.get()
                        .load(RetrofitInstance.BASE_URL + "public/files/" + message.getContent())
                        .placeholder(R.drawable.loading)
                        .into(_binding.groupListItemMyMessageFile);
            } else {
                GroupListItemOtherMessageFileBinding _binding = (GroupListItemOtherMessageFileBinding) holder.viewDataBinding;
                _binding.setMessage(message);

                // set image
                Picasso.get()
                        .load(RetrofitInstance.BASE_URL + "public/files/" + message.getContent())
                        .placeholder(R.drawable.loading)
                        .into(_binding.groupListItemOtherMessageFile);
            }
        }
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
