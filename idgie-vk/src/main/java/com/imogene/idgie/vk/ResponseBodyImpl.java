package com.imogene.idgie.vk;

import com.google.gson.annotations.SerializedName;
import com.imogene.idgie.ResponseBody;

/**
 * Created by Admin on 11.04.2017.
 */

class ResponseBodyImpl<T> implements ResponseBody<T> {

    @SerializedName("response")
    private T body;

    @Override
    public T body() {
        return body;
    }
}