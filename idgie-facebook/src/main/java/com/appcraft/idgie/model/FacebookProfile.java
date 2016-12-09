package com.appcraft.idgie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 02.12.2016.
 */

public class FacebookProfile {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String email;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
