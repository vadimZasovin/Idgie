package com.appcraft.idgie;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Admin on 29.03.2017.
 */

public abstract class ApiManager {

    protected final AccessToken mAccessToken;
    protected final boolean mEnableLogging;
    protected final long mReadTimeout;
    protected final TimeUnit mReadTimeoutTimeUnit;

    protected ApiManager(Builder builder){
        mAccessToken = builder.mAccessToken;
        mEnableLogging = builder.mEnableLogging;
        mReadTimeout = builder.mReadTimeout;
        mReadTimeoutTimeUnit = builder.mReadTimeoutTimeUnit;
    }

    public static abstract class Builder<T extends ApiManager>{

        private AccessToken mAccessToken;
        private boolean mEnableLogging;
        private long mReadTimeout;
        private TimeUnit mReadTimeoutTimeUnit;

        public Builder(){
            // defaults
            mReadTimeout = 15;
            mReadTimeoutTimeUnit = TimeUnit.SECONDS;
        }

        public Builder<T> accessToken(AccessToken accessToken){
            mAccessToken = accessToken;
            return this;
        }

        public Builder<T> enableLogging(boolean enableLogging){
            mEnableLogging = enableLogging;
            return this;
        }

        public Builder<T> readTimeout(long timeout, TimeUnit timeUnit){
            mReadTimeout = timeout;
            mReadTimeoutTimeUnit = timeUnit;
            return this;
        }

        public abstract T build();
    }

    protected <T> T executeCall(Call<T> call) throws ApiRequestException{
        Response<T> response;
        try {
            response = call.execute();
        }catch (IOException e){
            throw new ApiRequestException(e);
        }
        if(response.isSuccessful()){
            return response.body();
        }else {
            int errorCode = response.code();
            throw new ApiRequestException(errorCode);
        }
    }
}