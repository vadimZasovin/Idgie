package com.appcraft.idgie;

/**
 * Created by Admin on 07.12.2016.
 */

public interface AuthorizationResult {

    AccessToken getAccessToken();

    boolean isAuthorized();
}
