package com.appcraftlab.idgie.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.appcraft.idgie.AccessToken;
import com.appcraft.idgie.ApiRequestException;
import com.appcraft.idgie.AuthorizationResult;
import com.appcraft.idgie.IdentityProvider;
import com.appcraft.idgie.facebook.FacebookApi;
import com.appcraft.idgie.facebook.FacebookApiManager;
import com.appcraft.idgie.facebook.FacebookIdentityProvider;
import com.appcraft.idgie.facebook.FacebookPermissions;
import com.appcraft.idgie.facebook.FacebookProfileFields;
import com.appcraft.idgie.google.GoogleIdentityProvider;
import com.appcraft.idgie.vk.VkIdentityProvider;
import com.appcraft.idgie.yandex.YandexIdentityProvider;

/**
 * Created by Admin on 29.03.2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int RC_AUTHORIZE = 1;

    private IdentityProvider mIdentityProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.facebookButton).setOnClickListener(this);
        findViewById(R.id.googleButton).setOnClickListener(this);
        findViewById(R.id.vkButton).setOnClickListener(this);
        findViewById(R.id.yandexButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String name = null;
        switch (id){
            case R.id.facebookButton:
                name = FacebookIdentityProvider.NAME;
                break;
            case R.id.googleButton:
                name = GoogleIdentityProvider.NAME;
                break;
            case R.id.vkButton:
                name = VkIdentityProvider.NAME;
                break;
            case R.id.yandexButton:
                name = YandexIdentityProvider.NAME;
                break;
        }
        authorize(name);
    }

    private void authorize(String name){
        switch (name){
            case FacebookIdentityProvider.NAME:
                mIdentityProvider = createFacebookIdentityProvider();
                break;
            case GoogleIdentityProvider.NAME:
                break;
            case VkIdentityProvider.NAME:
                break;
            case YandexIdentityProvider.NAME:
                break;
        }
        authorize();
    }

    private IdentityProvider createFacebookIdentityProvider(){
        String clientId = getString(R.string.facebook_app_id);
        String redirectUri = getString(R.string.facebook_redirect_uri);
        return FacebookIdentityProvider.startBuilding()
                .apiVersion(FacebookApi.DEFAULT_API_VERSION)
                .permissions(FacebookPermissions.EMAIL, FacebookPermissions.PUBLIC_PROFILE)
                .clientId(clientId)
                .redirectUri(redirectUri)
                .build();
    }

    private void authorize(){
        if(mIdentityProvider.usesCustomRedirectUriScheme()){
            mIdentityProvider.initiateAuthorizationFlow(this);
        }else {
            mIdentityProvider.initiateAuthorizationFlow(this, RC_AUTHORIZE);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        AuthorizationResult result = extractAuthorizationResult(intent);
        loadProfileInfo(result);
    }

    private AuthorizationResult extractAuthorizationResult(Intent intent){
        if(mIdentityProvider != null){
            return mIdentityProvider.extractAuthorizationResultFromIntent(intent);
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case RC_AUTHORIZE:
                if(resultCode == Activity.RESULT_OK && data != null){
                    AuthorizationResult result = extractAuthorizationResult(data);
                    loadProfileInfo(result);
                }
                break;
        }
    }

    private void loadProfileInfo(AuthorizationResult result){
        if(result != null && result.isAuthorized()){
            AccessToken accessToken = result.getAccessToken();
            loadProfileInfo(accessToken);
        }
    }

    private void loadProfileInfo(final AccessToken accessToken){
        new Thread(){
            @Override
            public void run() {
                try {
                    new FacebookApiManager.Builder()
                            .accessToken(accessToken)
                            .enableLogging(true)
                            .build().getProfile(FacebookProfileFields.NAME);
                } catch (ApiRequestException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
