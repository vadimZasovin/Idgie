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

    interface ClientIdSetter<F extends Finish<IP>, IP extends IdentityProvider>{

        RedirectUriSetter<F, IP> clientId(@NonNull String clientId);
    }

    interface RedirectUriSetter<F extends Finish<IP>, IP extends IdentityProvider>{

        F redirectUri(@NonNull String redirectUri);
    }

    interface Finish<T extends IdentityProvider> {

        T build();
    }

    interface PermissionsSetter<F extends Finish<IP>, IP extends IdentityProvider>{

        F permissions(@Nullable String... permissions);
    }

    interface ApiVersionSetter<F extends Finish<IP>, IP extends IdentityProvider>{

        F apiVersion(@Nullable String apiVersion);
    }

    interface Builder<F extends Finish<IP>, IP extends IdentityProvider>
            extends ClientIdSetter<F, IP>, RedirectUriSetter<F, IP>{}
}