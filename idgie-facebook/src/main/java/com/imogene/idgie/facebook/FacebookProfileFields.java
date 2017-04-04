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
