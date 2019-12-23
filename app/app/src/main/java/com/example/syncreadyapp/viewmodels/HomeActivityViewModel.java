package com.example.syncreadyapp.viewmodels;

import android.app.Application;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

public class HomeActivityViewModel extends AndroidViewModel {

    private Application application;

    public HomeActivityViewModel(@NonNull Application application) {
        super(application);

        this.application = application;
    }

    public void onLogoutOptionClick(View view) {
        Toast.makeText(application.getApplicationContext(), "Deste ghost xD - chamado pelo viewmodel (LIVE DATA É O MAIOR CRL)", Toast.LENGTH_LONG)
            .show();
    }

    public void onQRCodeOptionClick(View view) {
        Toast.makeText(application.getApplicationContext(), "Deste scan xD - chamado pelo viewmodel (LIVE DATA É O MAIOR CRL)", Toast.LENGTH_LONG)
            .show();
    }
}
