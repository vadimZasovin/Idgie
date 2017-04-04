package com.imogene.idgie;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by Admin on 01.12.2016.
 */

public class ApiCore<T> {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final T api;

    private ApiCore(final Builder<T> builder){
        HttpLoggingInterceptor loggingInterceptor = null;
        if(builder.loggingEnabled){
            loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if(loggingInterceptor != null){
            clientBuilder.addInterceptor(loggingInterceptor);
        }

        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                AccessToken accessToken = builder.accessToken;
                String header = accessToken.type + " " + accessToken.token;
                Request intercepted = original.newBuilder()
                        .addHeader(AUTHORIZATION_HEADER, header)
                        .build();
                return chain.proceed(intercepted);
            }
        });

        long readTimeout = builder.readTimeout;
        TimeUnit readTimeoutTimeUnit = builder.readTimeoutTimeUnit;
        clientBuilder.readTimeout(readTimeout, readTimeoutTimeUnit);

        OkHttpClient client = clientBuilder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(builder.baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        api = retrofit.create(builder.clazz);
    }

    public T api(){
        return api;
    }

    public static final class Builder<T>{

        private String baseUrl;
        private Class<T> clazz;
        private AccessToken accessToken;
        private boolean loggingEnabled;
        private long readTimeout;
        private TimeUnit readTimeoutTimeUnit;

        public Builder(){
            // defaults
            readTimeout = 15;
            readTimeoutTimeUnit = TimeUnit.SECONDS;
        }

        public Builder<T> baseUrl(String baseUrl){
            ArgumentValidator.throwIfEmpty(baseUrl, "Base url");
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder<T> apiClass(Class<T> clazz){
            ArgumentValidator.throwIfNull(clazz, "Api class");
            this.clazz = clazz;
            return this;
        }

        public Builder<T> accessToken(AccessToken accessToken){
            ArgumentValidator.throwIfNull(accessToken, "Access token");
            this.accessToken = accessToken;
            return this;
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

        public ApiCore<T> build(){
            return new ApiCore<>(this);
        }
    }
}