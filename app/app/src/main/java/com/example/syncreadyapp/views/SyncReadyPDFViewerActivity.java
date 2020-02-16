package com.example.syncreadyapp.views;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.SyncreadyPdfViewerBinding;
import com.example.syncreadyapp.services.RetrofitInstance;

public class SyncReadyPDFViewerActivity extends AppCompatActivity {

    private SyncreadyPdfViewerBinding syncreadyPdfViewerBinding;
    private String url = null;
    private String formattedUrl = null;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        syncreadyPdfViewerBinding = DataBindingUtil.setContentView(this, R.layout.syncready_pdf_viewer);
        formattedUrl = "https://docs.google.com/viewer?url=" + RetrofitInstance.BASE_URL + "public/files/"
                + getIntent().getExtras().getString("syncready_room_designation", "http://www.orimi.com/pdf-test.pdf");

        syncreadyPdfViewerBinding.webview.getSettings().setJavaScriptEnabled(true);

        syncreadyPdfViewerBinding.webview.loadUrl(formattedUrl);
        syncreadyPdfViewerBinding.webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    syncreadyPdfViewerBinding.progressbar.setVisibility(View.GONE);
                }
            }
        });
    }
}
