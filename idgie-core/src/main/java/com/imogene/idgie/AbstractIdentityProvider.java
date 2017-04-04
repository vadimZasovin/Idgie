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
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;

/**
 * Created by Admin on 07.12.2016.
 */

public abstract class AbstractIdentityProvider implements IdentityProvider {

    protected final String authorizationUrl;
    protected final String redirectUri;

    protected AbstractIdentityProvider(String authorizationUrl, String redirectUri){
        this.authorizationUrl = authorizationUrl;
        this.redirectUri = redirectUri;
    }

    @Override
    public void initiateAuthorizationFlow(@NonNull Activity activity, int requestCode) {
        Intent intent = createAuthorizationWebActivityIntent(activity);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void initiateAuthorizationFlow(@NonNull Fragment fragment, int requestCode) {
        Context context = fragment.getActivity();
        Intent intent = createAuthorizationWebActivityIntent(context);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    public void initiateAuthorizationFlow(@NonNull android.app.Fragment fragment, int requestCode) {
        Context context = fragment.getActivity();
        Intent intent = createAuthorizationWebActivityIntent(context);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    public void initiateAuthorizationFlow(@NonNull Context context, @NonNull CustomTabsIntent intent) {
        intent.launchUrl(context, Uri.parse(authorizationUrl));
    }

    @Override
    public void initiateAuthorizationFlow(@NonNull Context context) {
        CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
        initiateAuthorizationFlow(context, intent);
    }

    @Override
    public boolean usesCustomRedirectUriScheme() {
        return usesCustomRedirectUriScheme(redirectUri);
    }

    private boolean usesCustomRedirectUriScheme(String redirectUri){
        Uri uri = Uri.parse(redirectUri);
        String scheme = uri.getScheme();
        return !(scheme.equals("http") || scheme.equals("https"));
    }

    private Intent createAuthorizationWebActivityIntent(Context context){
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(WebActivity.EXTRA_URL, authorizationUrl);
        intent.putExtra(WebActivity.EXTRA_REDIRECT_URI, redirectUri);
        return intent;
    }

    @Nullable
    @Override
    public AuthorizationResult extractAuthorizationResultFromIntent(Intent intent) {
        if(intent != null){
            Uri uri = intent.getData();
            if(uri != null){
                String filledRedirectUri = uri.toString();
                if(filledRedirectUri.startsWith(redirectUri)){
                    RedirectUriParser redirectUriParser = getRedirectUriParser();
                    return redirectUriParser.parse(filledRedirectUri);
                }
            }
        }
        return null;
    }

    @NonNull
    protected abstract RedirectUriParser getRedirectUriParser();

    protected static abstract class AbstractBuilder<T extends IdentityProvider> implements Builder<T> {

        private final StringBuilder urlBuilder;
        private boolean firstUrlParameter = true;

        protected AbstractBuilder(){
            urlBuilder = new StringBuilder();
        }

        protected final void appendUrlParameter(String name, String value){
            appendUrlParameterPrefix();
            appendUrlParameterInternal(name, value);
        }

        private void appendUrlParameterPrefix(){
            if(firstUrlParameter){
                urlBuilder.append("?");
                firstUrlParameter = false;
            }else {
                urlBuilder.append("&");
            }
        }

        private void appendUrlParameterInternal(String name, String value){
            urlBuilder.append(name).append("=").append(value);
        }

        protected final void appendValue(String value){
            urlBuilder.append(value);
        }

        protected final String getUrl(){
            return urlBuilder.toString();
        }
    }
}