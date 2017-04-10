package com.imogene.idgie.instagram;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Admin on 10.04.2017.
 */

public interface InstagramApi {

    String BASE_URL = "https://api.instagram.com/";
    String DEFAULT_API_VERSION = "v1";

    @GET("/{api_version}/users/self")
    Call<ProfileResponseBody> getProfile(@Path("api_version") String apiVersion,
                                         @Query("access_token") String token);
}
