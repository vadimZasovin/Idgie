package com.appcraft.idgie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 05.12.2016.
 */

public class GoogleProfile {

    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";

    @SerializedName("gender")
    private String gender;

    @SerializedName("emails")
    private GoogleEmail[] emails;

    @SerializedName("id")
    private String id;

    @SerializedName("displayName")
    private String displayName;

    public String getGender() {
        return gender;
    }

    public GoogleEmail[] getEmails() {
        return emails;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }
}