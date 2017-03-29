package com.appcraft.idgie.facebook;

import com.appcraft.idgie.ApiCore;
import com.appcraft.idgie.ApiManager;
import com.appcraft.idgie.ApiRequestException;

import retrofit2.Call;

/**
 * Created by Admin on 29.03.2017.
 */

public class FacebookApiManager extends ApiManager{

    private static volatile FacebookApiManager sInstance;
    private final FacebookApi mApi;

    private FacebookApiManager(Builder builder) {
        super(builder);
        ApiCore.Builder<FacebookApi> apiBuilder = new ApiCore.Builder<>();
        apiBuilder.apiClass(FacebookApi.class)
                .accessToken(mAccessToken)
                .baseUrl(FacebookApi.BASE_URL)
                .readTimeout(mReadTimeout, mReadTimeoutTimeUnit);
        if(mEnableLogging){
            apiBuilder.enableLogging();
        }
        ApiCore<FacebookApi> apiCore = apiBuilder.build();
        mApi = apiCore.api();
    }

    public static void setInstance(FacebookApiManager instance){
        sInstance = instance;
    }

    public static FacebookApiManager getInstance(){
        return sInstance;
    }

    public FacebookProfile getProfile(String... fields) throws ApiRequestException{
        String token = mAccessToken.token;
        String fieldsParam = FacebookProfileFields.createSingleUrlParameter(fields);
        Call<FacebookProfile> call = mApi.getProfile(token, fieldsParam);
        return executeCall(call);
    }

    public static final class Builder extends ApiManager.Builder<FacebookApiManager>{

        @Override
        public FacebookApiManager build() {
            return new FacebookApiManager(this);
        }
    }
}