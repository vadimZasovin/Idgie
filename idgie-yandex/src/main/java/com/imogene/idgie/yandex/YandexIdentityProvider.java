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

        ClientIdSetter<Finish<YandexIdentityProvider>, YandexIdentityProvider>
        deviceName(@NonNull String deviceName);
    }

    private static class InternalBuilder
            extends AbstractBuilder<Finish<YandexIdentityProvider>, YandexIdentityProvider>
            implements DeviceIdSetter, DeviceNameSetter, Finish<YandexIdentityProvider> {

        private static final String BASE_AUTHORIZATION_URL = "https://oauth.yandex.ru/authorize";
        private static final String RESPONSE_TYPE = "token";
        private static final String DISPLAY_MODE = "popup";
        private static final String FORCE_CONFIRM = Boolean.toString(true);

        private String redirectUri;

        private InternalBuilder(){
            super(BASE_AUTHORIZATION_URL);
            appendUrlParameter("response_type", RESPONSE_TYPE);
            appendUrlParameter("display", DISPLAY_MODE);
            appendUrlParameter("force_confirm", FORCE_CONFIRM);
        }

        @Override
        public DeviceNameSetter deviceId(@NonNull String deviceId) {
            ArgumentValidator.throwIfEmpty(deviceId, "Device id");
            appendUrlParameter("device_id", deviceId);
            return this;
        }

        @Override
        public InternalBuilder deviceName(@NonNull String deviceName) {
            ArgumentValidator.throwIfEmpty(deviceName, "Device name");
            appendUrlParameter("device_name", deviceName);
            return this;
        }

        @Override
        public InternalBuilder clientId(@NonNull String clientId) {
            ArgumentValidator.throwIfEmpty(clientId, "Client id");
            appendUrlParameter("client_id", clientId);
            return this;
        }

        @Override
        public InternalBuilder redirectUri(@NonNull String redirectUri) {
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