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
package com.imogene.idgie.google;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.imogene.idgie.AbstractIdentityProvider;
import com.imogene.idgie.ArgumentValidator;
import com.imogene.idgie.DefaultRedirectUriParser;
import com.imogene.idgie.RedirectUriParser;

/**
 * Created by Admin on 01.12.2016.
 */

public class GoogleIdentityProvider extends AbstractIdentityProvider {

    public static final String NAME = "Google";

    private GoogleIdentityProvider(String authorizationUrl, String redirectUri){
        super(authorizationUrl, redirectUri);
    }

    public static ClientIdSetter<InternalFinish, GoogleIdentityProvider> startBuilding(){
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

    public interface InternalFinish extends Finish<GoogleIdentityProvider>,
            PermissionsSetter<InternalFinish, GoogleIdentityProvider>{

        InternalFinish permissions(@Nullable String... permissions);

        GoogleIdentityProvider build();
    }

    private static class InternalBuilder
            extends AbstractBuilder<InternalFinish, GoogleIdentityProvider>
            implements InternalFinish {

        private static final String BASE_AUTHORIZATION_URL =
                "https://accounts.google.com/o/oauth2/v2/auth";
        private static final String RESPONSE_TYPE = "token";

        private String redirectUri;

        private InternalBuilder(){
            super(BASE_AUTHORIZATION_URL);
            appendUrlParameter("response_type", RESPONSE_TYPE);
        }

        @Override
        public InternalBuilder clientId(@NonNull String clientId) {
            ArgumentValidator.throwIfEmpty(clientId, "Client id");
            appendUrlParameter("client_id", clientId);
            return this;
        }

        @Override
        public InternalBuilder permissions(String... permissions) {
            appendUrlParameter("scope", ' ', permissions);
            return this;
        }

        @Override
        public InternalBuilder redirectUri(@NonNull String redirectUri) {
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