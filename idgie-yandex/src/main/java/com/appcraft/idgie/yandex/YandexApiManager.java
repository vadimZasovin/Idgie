package com.appcraft.idgie.yandex;

import com.appcraft.idgie.AccessToken;
import com.appcraft.idgie.ApiCore;
import com.appcraft.idgie.ApiManager;
import com.appcraft.idgie.ApiRequestException;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;

/**
 * Created by vadim on 4/1/17.
 */

public class YandexApiManager extends ApiManager {

    private static volatile YandexApiManager instance;
    private final YandexApi api;

    private YandexApiManager(Builder builder) {
        super(builder);
        ApiCore.Builder<YandexApi> apiBuilder = new ApiCore.Builder<>();
        apiBuilder.apiClass(YandexApi.class)
                .accessToken(accessToken)
                .baseUrl(YandexApi.BASE_URL)
                .readTimeout(readTimeout, readTimeoutTimeUnit);
        if(loggingEnabled){
            apiBuilder.enableLogging();
        }
        ApiCore<YandexApi> apiCore = apiBuilder.build();
        api = apiCore.api();
    }

    public static void setInstance(YandexApiManager instance){
        YandexApiManager.instance = instance;
    }

    public static YandexApiManager getInstance(){
        return instance;
    }

    public YandexProfile getProfile() throws ApiRequestException{
        Call<YandexProfile> call = api.getProfile();
        return executeCall(call);
    }

    public static final class Builder extends ApiManager.Builder<YandexApiManager>{

        public Builder(AccessToken accessToken) {
            super(accessToken);
        }

        @Override
        public ApiManager.Builder<YandexApiManager> enableLogging() {
            return super.enableLogging();
        }

        @Override
        public ApiManager.Builder<YandexApiManager> readTimeout(long timeout, TimeUnit timeUnit) {
            return super.readTimeout(timeout, timeUnit);
        }

        @Override
        public YandexApiManager build() {
            return new YandexApiManager(this);
        }
    }
}