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
package com.imogene.idgie.vk;

import com.imogene.idgie.AccessToken;
import com.imogene.idgie.AuthorizationResult;

/**
 * Created by Admin on 07.12.2016.
 */

public class VkAuthorizationResult implements AuthorizationResult {

    private final AuthorizationResult authorizationResult;

    String email;
    String userId;

    VkAuthorizationResult(AuthorizationResult authorizationResult){
        this.authorizationResult = authorizationResult;
    }

    @Override
    public AccessToken getAccessToken() {
        return authorizationResult.getAccessToken();
    }

    @Override
    public boolean isAuthorized() {
        return authorizationResult.isAuthorized();
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }
}