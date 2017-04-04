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

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 05.12.2016.
 */

public class VkProfile {

    public static final int SEX_MALE = 2;
    public static final int SEX_FEMALE = 1;
    public static final int SEX_UNSPECIFIED = 0;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("sex")
    private int sex;

    @SerializedName("home_town")
    private String homeTown;

    @SerializedName("country")
    private VkItem country;

    @SerializedName("city")
    private VkItem city;

    @SerializedName("status")
    private String status;

    @SerializedName("phone")
    private String phone;

    private VkProfile(){}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getSex() {
        return sex;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public VkItem getCountry() {
        return country;
    }

    public VkItem getCity() {
        return city;
    }

    public String getStatus() {
        return status;
    }

    public String getPhone() {
        return phone;
    }
}