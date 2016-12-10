package com.appcraft.idgie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by Admin on 01.12.2016.
 */

public class WebActivity extends Activity {

    public static final String EXTRA_URL =
            "com.appcraft.uremont.WebActivity.EXTRA_URL";
    public static final String EXTRA_REDIRECT_URI =
            "com.appcraft.uremont.WebActivity.EXTRA_REDIRECT_URI";

    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mWebView = new WebView(this);
        setContentView(mWebView);

        initWebView();
        loadUrl(intent);

        String redirectUri = intent.getStringExtra(EXTRA_REDIRECT_URI);
        WebViewClient client = new InternalWebViewClient(redirectUri);
        mWebView.setWebViewClient(client);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(){
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    private void loadUrl(Intent intent){
        String url = intent.getStringExtra(EXTRA_URL);
        mWebView.loadUrl(url);
    }

    private class InternalWebViewClient extends WebViewClient{

        private final String mRedirectUri;

        private InternalWebViewClient(String redirectUri){
            mRedirectUri = redirectUri;
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.startsWith(mRedirectUri)){
                Uri filledRedirectUri = Uri.parse(url);
                Intent data = new Intent();
                data.setData(filledRedirectUri);
                setResult(RESULT_OK, data);
                finish();
            }else {
                view.loadUrl(url);
            }
            return false;
        }
    }
}