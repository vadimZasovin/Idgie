package com.appcraft.idgie;

import java.io.IOException;

/**
 * Created by Admin on 29.03.2017.
 */

public class ApiRequestException extends IOException {

    private final int errorCode;

    public ApiRequestException(Throwable cause) {
        super(cause);
        if(!(cause instanceof IOException)){
            throw new IllegalArgumentException("The cause must be an IOException");
        }
        errorCode = 0;
    }

    public ApiRequestException(int errorCode){
        this.errorCode = errorCode;
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    public int getErrorCode(){
        return errorCode;
    }
}
