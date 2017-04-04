package com.imogene.idgie.facebook;


import android.text.TextUtils;

/**
 * Created by Admin on 02.12.2016.
 */

public final class FacebookProfileFields {

    public static final String NAME = "name";
    public static final String EMAIL = "email";

    static String createSingleUrlParameter(String... fields){
        if(fields == null || fields.length == 0){
            throw new IllegalArgumentException("Fields must not be empty");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(String field : fields){
            if(!TextUtils.isEmpty(field)){
                if(stringBuilder.length() > 0){
                    stringBuilder.append(',');
                }
                stringBuilder.append(field);
            }
        }
        return stringBuilder.toString();
    }
}
