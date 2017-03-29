package com.appcraft.idgie;

import java.io.IOException;

/**
 * Created by Admin on 29.03.2017.
 */

public class ApiRequestException extends IOException {

    private final int mErrorCode;

    ApiRequestException(Throwable cause) {
        super(cause);
        if(!(cause instanceof IOException)){
            throw new IllegalArgumentException("The cause must be an IOException");
        }
        mErrorCode = 0;
    }

    ApiRequestException(int errorCode){
        mErrorCode = errorCode;
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    public int getErrorCode(){
        return mErrorCode;
    }
}
