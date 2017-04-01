package com.appcraft.idgie.google;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Admin on 05.12.2016.
 */

public interface GoogleApi {

    String DEFAULT_API_VERSION = "v1";
    String BASE_URL = "https://www.googleapis.com/";

    @GET("/plus/{v}/people/me")
    Call<GoogleProfile> getProfile(@Path("v") String apiVersion);
}