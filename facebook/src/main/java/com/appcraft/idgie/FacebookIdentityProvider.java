package com.appcraft.idgie;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Admin on 01.12.2016.
 */

public class FacebookIdentityProvider extends AbstractIdentityProvider {

    public static final String NAME = "Facebook";

    private final String mAuthorizationUrl;
    private final String mRedirectUri;

    private FacebookIdentityProvider(String authorizationUrl, String redirectUri){
        mAuthorizationUrl = authorizationUrl;
        mRedirectUri = redirectUri;
    }

    public static ApiVersionSetter startBuilding(){
        return new InternalBuilder();
    }

    @NonNull
    @Override
    public String getName() {
        return NAME;
    }

    @NonNull
    @Override
    String getAuthorizationUrl() {
        return mAuthorizationUrl;
    }

    @NonNull
    @Override
    String getRedirectUri() {
        return mRedirectUri;
    }

    @NonNull
    @Override
    RedirectUriParser getRedirectUriParser() {
        return new DefaultRedirectUriParser("access_token", null, "expires_in");
    }

    public interface ApiVersionSetter{

        PermissionsSetter apiVersion(@Nullable String apiVersion);
    }

    private static class InternalBuilder
            extends AbstractBuilder<FacebookIdentityProvider>
            implements ApiVersionSetter, PermissionsSetter {

        private static final String BASE_AUTHORIZATION_URL = "https://facebook.com/";
        private static final String RESPONSE_TYPE = "token";

        private String mRedirectUri;

        private InternalBuilder(){
            appendValue(BASE_AUTHORIZATION_URL);
        }

        @Override
        public PermissionsSetter apiVersion(@Nullable String apiVersion) {
            apiVersion = !TextUtils.isEmpty(apiVersion) ?
                    apiVersion :
                    FacebookApi.DEFAULT_API_VERSION;
            appendValue(apiVersion);
            appendValue("/dialog/oauth");
            return this;
        }

        @Override
        public RedirectUriSetter clientId(@NonNull String clientId) {
            ArgumentValidator.throwIfEmpty(clientId, "Client id");
            appendUrlParameter("client_id", clientId);
            return this;
        }

        @Override
        public ClientIdSetter permissions(String... permissions) {
            String scopesString;
            if(permissions != null && permissions.length > 0){
                scopesString = createPermissionsUrlParameterValue(permissions);
            }else {
                scopesString = FacebookPermissions.PUBLIC_PROFILE;
            }
            appendUrlParameter("scope", scopesString);
            return this;
        }

        private String createPermissionsUrlParameterValue(String... permissions){
            StringBuilder stringBuilder = new StringBuilder();
            for(String permission : permissions){
                if(!TextUtils.isEmpty(permission)){
                    if(stringBuilder.length() > 0){
                        stringBuilder.append(',');
                    }
                    stringBuilder.append(permission);
                }
            }
            return stringBuilder.toString();
        }

        @Override
        public Finish<FacebookIdentityProvider> redirectUri(@NonNull String redirectUri) {
            ArgumentValidator.throwIfEmpty(redirectUri, "Redirect uri");
            appendUrlParameter("redirect_uri", redirectUri);
            mRedirectUri = redirectUri;
            return this;
        }

        @Override
        public FacebookIdentityProvider build() {
            appendUrlParameter("response_type", RESPONSE_TYPE);
            return new FacebookIdentityProvider(getUrl(), mRedirectUri);
        }
    }
}