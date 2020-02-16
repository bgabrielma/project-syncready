package com.example.syncreadyapp.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.NotificationsListBinding;
import com.example.syncreadyapp.databinding.RoomListItemBinding;
import com.example.syncreadyapp.interfaces.OnAlertListClickListener;
import com.example.syncreadyapp.interfaces.OnRoomListClickListener;
import com.example.syncreadyapp.models.alert.Alert;
import com.example.syncreadyapp.models.room.Room;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AlertListAdapter extends RecyclerView.Adapter<AlertListAdapter.RoomListViewHolder> {

    private List<Alert> alertsDataset;
    private OnAlertListClickListener onAlertListClickListener;

    public AlertListAdapter(List<Alert> alertsDataset, OnAlertListClickListener onAlertListClickListener) {
        this.alertsDataset = alertsDataset;
        this.onAlertListClickListener = onAlertListClickListener;
    }

    @NonNull
    @Override
    public RoomListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationsListBinding notificationsListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.notifications_list, parent, false);
        return new RoomListViewHolder(notificationsListBinding, onAlertListClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomListViewHolder holder, int position) {
        Alert _alert = alertsDataset.get(position);
        holder.notificationsListBinding.setAlert(_alert);

        int icon = 0;

        switch (_alert.getTypeOfAlertDesignation()) {
            case "Urgente":
                icon = R.drawable.ic_action_warning;
                break;
            case "Importante":
                icon = R.drawable.ic_action_eye_open;
                break;
            case "Informação":
                icon = R.drawable.ic_info;
        }

        // Load image
        holder.notificationsListBinding.infoNotificationIcon.setImageDrawable(holder.notificationsListBinding.getRoot().getContext().getDrawable(icon));
    }

    @Override
    public int getItemCount() {
        return alertsDataset.size();
    }

    public class RoomListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public NotificationsListBinding notificationsListBinding;
        private OnAlertListClickListener onAlertListClickListener;

        public RoomListViewHolder(@NonNull NotificationsListBinding notificationsListBinding, @NonNull  OnAlertListClickListener onAlertListClickListener) {
            super(notificationsListBinding.getRoot());
            this.notificationsListBinding = notificationsListBinding;
            this.onAlertListClickListener = onAlertListClickListener;
            notificationsListBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onAlertListClickListener.onAlertListClick(getAdapterPosition());
        }
    }
}
