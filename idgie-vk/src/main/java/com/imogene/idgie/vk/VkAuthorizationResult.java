package com.imogene.idgie.vk;

import com.imogene.idgie.AccessToken;
import com.imogene.idgie.AuthorizationResult;

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