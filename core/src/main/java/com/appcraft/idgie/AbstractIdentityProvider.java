package com.appcraft.idgie;

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

abstract class AbstractIdentityProvider implements IdentityProvider {

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
        String authorizationUrl = getAuthorizationUrl();
        intent.launchUrl(context, Uri.parse(authorizationUrl));
    }

    @Override
    public void initiateAuthorizationFlow(@NonNull Context context) {
        CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
        initiateAuthorizationFlow(context, intent);
    }

    @Override
    public boolean usesCustomRedirectUriScheme() {
        String redirectUri = getRedirectUri();
        return usesCustomRedirectUriScheme(redirectUri);
    }

    private boolean usesCustomRedirectUriScheme(String redirectUri){
        Uri uri = Uri.parse(redirectUri);
        String scheme = uri.getScheme();
        return !(scheme.equals("http") || scheme.equals("https"));
    }

    @NonNull
    abstract String getAuthorizationUrl();

    @NonNull
    abstract String getRedirectUri();

    private Intent createAuthorizationWebActivityIntent(Context context){
        Intent intent = new Intent(context, WebActivity.class);
        String authorizationUrl = getAuthorizationUrl();
        String redirectUri = getRedirectUri();
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
                String redirectUri = uri.toString();
                String sourceRedirectUri = getRedirectUri();
                if(redirectUri.startsWith(sourceRedirectUri)){
                    RedirectUriParser redirectUriParser = getRedirectUriParser();
                    return redirectUriParser.parse(redirectUri);
                }
            }
        }
        return null;
    }

    @NonNull
    abstract RedirectUriParser getRedirectUriParser();

    static abstract class AbstractBuilder<T extends IdentityProvider> implements Builder<T>{

        private final StringBuilder mUrlBuilder;
        private boolean mFirstUrlParameter = true;

        AbstractBuilder(){
            mUrlBuilder = new StringBuilder();
        }

        final void appendUrlParameter(String name, String value){
            appendUrlParameterPrefix();
            appendUrlParameterInternal(name, value);
        }

        private void appendUrlParameterPrefix(){
            if(mFirstUrlParameter){
                mUrlBuilder.append("?");
                mFirstUrlParameter = false;
            }else {
                mUrlBuilder.append("&");
            }
        }

        private void appendUrlParameterInternal(String name, String value){
            mUrlBuilder.append(name).append("=").append(value);
        }

        final void appendValue(CharSequence value){
            mUrlBuilder.append(value);
        }

        final String getUrl(){
            return mUrlBuilder.toString();
        }
    }
}