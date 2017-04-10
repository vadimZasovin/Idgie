package com.imogene.idgie.instagram;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 10.04.2017.
 */

class ProfileCharacteristics {

    @SerializedName("media")
    int mediaCount;

    @SerializedName("follows")
    int followsCount;

    @SerializedName("followed_by")
    int followersCount;
}