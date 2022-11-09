package com.nimitzpro.rio22;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewGeneral extends AppCompatActivity {

    android.webkit.WebView teamwebView;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String url = (String) getIntent().getExtras().getSerializable("url");

        progressBar = (ProgressBar)findViewById(R.id.loadingIndicator);


        teamwebView = findViewById(R.id.teamWebView);
        teamwebView.loadUrl(url);

        WebSettings webSettings = teamwebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        teamwebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                setTitle("Loading...");
            }

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                setTitle(view.getTitle());
            }
        });

    }

}