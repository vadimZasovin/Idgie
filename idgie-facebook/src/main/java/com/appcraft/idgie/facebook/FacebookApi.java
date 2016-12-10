package com.appcraft.idgie.facebook;

import com.appcraft.idgie.facebook.model.FacebookProfile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Admin on 01.12.2016.
 */

public interface FacebookApi {

    String DEFAULT_API_VERSION = "v2.8";
    String BASE_URL = "https://graph.facebook.com/" + DEFAULT_API_VERSION + "/";

    @GET("/me")
    Call<FacebookProfile> getProfile(@Query("access_token") String token,
                                     @Query("fields") String profileFields);
}
