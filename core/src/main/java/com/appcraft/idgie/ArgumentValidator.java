package com.appcraft.idgie;

import android.text.TextUtils;

/**
 * Created by Admin on 02.12.2016.
 */

final class ArgumentValidator {

    static void throwIfNull(Object o, String argumentName){
        if(o == null) {
            throwException(argumentName + " must not be null");
        }
    }

    static void throwIfEmpty(String str, String argumentName){
        if(TextUtils.isEmpty(str)){
            throwException(argumentName + " must not be null");
        }
    }

    static void throwException(String message){
        throw new IllegalArgumentException(message);
    }
}
