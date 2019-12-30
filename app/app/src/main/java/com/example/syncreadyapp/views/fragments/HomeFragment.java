package com.example.syncreadyapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.HomeBinding;

public class HomeFragment extends Fragment {

    private View prevView;
    private HomeBinding homeBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (prevView != null) {
            return prevView;
        }

        homeBinding = DataBindingUtil.inflate(inflater, R.layout.home, container, false);
        homeBinding.setLifecycleOwner(this);

        prevView = homeBinding.getRoot();

        return homeBinding.getRoot();
    }
}
