package com.imogene.idgie.sample;

import com.imogene.idgie.AccessToken;
import com.imogene.idgie.ApiRequestException;
import com.imogene.idgie.facebook.FacebookApiManager;
import com.imogene.idgie.facebook.FacebookIdentityProvider;
import com.imogene.idgie.facebook.FacebookProfile;
import com.imogene.idgie.facebook.FacebookProfileFields;
import com.imogene.idgie.google.GoogleApiManager;
import com.imogene.idgie.google.GoogleEmail;
import com.imogene.idgie.google.GoogleIdentityProvider;
import com.imogene.idgie.google.GoogleProfile;
import com.imogene.idgie.vk.VkApiManager;
import com.imogene.idgie.vk.VkIdentityProvider;
import com.imogene.idgie.vk.VkProfile;
import com.imogene.idgie.yandex.YandexApiManager;
import com.imogene.idgie.yandex.YandexProfile;

import java.net.SocketTimeoutException;

/**
 * Created by vadim on 4/2/17.
 */

class LoadProfileInfoAsyncTask extends BaseAsyncTask<AccessToken, Void, BaseProfile> {

    private final String identityProviderName;
    private String vkEmail;

    LoadProfileInfoAsyncTask(Callbacks callbacks, int taskId, String identityProviderName) {
        super(callbacks, taskId);
        this.identityProviderName = identityProviderName;
    }

    void setVkEmail(String email){
        vkEmail = email;
    }

    @Override
    protected BaseProfile doInBackground(AccessToken... params) {
        try {
            AccessToken accessToken = params[0];
            return loadProfileInfo(accessToken);
        }catch (ApiRequestException e){
            handleApiRequestException(e);
            return null;
        }
    }

    private BaseProfile loadProfileInfo(AccessToken accessToken) throws ApiRequestException{
        switch (identityProviderName){
            case FacebookIdentityProvider.NAME:
                return loadFacebookProfile(accessToken);
            case GoogleIdentityProvider.NAME:
                return loadGoogleProfile(accessToken);
            case VkIdentityProvider.NAME:
                return loadVkProfile(accessToken);
            default:
                return loadYandexProfile(accessToken);
        }
    }

    private BaseProfile loadFacebookProfile(AccessToken accessToken) throws ApiRequestException{
        String[] fields = new String[]{
                FacebookProfileFields.NAME,
                FacebookProfileFields.EMAIL};
        FacebookProfile facebookProfile = new FacebookApiManager.Builder(accessToken)
                .enableLogging()
                .build().getProfile(fields);
        String name = facebookProfile.getName();
        String email = facebookProfile.getEmail();
        return new BaseProfile(name, email);
    }

    private BaseProfile loadGoogleProfile(AccessToken accessToken) throws ApiRequestException{
        GoogleProfile googleProfile = new GoogleApiManager.Builder(accessToken)
                .enableLogging()
                .build().getProfile();
        String name = googleProfile.getDisplayName();
        GoogleEmail[] emails = googleProfile.getEmails();
        String email = getGoogleEmail(emails);
        return new BaseProfile(name, email);
    }

    private String getGoogleEmail(GoogleEmail[] emails){
        if(emails != null && emails.length > 0){
            for (GoogleEmail email : emails){
                if(email.isAccountEmail()){
                    return email.getValue();
                }
            }
            return emails[0].getValue();
        }
        return null;
    }

    private BaseProfile loadVkProfile(AccessToken accessToken) throws ApiRequestException{
        VkProfile vkProfile = new VkApiManager.Builder(accessToken)
                .enableLogging()
                .build().getProfile();
        String firstName = vkProfile.getFirstName();
        String lastName = vkProfile.getLastName();
        String name = firstName + " " + lastName;
        return new BaseProfile(name, vkEmail);
    }

    private BaseProfile loadYandexProfile(AccessToken accessToken) throws ApiRequestException{
        YandexProfile yandexProfile = new YandexApiManager.Builder(accessToken)
                .enableLogging()
                .build().getProfile();
        String name = yandexProfile.getDisplayName();
        String email = yandexProfile.getDefaultEmail();
        return new BaseProfile(name, email);
    }

    private void handleApiRequestException(ApiRequestException e){
        Throwable cause = e.getCause();
        if(cause != null){
            if(cause instanceof SocketTimeoutException){
                reportError(R.string.server_not_responses);
            }else {
                reportError(R.string.connection_error);
            }
        }else {
            reportError(R.string.unsuccessful_request);
        }
    }
}
