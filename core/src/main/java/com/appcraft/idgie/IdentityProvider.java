package com.appcraft.idgie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;

/**
 * Created by Admin on 01.12.2016.
 */

public interface IdentityProvider {

    @NonNull
    String getName();

    void initiateAuthorizationFlow(@NonNull Activity activity, int requestCode);

    void initiateAuthorizationFlow(@NonNull Fragment fragment, int requestCode);

    void initiateAuthorizationFlow(@NonNull android.app.Fragment fragment, int requestCode);

    void initiateAuthorizationFlow(@NonNull Context context, @NonNull CustomTabsIntent intent);

    void initiateAuthorizationFlow(@NonNull Context context);

    boolean usesCustomRedirectUriScheme();

    @Nullable
    AuthorizationResult extractAuthorizationResultFromIntent(Intent intent);

    interface PermissionsSetter{

        ClientIdSetter permissions(@Nullable String... permissions);
    }

    interface ClientIdSetter{

        RedirectUriSetter clientId(@NonNull String clientId);
    }

    interface RedirectUriSetter<T extends IdentityProvider>{

        Finish<T> redirectUri(@NonNull String redirectUri);
    }

    interface Finish<T extends IdentityProvider> {

        T build();
    }
}
