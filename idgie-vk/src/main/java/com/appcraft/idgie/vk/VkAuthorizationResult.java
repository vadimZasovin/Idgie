package com.appcraft.idgie.vk;

import com.appcraft.idgie.AccessToken;
import com.appcraft.idgie.AuthorizationResult;

/**
 * Created by Admin on 07.12.2016.
 */

public class VkAuthorizationResult implements AuthorizationResult {

    private final AuthorizationResult mAuthorizationResult;

    String mEmail;
    String mUserId;

    VkAuthorizationResult(AuthorizationResult authorizationResult){
        mAuthorizationResult = authorizationResult;
    }

    @Override
    public AccessToken getAccessToken() {
        return mAuthorizationResult.getAccessToken();
    }

    @Override
    public boolean isAuthorized() {
        return mAuthorizationResult.isAuthorized();
    }

    public String getEmail() {
        return mEmail;
    }

    public String getUserId() {
        return mUserId;
    }
}
