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
package com.imogene.idgie.vk;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.imogene.idgie.AbstractIdentityProvider;
import com.imogene.idgie.ArgumentValidator;
import com.imogene.idgie.RedirectUriParser;

/**
 * Created by Admin on 01.12.2016.
 */

public class VkIdentityProvider extends AbstractIdentityProvider {

    public static final String NAME = "Vk";

    private VkIdentityProvider(String authorizationUrl, String redirectUri){
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

        private String redirectUri;

        private InternalBuilder(){
            super(BASE_AUTHORIZATION_URL);
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
            appendUrlParameter("scope", ',', permissions);
            return this;
        }

        @Override
        public Finish<VkIdentityProvider> redirectUri(@NonNull String redirectUri) {
            ArgumentValidator.throwIfEmpty(redirectUri, "Redirect uri");
            appendUrlParameter("redirect_uri", redirectUri);
            this.redirectUri = redirectUri;
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
            return new VkIdentityProvider(getUrl(), redirectUri);
        }
    }
}