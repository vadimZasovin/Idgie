package com.imogene.idgie;

import android.text.TextUtils;

/**
 * Created by Admin on 07.12.2016.
 */

public class AuthorizationResultImpl implements AuthorizationResult {

    AccessToken accessToken;

    @Override
    public AccessToken getAccessToken() {
        return accessToken;
    }

    @Override
    public boolean isAuthorized() {
        return accessToken != null && !TextUtils.isEmpty(accessToken.token);
    }
}
