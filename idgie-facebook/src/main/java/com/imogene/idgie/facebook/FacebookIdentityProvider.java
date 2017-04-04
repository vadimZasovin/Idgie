/*Copyright 2017 Vadim Zasovin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
package com.imogene.idgie.facebook;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.imogene.idgie.AbstractIdentityProvider;
import com.imogene.idgie.DefaultRedirectUriParser;
import com.imogene.idgie.RedirectUriParser;
import com.imogene.idgie.ArgumentValidator;

/**
 * Created by Admin on 01.12.2016.
 */

public class FacebookIdentityProvider extends AbstractIdentityProvider {

    public static final String NAME = "Facebook";

    private FacebookIdentityProvider(String authorizationUrl, String redirectUri){
        super(authorizationUrl, redirectUri);
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
    protected RedirectUriParser getRedirectUriParser() {
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

        private String redirectUri;

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
            this.redirectUri = redirectUri;
            return this;
        }

        @Override
        public FacebookIdentityProvider build() {
            appendUrlParameter("response_type", RESPONSE_TYPE);
            return new FacebookIdentityProvider(getUrl(), redirectUri);
        }
    }
}