package com.imogene.idgie.instagram;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.imogene.idgie.AbstractIdentityProvider;
import com.imogene.idgie.ArgumentValidator;
import com.imogene.idgie.DefaultRedirectUriParser;
import com.imogene.idgie.RedirectUriParser;

/**
 * Created by Admin on 10.04.2017.
 */

public class InstagramIdentityProvider  extends AbstractIdentityProvider{

    public static final String NAME = "Instagram";

    private InstagramIdentityProvider(String authorizationUrl, String redirectUri) {
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
        return new DefaultRedirectUriParser("access_token", null, null);
    }

    private static class InternalBuilder
            extends AbstractBuilder<InstagramIdentityProvider>
            implements PermissionsSetter{

        private static final String BASE_AUTHORIZATION_URL =
                "https://api.instagram.com/oauth/authorize/";
        private static final String RESPONSE_TYPE = "token";

        private String redirectUri;

        private InternalBuilder() {
            super(BASE_AUTHORIZATION_URL);
        }

        @Override
        public ClientIdSetter permissions(@Nullable String... permissions) {
            appendUrlParameter("scope", '+', permissions);
            return this;
        }

        @Override
        public RedirectUriSetter clientId(@NonNull String clientId) {
            ArgumentValidator.throwIfEmpty(clientId, "Client id");
            appendUrlParameter("client_id", clientId);
            return this;
        }

        @Override
        public Finish<InstagramIdentityProvider> redirectUri(@NonNull String redirectUri) {
            ArgumentValidator.throwIfEmpty(redirectUri, "Redirect uri");
            appendUrlParameter("redirect_uri", redirectUri);
            this.redirectUri = redirectUri;
            return this;
        }

        @Override
        public InstagramIdentityProvider build() {
            appendUrlParameter("response_type", RESPONSE_TYPE);
            return new InstagramIdentityProvider(getUrl(), redirectUri);
        }
    }
}