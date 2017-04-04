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
package com.imogene.idgie.yandex;

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