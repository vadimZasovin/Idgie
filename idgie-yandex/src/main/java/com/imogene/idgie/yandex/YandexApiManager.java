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

import com.imogene.idgie.AccessToken;
import com.imogene.idgie.ApiCore;
import com.imogene.idgie.ApiManager;
import com.imogene.idgie.ApiRequestException;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;

/**
 * Created by vadim on 4/1/17.
 */

public class YandexApiManager extends ApiManager {

    private static volatile YandexApiManager instance;
    private final YandexApi api;

    private YandexApiManager(Builder builder) {
        super(builder);
        ApiCore.Builder<YandexApi> apiBuilder = new ApiCore.Builder<>();
        apiBuilder.apiClass(YandexApi.class)
                .accessToken(accessToken)
                .baseUrl(YandexApi.BASE_URL)
                .readTimeout(readTimeout, readTimeoutTimeUnit);
        if(loggingEnabled){
            apiBuilder.enableLogging();
        }
        ApiCore<YandexApi> apiCore = apiBuilder.build();
        api = apiCore.api();
    }

    public static void setInstance(YandexApiManager instance){
        YandexApiManager.instance = instance;
    }

    public static YandexApiManager getInstance(){
        return instance;
    }

    public YandexProfile getProfile() throws ApiRequestException{
        Call<YandexProfile> call = api.getProfile();
        return executeCall(call);
    }

    public static final class Builder extends ApiManager.Builder<YandexApiManager>{

        public Builder(AccessToken accessToken) {
            super(accessToken);
        }

        @Override
        public ApiManager.Builder<YandexApiManager> enableLogging() {
            return super.enableLogging();
        }

        @Override
        public ApiManager.Builder<YandexApiManager> readTimeout(long timeout, TimeUnit timeUnit) {
            return super.readTimeout(timeout, timeUnit);
        }

        @Override
        public YandexApiManager build() {
            return new YandexApiManager(this);
        }
    }
}