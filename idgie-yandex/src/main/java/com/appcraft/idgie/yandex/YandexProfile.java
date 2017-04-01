package com.appcraft.idgie.yandex;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 02.12.2016.
 */

public class YandexProfile {

    public static final String SEX_MALE = "male";
    public static final String SEX_FEMALE = "female";

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("emails")
    private String[] emails;

    @SerializedName("default_email")
    private String defaultEmail;

    @SerializedName("real_name")
    private String realName;

    @SerializedName("login")
    private String login;

    @SerializedName("sex")
    private String sex;

    @SerializedName("id")
    private String id;

    private YandexProfile(){}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String[] getEmails() {
        return emails;
    }

    public String getDefaultEmail() {
        return defaultEmail;
    }

    public String getRealName() {
        return realName;
    }

    public String getLogin() {
        return login;
    }

    public String getSex() {
        return sex;
    }

    public String getId() {
        return id;
    }
}