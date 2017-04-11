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
package com.imogene.idgie;

import android.text.TextUtils;

/**
 * Created by Admin on 02.12.2016.
 */

public final class ArgumentValidator {

    public static void throwIfNull(Object o, String argumentName){
        if(o == null) {
            throwException(argumentName + " must not be null");
        }
    }

    public static void throwIfEmpty(String str, String argumentName){
        if(TextUtils.isEmpty(str)){
            throwException(argumentName + " must not be empty");
        }
    }

    public static void throwException(String message){
        throw new IllegalArgumentException(message);
    }
}
