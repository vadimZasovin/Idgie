package com.appcraft.idgie;

import android.support.annotation.NonNull;

/**
 * Created by Admin on 02.12.2016.
 */

public interface RedirectUriParser {

    @NonNull
    AuthorizationResult parse(String redirectUri);
}
