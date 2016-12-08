package com.appcraft.idgie;

import android.text.TextUtils;

/**
 * Created by Admin on 07.12.2016.
 */

class AuthorizationResultImpl implements AuthorizationResult {

    AccessToken mAccessToken;

    @Override
    public AccessToken getAccessToken() {
        return mAccessToken;
    }

    @Override
    public boolean isAuthorized() {
        return mAccessToken != null && !TextUtils.isEmpty(mAccessToken.token);
    }
}
