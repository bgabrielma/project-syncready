package com.example.syncreadyapp.views;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.syncreadyapp.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity {

    private ZXingScannerView scannerView;
    private TextView scannerResult;
    private List<BarcodeFormat> barcodeFormats = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

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

        scannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result rawResult) {
                // do something
                // ...

                //recursive method
                scannerView.resumeCameraPreview(this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }
}