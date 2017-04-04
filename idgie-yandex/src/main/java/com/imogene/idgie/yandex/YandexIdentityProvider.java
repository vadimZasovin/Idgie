package com.imogene.idgie.yandex;

import android.support.annotation.NonNull;

import com.imogene.idgie.AbstractIdentityProvider;
import com.imogene.idgie.ArgumentValidator;
import com.imogene.idgie.DefaultRedirectUriParser;
import com.imogene.idgie.RedirectUriParser;

/**
 * Created by Admin on 01.12.2016.
 */

public class YandexIdentityProvider extends AbstractIdentityProvider {

    public static final String NAME = "Yandex";

    private YandexIdentityProvider(String authorizationUrl, String redirectUri){
        super(authorizationUrl, redirectUri);
    }

    public static DeviceIdSetter startBuilding(){
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

    public interface DeviceIdSetter{

        DeviceNameSetter deviceId(@NonNull String deviceId);
    }

    public interface DeviceNameSetter{

        ClientIdSetter deviceName(@NonNull String deviceName);
    }

    private static class InternalBuilder
            extends AbstractBuilder<YandexIdentityProvider>
            implements DeviceIdSetter, DeviceNameSetter {

        private static final String BASE_AUTHORIZATION_URL = "https://oauth.yandex.ru/authorize";
        private static final String RESPONSE_TYPE = "token";
        private static final String DISPLAY_MODE = "popup";
        private static final String FORCE_CONFIRM = Boolean.toString(true);

        private String redirectUri;

        private InternalBuilder(){
            appendValue(BASE_AUTHORIZATION_URL);
            appendUrlParameter("response_type", RESPONSE_TYPE);
            appendUrlParameter("display", DISPLAY_MODE);
            appendUrlParameter("force_confirm", FORCE_CONFIRM);
        }

        @Override
        public RedirectUriSetter<YandexIdentityProvider> clientId(@NonNull String clientId) {
            ArgumentValidator.throwIfEmpty(clientId, "Client id");
            appendUrlParameter("client_id", clientId);
            return this;
        }

        @Override
        public DeviceNameSetter deviceId(@NonNull String deviceId) {
            ArgumentValidator.throwIfEmpty(deviceId, "Device id");
            appendUrlParameter("device_id", deviceId);
            return this;
        }

        @Override
        public ClientIdSetter deviceName(@NonNull String deviceName) {
            ArgumentValidator.throwIfEmpty(deviceName, "Device name");
            appendUrlParameter("device_name", deviceName);
            return this;
        }

        @Override
        public Finish<YandexIdentityProvider> redirectUri(@NonNull String redirectUri) {
            ArgumentValidator.throwIfEmpty(redirectUri, "Redirect uri");
            this.redirectUri = redirectUri;
            return this;
        }

        @Override
        public YandexIdentityProvider build() {
            return new YandexIdentityProvider(getUrl(), redirectUri);
        }
    }
}