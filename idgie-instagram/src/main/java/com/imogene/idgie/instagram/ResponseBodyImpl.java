package com.imogene.idgie.instagram;

import com.google.gson.annotations.SerializedName;
import com.imogene.idgie.ResponseBody;

/**
 * Created by Admin on 10.04.2017.
 */

class ResponseBodyImpl<T> implements ResponseBody<T> {

    @SerializedName("data")
    private T body;

    @Override
    public T body() {
        return body;
    }
}