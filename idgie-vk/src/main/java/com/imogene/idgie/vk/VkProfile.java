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