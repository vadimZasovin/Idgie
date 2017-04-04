package com.imogene.idgie.vk;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 05.12.2016.
 */

public class VkItem {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    private VkItem(){}

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}