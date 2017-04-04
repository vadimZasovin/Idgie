package com.imogene.idgie.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.imogene.idgie.AccessToken;
import com.imogene.idgie.AuthorizationResult;
import com.imogene.idgie.IdentityProvider;
import com.imogene.idgie.facebook.FacebookApi;
import com.imogene.idgie.facebook.FacebookIdentityProvider;
import com.imogene.idgie.facebook.FacebookPermissions;
import com.imogene.idgie.google.GoogleIdentityProvider;
import com.imogene.idgie.vk.VkAuthorizationResult;
import com.imogene.idgie.vk.VkIdentityProvider;
import com.imogene.idgie.yandex.YandexIdentityProvider;

/**
 * Created by Admin on 29.03.2017.
 */

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, BaseAsyncTask.Callbacks{

    private static final int RC_AUTHORIZE = 1;

    private IdentityProvider identityProvider;

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
                identityProvider = createFacebookIdentityProvider();
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
        if(identityProvider.usesCustomRedirectUriScheme()){
            identityProvider.initiateAuthorizationFlow(this);
        }else {
            identityProvider.initiateAuthorizationFlow(this, RC_AUTHORIZE);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        AuthorizationResult result = extractAuthorizationResult(intent);
        loadProfileInfo(result);
    }

    private AuthorizationResult extractAuthorizationResult(Intent intent){
        if(identityProvider != null){
            return identityProvider.extractAuthorizationResultFromIntent(intent);
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
            loadProfileInfoInternal(result);
        }
    }

    private void loadProfileInfoInternal(AuthorizationResult result){
        String name = identityProvider.getName();
        LoadProfileInfoAsyncTask asyncTask = new LoadProfileInfoAsyncTask(this, 0, name);
        if(VkIdentityProvider.NAME.equals(name)){
            VkAuthorizationResult vkResult = (VkAuthorizationResult) result;
            String vkEmail = vkResult.getEmail();
            asyncTask.setVkEmail(vkEmail);
        }
        AccessToken accessToken = result.getAccessToken();
        asyncTask.execute(accessToken);
    }

    @Override
    public void onTaskStarted(int taskId) {}

    @Override
    public void onTaskFinished(int taskId, Object result) {
        BaseProfile profile = (BaseProfile) result;
        String name = profile.getName();
        String email = profile.getEmail();
        String message = getString(R.string.profile_info, name, email);
        showToast(message, true);
    }

    private void showToast(String message, boolean lengthLong){
        int length = lengthLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        Toast.makeText(this, message, length).show();
    }

    @Override
    public void onTaskError(int taskId, String errorMessage) {
        if(!TextUtils.isEmpty(errorMessage)){
            showToast(errorMessage, false);
        }
    }
}
