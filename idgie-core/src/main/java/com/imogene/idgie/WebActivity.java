/*Copyright 2017 Vadim Zasovin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
package com.imogene.idgie;

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

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        webView = new WebView(this);
        setContentView(webView);

        initWebView();
        loadUrl(intent);

        String redirectUri = intent.getStringExtra(EXTRA_REDIRECT_URI);
        WebViewClient client = new InternalWebViewClient(redirectUri);
        webView.setWebViewClient(client);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    private void loadUrl(Intent intent){
        String url = intent.getStringExtra(EXTRA_URL);
        webView.loadUrl(url);
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