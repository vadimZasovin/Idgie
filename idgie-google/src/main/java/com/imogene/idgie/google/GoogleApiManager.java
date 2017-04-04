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