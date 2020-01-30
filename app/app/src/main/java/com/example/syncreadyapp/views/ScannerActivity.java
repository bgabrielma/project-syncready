package com.example.syncreadyapp.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.Utils;
import com.example.syncreadyapp.models.room.ResponseRoom;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity {

    private ZXingScannerView scannerView;
    private HomeActivityViewModel homeActivityViewModel;
    private TextView scannerResult;
    private String rawResult;

    private Bundle bundle;
    private List<BarcodeFormat> barcodeFormats = new ArrayList<>();

    private String uuidroom = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        bundle = this.getIntent().getExtras();
        homeActivityViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);

        homeActivityViewModel.uuidMutableLiveData.setValue(bundle.getString("sycnready_user_uuid", null));
        homeActivityViewModel.tokenAccessMutableLiveData.setValue("Bearer " + bundle.getString("syncready_user_token_access", null));
        scannerView = findViewById(R.id.scannerView);

        // configure scanner
        configureScanner();

        // start camera
        scannerView.startCamera();

    }

    private void configureScanner() {
        // Add qrcore format only
        barcodeFormats.add(BarcodeFormat.QR_CODE);
        scannerView.setFormats(barcodeFormats);

        scannerView.setAspectTolerance(0.5f);

        scannerView.setResultHandler(scannerHandler);
    }

    private final ZXingScannerView.ResultHandler scannerHandler = new ZXingScannerView.ResultHandler() {
        @Override
        public void handleResult(Result rawResult) {
            // do something
            homeActivityViewModel.getRoomByQR(ScannerActivity.this.rawResult = rawResult.getText(), homeActivityViewModel.tokenAccessMutableLiveData.getValue()).observe(ScannerActivity.this, getRoomsObserver);
        }
    };

    private final Observer<ResponseRoom> getRoomsObserver = new Observer<ResponseRoom>() {
        @Override
        public void onChanged(ResponseRoom responseRoom) {
            if (responseRoom.getResponse().size() != 0) {

                homeActivityViewModel.validationUserInRoom(homeActivityViewModel.uuidMutableLiveData.getValue(), ScannerActivity.this.uuidroom = responseRoom.getResponse().get(0).getUuidRoom(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                        .observe(ScannerActivity.this, validationUserInRoomObserver);
            } else {
                Utils.showInvalidRoomCode(ScannerActivity.this).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
            }
        }
    };

    private final Observer<JsonObject> validationUserInRoomObserver = new Observer<JsonObject>() {
        @Override
        public void onChanged(JsonObject jsonObject) {
            if(jsonObject.get("count").getAsInt() == 0) {
                Intent roomPreview = new Intent(ScannerActivity.this, RoomPreviewActivity.class);
                bundle.putString("syncready_room_code", rawResult);
                bundle.putString("syncready_room_uuid", ScannerActivity.this.uuidroom);

                roomPreview.putExtras(bundle);
                startActivity(roomPreview);
                finish();
            } else {
                Utils.showAlreadyAdded(ScannerActivity.this).setPositiveButton("Voltar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }
}