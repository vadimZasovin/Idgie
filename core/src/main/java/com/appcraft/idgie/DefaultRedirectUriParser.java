package com.appcraft.idgie;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 02.12.2016.
 */

class DefaultRedirectUriParser implements RedirectUriParser {

    private static final String DEFAULT_TOKEN_TYPE = "Bearer";
    private static final long DEFAULT_EXPIRES_IN = Long.MAX_VALUE;

    private final String mTokenParamName;
    private final String mTypeParamName;
    private final String mExpiresParamName;

    private AuthorizationResultImpl mAuthorizationResult;
    private AccessToken mAccessToken;

    DefaultRedirectUriParser(@NonNull String tokenParamName,
                                    @Nullable String typeParamName,
                                    @Nullable String expiresParamName){
        ArgumentValidator.throwIfEmpty(tokenParamName, "Token param name");
        mTokenParamName = tokenParamName;
        mTypeParamName = typeParamName;
        mExpiresParamName = expiresParamName;
    }

    @NonNull
    @Override
    public AuthorizationResult parse(String redirectUri) {
        mAuthorizationResult = new AuthorizationResultImpl();
        if(setupTokenAndCreateIfNeeded(redirectUri)){
            setupTokenType(redirectUri);
            setupExpiresIn(redirectUri);
        }
        return mAuthorizationResult;
    }

    private boolean setupTokenAndCreateIfNeeded(String redirectUri){
        Pattern pattern = Pattern.compile(mTokenParamName + "=(.*?)(&|$)");
        Matcher matcher = pattern.matcher(redirectUri);
        if(matcher.find()){
            mAccessToken = new AccessToken();
            mAccessToken.token = matcher.group(1);
            mAuthorizationResult.mAccessToken = mAccessToken;
            return true;
        }
        return false;
    }

    private void setupTokenType(String redirectUri){
        boolean useDefaultType = true;
        if(!TextUtils.isEmpty(mTypeParamName)){
            Pattern pattern = Pattern.compile(mTypeParamName + "=(.*?)(&|$)");
            Matcher matcher = pattern.matcher(redirectUri);
            if(matcher.find()){
                mAccessToken.type = matcher.group(1);
                useDefaultType = false;
            }
        }
        if(useDefaultType){
            mAccessToken.type = DEFAULT_TOKEN_TYPE;
        }
    }

    private void setupExpiresIn(String redirectUri){
        boolean useDefaultExpiresIn = true;
        if(!TextUtils.isEmpty(mExpiresParamName)){
            Pattern pattern = Pattern.compile(mExpiresParamName + "=(.*?)(&|$)");
            Matcher matcher = pattern.matcher(redirectUri);
            if(matcher.find()){
                mAccessToken.expiresIn = Long.parseLong(matcher.group(1));
                useDefaultExpiresIn = false;
            }
        }
        if(useDefaultExpiresIn){
            mAccessToken.expiresIn = DEFAULT_EXPIRES_IN;
        }
    }
}
