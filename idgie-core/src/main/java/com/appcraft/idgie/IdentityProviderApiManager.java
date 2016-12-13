package com.appcraft.idgie;


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

public class IdentityProviderApiManager<T> {

    private static final long READ_TIMEOUT = 5;
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final T mApi;

    private IdentityProviderApiManager(final Builder<T> builder){
        HttpLoggingInterceptor loggingInterceptor = null;
        if(builder.mLoggingEnabled){
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
                AccessToken accessToken = builder.mAccessToken;
                String header = accessToken.type + " " + accessToken.token;
                Request intercepted = original.newBuilder()
                        .addHeader(AUTHORIZATION_HEADER, header)
                        .build();
                return chain.proceed(intercepted);
            }
        });
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.MINUTES);

        OkHttpClient client = clientBuilder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(builder.mBaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        mApi = retrofit.create(builder.mClass);
    }

    public T api(){
        return mApi;
    }

    public static final class Builder<T>{

        private String mBaseUrl;
        private Class<T> mClass;
        private AccessToken mAccessToken;
        private boolean mLoggingEnabled;

        public Builder<T> baseUrl(String baseUrl){
            ArgumentValidator.throwIfEmpty(baseUrl, "Base url");
            mBaseUrl = baseUrl;
            return this;
        }

        public Builder<T> apiClass(Class<T> clazz){
            ArgumentValidator.throwIfNull(clazz, "Api class");
            mClass = clazz;
            return this;
        }

        public Builder<T> accessToken(AccessToken accessToken){
            ArgumentValidator.throwIfNull(accessToken, "Access token");
            mAccessToken = accessToken;
            return this;
        }

        public Builder<T> enableLogging(){
            mLoggingEnabled = true;
            return this;
        }

        public IdentityProviderApiManager<T> build(){
            return new IdentityProviderApiManager<>(this);
        }
    }
}