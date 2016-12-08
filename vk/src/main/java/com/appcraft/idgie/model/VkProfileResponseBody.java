package com.appcraft.idgie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 05.12.2016.
 */

public class VkProfileResponseBody {

    @SerializedName("response")
    private VkProfile mProfile;

    public VkProfile getProfile() {
        return mProfile;
    }
}
