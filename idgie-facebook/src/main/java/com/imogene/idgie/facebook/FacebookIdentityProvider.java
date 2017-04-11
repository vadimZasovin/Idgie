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

    public static ClientIdSetter<InternalFinish, FacebookIdentityProvider> startBuilding(){
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

    public interface InternalFinish
            extends Finish<FacebookIdentityProvider>,
            PermissionsSetter<InternalFinish, FacebookIdentityProvider>,
            ApiVersionSetter<InternalFinish, FacebookIdentityProvider>{

        InternalFinish permissions(@Nullable String... permissions);

        InternalFinish apiVersion(@Nullable String apiVersion);

        FacebookIdentityProvider build();
    }

    private static class InternalBuilder
            extends AbstractBuilder<InternalFinish, FacebookIdentityProvider>
            implements InternalFinish{

        private static final String BASE_AUTHORIZATION_URL = "https://facebook.com/";
        private static final String RESPONSE_TYPE = "token";

        private String clientId;
        private String redirectUri;
        private String[] permissions;
        private String apiVersion;

        private InternalBuilder(){
            super(BASE_AUTHORIZATION_URL);
        }

        @Override
        public InternalBuilder clientId(@NonNull String clientId) {
            ArgumentValidator.throwIfEmpty(clientId, "Client id");
            this.clientId = clientId;
            return this;
        }

        @Override
        public InternalBuilder redirectUri(@NonNull String redirectUri) {
            ArgumentValidator.throwIfEmpty(redirectUri, "Redirect uri");
            this.redirectUri = redirectUri;
            return this;
        }

        @Override
        public InternalBuilder permissions(@Nullable String... permissions) {
            this.permissions = permissions;
            return this;
        }

        @Override
        public InternalBuilder apiVersion(@Nullable String apiVersion) {
            this.apiVersion = apiVersion;
            return this;
        }

        @Override
        public FacebookIdentityProvider build() {
            if(!TextUtils.isEmpty(apiVersion)){
                apiVersion = FacebookApi.DEFAULT_API_VERSION;
            }
            appendValue(apiVersion);
            appendValue("/dialog/oauth");
            appendUrlParameter("client_id", clientId);
            appendUrlParameter("redirect_uri", redirectUri);
            appendUrlParameter("scope", ',', permissions);
            appendUrlParameter("response_type", RESPONSE_TYPE);
            return new FacebookIdentityProvider(getUrl(), redirectUri);
        }
    }
}