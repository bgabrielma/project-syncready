package com.example.syncreadyapp.views.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.Utils;
import com.example.syncreadyapp.adapters.AlertListAdapter;
import com.example.syncreadyapp.databinding.AlertBinding;
import com.example.syncreadyapp.interfaces.OnAlertListClickListener;
import com.example.syncreadyapp.models.alert.Alert;
import com.example.syncreadyapp.models.alert.ResponseAlert;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.example.syncreadyapp.views.RoomActivity;

import java.util.List;

public class AlertsFragment extends Fragment implements OnAlertListClickListener {
    private AlertBinding alertBinding;
    private HomeActivityViewModel homeActivityViewModel;
    private List<Alert> alerts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeActivityViewModel = ViewModelProviders.of(getActivity()).get(HomeActivityViewModel.class);
        alertBinding = DataBindingUtil.inflate(inflater, R.layout.alert, container, false);
        alertBinding.setLifecycleOwner(this);

        homeActivityViewModel.getAlertsByUuidTypeUsers(homeActivityViewModel.userMutableLiveData.getValue().getTypeOfUserUuidTypeOfUsers(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
            .observe(this, getAlertsByUuidTypeUser);

        return alertBinding.getRoot();
    }

    private final Observer<ResponseAlert> getAlertsByUuidTypeUser = new Observer<ResponseAlert>() {
        @Override
        public void onChanged(ResponseAlert responseAlert) {
            if (responseAlert != null) {
                alerts = responseAlert.getResponse();
                RecyclerView recyclerView = alertBinding.recyclerAlerts;
                recyclerView.setAdapter(new AlertListAdapter(alerts, AlertsFragment.this));
                recyclerView.setLayoutManager(new LinearLayoutManager(AlertsFragment.this.getContext()));
            } else {
                Utils.showInternalUnavailableConnectionToServerAlert(getActivity()).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Utils.ApplicationLogout(getActivity());
                    }
                }).show();
            }
        }
    };

    @Override
    public void onAlertListClick(int position) {
        int icon = 0;
        Alert alert = alerts.get(position);

        switch (alert.getTypeOfAlertDesignation()) {
            case "Urgente":
                icon = R.drawable.ic_action_warning;
                break;
            case "Importante":
                icon = R.drawable.ic_action_eye_open;
                break;
            case "Informação":
                icon = R.drawable.ic_info;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setIcon(icon)
            .setTitle(alert.getNameAlerts())
            .setMessage(alert.getAlertText())
            .setPositiveButton("Ok", null)
            .show();
    }
}
