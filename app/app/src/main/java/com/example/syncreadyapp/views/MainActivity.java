package com.example.syncreadyapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.Utils;
import com.example.syncreadyapp.views.fragments.LoginFragment;
import com.example.syncreadyapp.views.fragments.RegisterFragment;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginregister);

        // request permissions
        requestAllPermissionsNeeded();

        // then, continue the process
        changeFragment(R.layout.login);
    }

    public void changeFragment(int layoutId) {
        Fragment selectedFragment = null;

        switch (layoutId) {
            case R.layout.login:
                selectedFragment = new LoginFragment();
                break;
            case R.layout.register:
                selectedFragment = new RegisterFragment();
                break;
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.loginRegisterFragmentZone, selectedFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .show(selectedFragment)
                    .commit();
        }
    }


    private void requestAllPermissionsNeeded() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.INTERNET)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(final MultiplePermissionsReport report) {

                        if (!report.areAllPermissionsGranted()) {
                            Utils.showPermissionDeniedAlert(MainActivity.this)
                                    .setPositiveButton("Verificar permissões", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int a) {
                                            final Intent i = new Intent();
                                            i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            i.addCategory(Intent.CATEGORY_DEFAULT);
                                            i.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                            i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                            startActivity(i);
                                            finish();
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        Toast.makeText(getApplicationContext(), "Request permissions", Toast.LENGTH_LONG).show();
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Utils.showPermissionDeniedAlert(MainActivity.this);
                    }
                })
                .check();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
