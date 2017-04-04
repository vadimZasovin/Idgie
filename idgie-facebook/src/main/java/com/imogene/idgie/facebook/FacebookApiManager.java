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