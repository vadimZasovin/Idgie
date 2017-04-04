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

import com.imogene.idgie.AccessToken;
import com.imogene.idgie.ApiCore;
import com.imogene.idgie.ApiManager;
import com.imogene.idgie.ApiRequestException;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;

/**
 * Created by vadim on 4/1/17.
 */

public class GoogleApiManager extends ApiManager {

    private static volatile GoogleApiManager instance;
    private final GoogleApi api;

    private GoogleApiManager(Builder builder) {
        super(builder);
        ApiCore.Builder<GoogleApi> apiBuilder = new ApiCore.Builder<>();
        apiBuilder.apiClass(GoogleApi.class)
                .accessToken(accessToken)
                .baseUrl(GoogleApi.BASE_URL)
                .readTimeout(readTimeout, readTimeoutTimeUnit);
        if(loggingEnabled){
            apiBuilder.enableLogging();
        }
        ApiCore<GoogleApi> apiCore = apiBuilder.build();
        api = apiCore.api();
    }

    public static void setInstance(GoogleApiManager instance){
        GoogleApiManager.instance = instance;
    }

    public static GoogleApiManager getInstance(){
        return instance;
    }

    public GoogleProfile getProfile() throws ApiRequestException{
        Call<GoogleProfile> call = api.getProfile(GoogleApi.DEFAULT_API_VERSION);
        return executeCall(call);
    }

    public static final class Builder extends ApiManager.Builder<GoogleApiManager>{

        public Builder(AccessToken accessToken) {
            super(accessToken);
        }

        @Override
        public ApiManager.Builder<GoogleApiManager> enableLogging() {
            return super.enableLogging();
        }

        @Override
        public ApiManager.Builder<GoogleApiManager> readTimeout(long timeout, TimeUnit timeUnit) {
            return super.readTimeout(timeout, timeUnit);
        }

        @Override
        public GoogleApiManager build() {
            return new GoogleApiManager(this);
        }
    }
}