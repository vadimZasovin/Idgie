package com.imogene.idgie.vk;

import android.support.annotation.NonNull;

import com.imogene.idgie.AuthorizationResult;
import com.imogene.idgie.DefaultRedirectUriParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 05.12.2016.
 */

class VkRedirectUriParser extends DefaultRedirectUriParser {

    private static final String TOKEN_PARAM_NAME = "access_token";
    private static final String EXPIRES_IN_PARAM_NAME = "expires_in";
    private static final String USER_ID_PARAM_NAME = "user_id";
    private static final String EMAIL_PARAM_NAME = "email";

    private VkAuthorizationResult authorizationResult;

    VkRedirectUriParser() {
        super(TOKEN_PARAM_NAME, null, EXPIRES_IN_PARAM_NAME);
    }

    @NonNull
    @Override
    public AuthorizationResult parse(String redirectUri) {
        AuthorizationResult result = super.parse(redirectUri);
        authorizationResult = new VkAuthorizationResult(result);
        if(authorizationResult.isAuthorized()){
            setupEmail(redirectUri);
            setupUserId(redirectUri);
        }
        return authorizationResult;
    }

    private void setupEmail(String redirectUri){
        Pattern pattern = Pattern.compile(EMAIL_PARAM_NAME + "=(.*?)(&|$)");
        Matcher matcher = pattern.matcher(redirectUri);
        if(matcher.find()){
            authorizationResult.email = matcher.group(1);
        }
    }

    private void setupUserId(String redirectUri){
        Pattern pattern = Pattern.compile(USER_ID_PARAM_NAME + "=(.*?)(&|$)");
        Matcher matcher = pattern.matcher(redirectUri);
        if(matcher.find()){
            authorizationResult.userId = matcher.group(1);
        }
    }
}