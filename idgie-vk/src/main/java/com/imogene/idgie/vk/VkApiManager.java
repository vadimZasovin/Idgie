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

import com.imogene.idgie.AccessToken;
import com.imogene.idgie.ApiCore;
import com.imogene.idgie.ApiManager;
import com.imogene.idgie.ApiRequestException;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;

/**
 * Created by vadim on 4/1/17.
 */

public class VkApiManager extends ApiManager {

    private static volatile VkApiManager instance;
    private final VkApi api;

    private VkApiManager(Builder builder) {
        super(builder);
        ApiCore.Builder<VkApi> apiBuilder = new ApiCore.Builder<>();
        apiBuilder.apiClass(VkApi.class)
                .accessToken(accessToken)
                .baseUrl(VkApi.BASE_URL)
                .readTimeout(readTimeout, readTimeoutTimeUnit);
        if(loggingEnabled){
            apiBuilder.enableLogging();
        }
        ApiCore<VkApi> apiCore = apiBuilder.build();
        api = apiCore.api();
    }

    public static void setInstance(VkApiManager instance){
        VkApiManager.instance = instance;
    }

    public static VkApiManager getInstance(){
        return instance;
    }

    public VkProfile getProfile() throws ApiRequestException{
        String token = accessToken.token;
        String apiVersion = VkApi.DEFAULT_API_VERSION;
        Call<ProfileResponseBody> call = api.getProfile(token, apiVersion);
        ProfileResponseBody responseBody = executeCall(call);
        return responseBody != null ? responseBody.body() : null;
    }

    public static final class Builder extends ApiManager.Builder<VkApiManager>{

        public Builder(AccessToken accessToken) {
            super(accessToken);
        }

        @Override
        public ApiManager.Builder<VkApiManager> enableLogging() {
            return super.enableLogging();
        }

        @Override
        public ApiManager.Builder<VkApiManager> readTimeout(long timeout, TimeUnit timeUnit) {
            return super.readTimeout(timeout, timeUnit);
        }

        @Override
        public VkApiManager build() {
            return new VkApiManager(this);
        }
    }
}