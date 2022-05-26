package com.example.webviewapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Surface;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private WebView webView;
    private Button backBtn;
    private Button nextBtn;
    SwipeRefreshLayout mySwipeRefreshLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mySwipeRefreshLayout = findViewById(R.id.swipe_container);
        mySwipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);

        mySwipeRefreshLayout.setColorSchemeColors(
                  Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        webView = findViewById(R.id.webView);
        backBtn = findViewById(R.id.back);
        nextBtn = findViewById(R.id.next);
        webView.getSettings().setBuiltInZoomControls(true);
        // включаем поддержку JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        // указываем страницу загрузки
        webView.loadUrl("https://www.google.com/");

        webView.setWebViewClient(new MyWebViewClient());

    }

    public void back(View view) {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void next(View view) {
        if(webView.canGoForward()) {
            webView.goForward();
        }
    }


    public void clearCache(View view) {
        webView.clearCache(true);
    }

    public void copyURL(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("m", webView.getOriginalUrl());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), webView.getOriginalUrl() +" copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        mySwipeRefreshLayout.setRefreshing(false);
        webView.reload();
    }

}

