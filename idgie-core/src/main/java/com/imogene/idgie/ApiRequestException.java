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
