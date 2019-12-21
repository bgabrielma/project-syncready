package com.example.syncreadyapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.syncreadyapp.R;

public class RegisterFragment extends Fragment {


    private View prevView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // prevent fragment to creating again the whole process. Prevent also execute "last operation made before switching fragments"
        if (prevView != null) {
            return prevView;
        }

        return inflater.inflate(R.layout.register, container, false);
    }
}
