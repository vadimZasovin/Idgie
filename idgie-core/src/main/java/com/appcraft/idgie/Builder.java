package com.appcraft.idgie;

import com.appcraft.idgie.IdentityProvider;

/**
 * Created by Admin on 07.12.2016.
 */

public interface Builder<T extends IdentityProvider>
        extends IdentityProvider.ClientIdSetter,
        IdentityProvider.RedirectUriSetter<T>, IdentityProvider.Finish<T> {}
