package com.appcraft.idgie.yandex;

import com.appcraft.idgie.yandex.model.YandexProfile;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Admin on 01.12.2016.
 */

public interface YandexApi {

    String BASE_URL = "https://login.yandex.ru/";

    @GET("/info")
    Call<YandexProfile> getProfile();
}