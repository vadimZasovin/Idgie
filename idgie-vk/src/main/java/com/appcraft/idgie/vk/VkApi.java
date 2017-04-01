package com.appcraft.idgie.vk;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Admin on 01.12.2016.
 */

public interface VkApi {

    String DEFAULT_API_VERSION = "5.60";
    String BASE_URL = "https://api.vk.com/method/";

    @GET("account.getProfileInfo")
    Call<VkProfileResponseBody> getProfile(@Query("access_token") String accessToken,
                                           @Query("v") String apiVersion);
}