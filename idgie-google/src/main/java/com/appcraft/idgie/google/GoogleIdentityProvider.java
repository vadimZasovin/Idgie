package com.appcraft.idgie.google;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.appcraft.idgie.AbstractIdentityProvider;
import com.appcraft.idgie.ArgumentValidator;
import com.appcraft.idgie.DefaultRedirectUriParser;
import com.appcraft.idgie.RedirectUriParser;

/**
 * Created by Admin on 01.12.2016.
 */

public class GoogleIdentityProvider extends AbstractIdentityProvider {

    public static final String NAME = "Google";

    private GoogleIdentityProvider(String authorizationUrl, String redirectUri){
        super(authorizationUrl, redirectUri);
    }

    public static PermissionsSetter startBuilding(){
        return new InternalBuilder();
    }

    @NonNull
    @Override
    public String getName() {
        return NAME;
    }

    @NonNull
    @Override
    protected RedirectUriParser getRedirectUriParser() {
        return new DefaultRedirectUriParser("access_token", "token_type", "expires_in");
    }

    private static class InternalBuilder
            extends AbstractBuilder<GoogleIdentityProvider>
            implements PermissionsSetter {

        private static final String BASE_AUTHORIZATION_URL =
                "https://accounts.google.com/o/oauth2/v2/auth";
        private static final String RESPONSE_TYPE = "token";

        private String redirectUri;

        private InternalBuilder(){
            appendValue(BASE_AUTHORIZATION_URL);
            appendUrlParameter("response_type", RESPONSE_TYPE);
        }

        @Override
        public RedirectUriSetter clientId(@NonNull String clientId) {
            ArgumentValidator.throwIfEmpty(clientId, "Client id");
            appendUrlParameter("client_id", clientId);
            return this;
        }

        @Override
        public ClientIdSetter permissions(String... permissions) {
            String scopesString = null;
            if(permissions != null && permissions.length > 0){
                scopesString = createPermissionsUrlParameterValue(permissions);
            }
            if(TextUtils.isEmpty(scopesString)){
                scopesString = GooglePermissions.PROFILE;
            }
            appendUrlParameter("scope", scopesString);
            return this;
        }

        private String createPermissionsUrlParameterValue(String... permissions){
            StringBuilder stringBuilder = new StringBuilder();
            for(String permission : permissions){
                if(!TextUtils.isEmpty(permission)){
                    if(stringBuilder.length() > 0){
                        stringBuilder.append(' ');
                    }
                    stringBuilder.append(permission);
                }
            }
            return stringBuilder.toString();
        }

        @Override
        public Finish<GoogleIdentityProvider> redirectUri(@NonNull String redirectUri) {
            ArgumentValidator.throwIfEmpty(redirectUri, "Redirect uri");
            appendUrlParameter("redirect_uri", redirectUri);
            this.redirectUri = redirectUri;
            return this;
        }

        @Override
        public GoogleIdentityProvider build() {
            return new GoogleIdentityProvider(getUrl(), redirectUri);
        }
    }
}