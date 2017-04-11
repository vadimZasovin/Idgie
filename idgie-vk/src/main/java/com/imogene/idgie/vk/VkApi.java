/*Copyright 2017 Vadim Zasovin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
package com.imogene.idgie.vk;

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
    Call<ProfileResponseBody> getProfile(@Query("access_token") String accessToken,
                                         @Query("v") String apiVersion);
}