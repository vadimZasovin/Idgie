package com.imogene.idgie.instagram;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 10.04.2017.
 */

public class InstagramProfile {

    @SerializedName("id")
    private int id;

    @SerializedName("username")
    private String userName;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("profile_picture")
    private String profilePictureUrl;

    @SerializedName("bio")
    private String biography;

    @SerializedName("website")
    private String website;

    @SerializedName("counts")
    private ProfileCharacteristics characteristics;

    private InstagramProfile(){}

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPictureUrl() {
        return profilePictureUrl;
    }

    public String getBiography() {
        return biography;
    }

    public String getWebsite() {
        return website;
    }

    public int getMediaCount() {
        return characteristics != null ? characteristics.mediaCount : 0;
    }

    public int getFollowsCount() {
        return characteristics != null ? characteristics.followsCount : 0;
    }

    public int getFollowersCount() {
        return characteristics != null ? characteristics.followersCount : 0;
    }
}