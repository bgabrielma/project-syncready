package com.example.syncreadyapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

import java.util.ArrayList;
import java.util.List;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.RoomListViewHolder> implements Filterable {

    private List<Room> roomsDataset;
    private List<Room> roomsListFiltered;
    private OnRoomListClickListener onRoomListClickListener;

    public RoomListAdapter(List<Room> roomsDataset, OnRoomListClickListener onRoomListClickListener) {
        this.roomsDataset = roomsDataset;
        this.onRoomListClickListener = onRoomListClickListener;

        roomsListFiltered = new ArrayList<>(roomsDataset);
    }

    @NonNull
    @Override
    public RoomListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RoomListItemBinding roomListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.room_list_item, parent, false);
        return new RoomListViewHolder(roomListItemBinding, onRoomListClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomListViewHolder holder, int position) {
        Room currentRoom = roomsListFiltered.get(position);
        holder.roomListItemBinding.setRoom(currentRoom);

        // set image
        Picasso.get()
            .load(RetrofitInstance.BASE_URL + "public/files/" + currentRoom.getImage())
            .placeholder(R.drawable.loading)
            .into(holder.roomListItemBinding.groupImage );
    }

    @Override
    public int getItemCount() {
        return roomsListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return roomListAdapterFilter;
    }

    private Filter roomListAdapterFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Room> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(roomsDataset);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Room room : roomsDataset) {
                    if (room.getNameRoom().toLowerCase().contains(filterPattern)) {
                        filteredList.add(room);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            roomsListFiltered.clear();
            roomsListFiltered.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public int getItemPosition(String roomUUID)
    {
        for (int position = 0; position < getItemCount() ; position ++)
            if (roomsListFiltered.get(position).getUuidRoom().equals(roomUUID))
                return position;
        return 0;
    }

    public class RoomListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RoomListItemBinding roomListItemBinding;
        private OnRoomListClickListener onRoomListClickListener;

        public RoomListViewHolder(@NonNull RoomListItemBinding roomListItemBinding, @NonNull  OnRoomListClickListener onRoomListClickListener) {
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
