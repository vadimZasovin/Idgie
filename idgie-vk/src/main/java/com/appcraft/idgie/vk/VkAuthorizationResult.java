package com.appcraft.idgie.vk;

import com.appcraft.idgie.AccessToken;
import com.appcraft.idgie.AuthorizationResult;

/**
 * Created by Admin on 07.12.2016.
 */

public class VkAuthorizationResult implements AuthorizationResult {

    private final AuthorizationResult authorizationResult;

    String email;
    String userId;

    VkAuthorizationResult(AuthorizationResult authorizationResult){
        this.authorizationResult = authorizationResult;
    }

    @Override
    public AccessToken getAccessToken() {
        return authorizationResult.getAccessToken();
    }

    @Override
    public boolean isAuthorized() {
        return authorizationResult.isAuthorized();
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }
}