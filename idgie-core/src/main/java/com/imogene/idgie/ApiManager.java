package com.imogene.idgie;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Admin on 29.03.2017.
 */

public abstract class ApiManager {

    protected final AccessToken accessToken;
    protected final boolean loggingEnabled;
    protected final long readTimeout;
    protected final TimeUnit readTimeoutTimeUnit;

    protected ApiManager(Builder builder){
        accessToken = builder.accessToken;
        loggingEnabled = builder.loggingEnabled;
        readTimeout = builder.readTimeout;
        readTimeoutTimeUnit = builder.readTimeoutTimeUnit;
    }

    public static abstract class Builder<T extends ApiManager>{

        private final AccessToken accessToken;
        private boolean loggingEnabled;
        private long readTimeout;
        private TimeUnit readTimeoutTimeUnit;

        public Builder(AccessToken accessToken){
            ArgumentValidator.throwIfNull(accessToken, "Access token");
            this.accessToken = accessToken;
            // defaults
            readTimeout = 15;
            readTimeoutTimeUnit = TimeUnit.SECONDS;
        }

        public Builder<T> enableLogging(){
            loggingEnabled = true;
            return this;
        }

        public Builder<T> readTimeout(long timeout, TimeUnit timeUnit){
            readTimeout = timeout;
            readTimeoutTimeUnit = timeUnit;
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