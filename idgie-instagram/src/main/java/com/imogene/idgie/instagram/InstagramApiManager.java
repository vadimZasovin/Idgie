package com.imogene.idgie.instagram;

import com.imogene.idgie.AccessToken;
import com.imogene.idgie.ApiCore;
import com.imogene.idgie.ApiManager;
import com.imogene.idgie.ApiRequestException;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;

/**
 * Created by Admin on 10.04.2017.
 */

public class InstagramApiManager extends ApiManager {

    private static volatile InstagramApiManager instance;
    private final InstagramApi api;

    private InstagramApiManager(Builder builder) {
        super(builder);
        ApiCore.Builder<InstagramApi> apiBuilder = new ApiCore.Builder<>();
        apiBuilder.apiClass(InstagramApi.class)
                .accessToken(accessToken)
                .baseUrl(InstagramApi.BASE_URL)
                .readTimeout(readTimeout, readTimeoutTimeUnit);
        if(loggingEnabled){
            apiBuilder.enableLogging();
        }
        ApiCore<InstagramApi> apiCore = apiBuilder.build();
        api = apiCore.api();
    }

    public static void setInstance(InstagramApiManager instance){
        InstagramApiManager.instance = instance;
    }

    public static InstagramApiManager getInstance(){
        return instance;
    }

    public InstagramProfile getProfile() throws ApiRequestException{
        String token = accessToken.token;
        String apiVersion = InstagramApi.DEFAULT_API_VERSION;
        Call<ProfileResponseBody> call = api.getProfile(apiVersion, token);
        ProfileResponseBody responseBody = executeCall(call);
        return responseBody != null ? responseBody.body() : null;
    }

    public static final class Builder extends ApiManager.Builder<InstagramApiManager>{

        public Builder(AccessToken accessToken) {
            super(accessToken);
        }

        @Override
        public ApiManager.Builder<InstagramApiManager> enableLogging() {
            return super.enableLogging();
        }

        @Override
        public ApiManager.Builder<InstagramApiManager> readTimeout(long timeout, TimeUnit timeUnit) {
            return super.readTimeout(timeout, timeUnit);
        }

        @Override
        public InstagramApiManager build() {
            return new InstagramApiManager(this);
        }
    }
}