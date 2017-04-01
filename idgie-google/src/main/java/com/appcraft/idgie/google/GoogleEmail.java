package com.appcraft.idgie.google;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 05.12.2016.
 */

public class GoogleEmail {

    public static final String TYPE_ACCOUNT = "account";

    @SerializedName("type")
    private String type;

    @SerializedName("value")
    private String value;

    private GoogleEmail(){}

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public boolean isAccountEmail(){
        return TYPE_ACCOUNT.equals(type);
    }
}
