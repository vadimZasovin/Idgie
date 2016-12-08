package com.appcraft.idgie;

/**
 * Created by Admin on 07.12.2016.
 */

interface Builder<T extends IdentityProvider>
        extends IdentityProvider.ClientIdSetter,
        IdentityProvider.RedirectUriSetter<T>, IdentityProvider.Finish<T> {}
