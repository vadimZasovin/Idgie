package com.appcraft.idgie.vk;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.appcraft.idgie.AbstractIdentityProvider;
import com.appcraft.idgie.ArgumentValidator;
import com.appcraft.idgie.RedirectUriParser;

/**
 * Created by Admin on 01.12.2016.
 */

public class VkIdentityProvider extends AbstractIdentityProvider {

    public static final String NAME = "Vk";

    private final String mAuthorizationUrl;
    private final String mRedirectUri;

    private VkIdentityProvider(String authorizationUrl, String redirectUri){
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
    protected String getAuthorizationUrl() {
        return mAuthorizationUrl;
    }

    @NonNull
    @Override
    protected String getRedirectUri() {
        return mRedirectUri;
    }

    @NonNull
    @Override
    protected RedirectUriParser getRedirectUriParser() {
        return new VkRedirectUriParser();
    }

    public interface ApiVersionSetter{

        RevokeSetter apiVersion(@Nullable String apiVersion);
    }

    public interface RevokeSetter{

        PermissionsSetter revoke(boolean revoke);
    }

    private static class InternalBuilder
            extends AbstractBuilder<VkIdentityProvider>
            implements PermissionsSetter,
            ApiVersionSetter, RevokeSetter{

        private static final String BASE_AUTHORIZATION_URL = "https://oauth.vk.com/authorize";
        private static final String DISPLAY_MODE = "mobile";
        private static final String RESPONSE_TYPE = "token";

        private String mRedirectUri;

        private InternalBuilder(){
            appendValue(BASE_AUTHORIZATION_URL);
            appendUrlParameter("display", DISPLAY_MODE);
            appendUrlParameter("response_type", RESPONSE_TYPE);
        }

        @Override
        public RevokeSetter apiVersion(@Nullable String apiVersion) {
            apiVersion = !TextUtils.isEmpty(apiVersion) ?
                    apiVersion :
                    VkApi.DEFAULT_API_VERSION;
            appendUrlParameter("v", apiVersion);
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
            String scopesString = null;
            if(permissions != null && permissions.length > 0){
                scopesString = createPermissionsUrlParameterValue(permissions);
            }
            if(TextUtils.isEmpty(scopesString)){
                scopesString = VkPermissions.EMAIL;
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
        public Finish<VkIdentityProvider> redirectUri(@NonNull String redirectUri) {
            ArgumentValidator.throwIfEmpty(redirectUri, "Redirect uri");
            appendUrlParameter("redirect_uri", redirectUri);
            mRedirectUri = redirectUri;
            return this;
        }

        @Override
        public PermissionsSetter revoke(boolean revoke) {
            String revokeValue = revoke ? "1" : "0";
            appendUrlParameter("revoke", revokeValue);
            return this;
        }

        @Override
        public VkIdentityProvider build() {
            return new VkIdentityProvider(getUrl(), mRedirectUri);
        }
    }
}