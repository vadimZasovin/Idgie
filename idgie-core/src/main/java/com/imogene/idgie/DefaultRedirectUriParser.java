package com.imogene.idgie;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 02.12.2016.
 */

public class DefaultRedirectUriParser implements RedirectUriParser {

    private static final String DEFAULT_TOKEN_TYPE = "Bearer";
    private static final long DEFAULT_EXPIRES_IN = Long.MAX_VALUE;

    private final String tokenParamName;
    private final String typeParamName;
    private final String expiresParamName;

    private AuthorizationResultImpl authorizationResult;
    private AccessToken accessToken;

    public DefaultRedirectUriParser(@NonNull String tokenParamName,
                                    @Nullable String typeParamName,
                                    @Nullable String expiresParamName){
        ArgumentValidator.throwIfEmpty(tokenParamName, "Token param name");
        this.tokenParamName = tokenParamName;
        this.typeParamName = typeParamName;
        this.expiresParamName = expiresParamName;
    }

    @NonNull
    @Override
    public AuthorizationResult parse(String redirectUri) {
        authorizationResult = new AuthorizationResultImpl();
        if(setupToken(redirectUri)){
            setupTokenType(redirectUri);
            setupExpiresIn(redirectUri);
        }
        return authorizationResult;
    }

    private boolean setupToken(String redirectUri){
        Pattern pattern = Pattern.compile(tokenParamName + "=(.*?)(&|$)");
        Matcher matcher = pattern.matcher(redirectUri);
        if(matcher.find()){
            accessToken = new AccessToken();
            accessToken.token = matcher.group(1);
            authorizationResult.accessToken = accessToken;
            return true;
        }
        return false;
    }

    private void setupTokenType(String redirectUri){
        boolean useDefaultType = true;
        if(!TextUtils.isEmpty(typeParamName)){
            Pattern pattern = Pattern.compile(typeParamName + "=(.*?)(&|$)");
            Matcher matcher = pattern.matcher(redirectUri);
            if(matcher.find()){
                accessToken.type = matcher.group(1);
                useDefaultType = false;
            }
        }
        if(useDefaultType){
            accessToken.type = DEFAULT_TOKEN_TYPE;
        }
    }

    private void setupExpiresIn(String redirectUri){
        boolean useDefaultExpiresIn = true;
        if(!TextUtils.isEmpty(expiresParamName)){
            Pattern pattern = Pattern.compile(expiresParamName + "=(.*?)(&|$)");
            Matcher matcher = pattern.matcher(redirectUri);
            if(matcher.find()){
                accessToken.expiresIn = Long.parseLong(matcher.group(1));
                useDefaultExpiresIn = false;
            }
        }
        if(useDefaultExpiresIn){
            accessToken.expiresIn = DEFAULT_EXPIRES_IN;
        }
    }
}
