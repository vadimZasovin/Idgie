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

import com.imogene.idgie.AccessToken;
import com.imogene.idgie.ApiCore;
import com.imogene.idgie.ApiManager;
import com.imogene.idgie.ApiRequestException;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;

/**
 * Created by Admin on 29.03.2017.
 */

public class FacebookApiManager extends ApiManager{

    private static volatile FacebookApiManager instance;
    private final FacebookApi api;

    private FacebookApiManager(Builder builder) {
        super(builder);
        ApiCore.Builder<FacebookApi> apiBuilder = new ApiCore.Builder<>();
        apiBuilder.apiClass(FacebookApi.class)
                .accessToken(accessToken)
                .baseUrl(FacebookApi.BASE_URL)
                .readTimeout(readTimeout, readTimeoutTimeUnit);
        if(loggingEnabled){
            apiBuilder.enableLogging();
        }
        ApiCore<FacebookApi> apiCore = apiBuilder.build();
        api = apiCore.api();
    }

    public static void setInstance(FacebookApiManager instance){
        FacebookApiManager.instance = instance;
    }

    public static FacebookApiManager getInstance(){
        return instance;
    }

    public FacebookProfile getProfile(String... fields) throws ApiRequestException{
        String token = accessToken.token;
        String fieldsParam = FacebookProfileFields.createSingleUrlParameter(fields);
        Call<FacebookProfile> call = api.getProfile(token, fieldsParam);
        return executeCall(call);
    }

    public static final class Builder extends ApiManager.Builder<FacebookApiManager>{

        public Builder(AccessToken accessToken) {
            super(accessToken);
        }

        @Override
        public ApiManager.Builder<FacebookApiManager> enableLogging() {
            return super.enableLogging();
        }

        @Override
        public ApiManager.Builder<FacebookApiManager> readTimeout(long timeout, TimeUnit timeUnit) {
            return super.readTimeout(timeout, timeUnit);
        }

        @Override
        public FacebookApiManager build() {
            return new FacebookApiManager(this);
        }
    }
}