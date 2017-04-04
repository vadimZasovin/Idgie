# Idgie
## Introduction
Idgie - is an android OAuth client library. It consists of several modules for different identity providers (social networks) such as Facebook, 
Google and so on.
The complete list of supported identity providers looks like this now:
1. [Facebook](http://github.com)
2. [Google](http://github.com)
3. [Vkontakte](http://github.com)
4. [Yandex](http://github.com)

This list will be expanded in the future.

With Idgie you will be able to get an access token of some identity provider (social network) and use this token to perform some actions 
with user's profile in this social network, for example load profile info, get friend list etc. The main advantage of Idgie is that it
does not use native sdks for each identity provider. Instead, it uses the great [Retrofit library](http://github.com) to make requests to
identity provider's underlying API. Using this approach we achive a smaller size of the libarary and a single, understandable API.

## Basic usage
In this example we demostrate how to obtain an access token in Facebook and use this access token to load user profile. Let's assume that
you already have an registered app on the Facebook's page for developers.

```java
// creating IdentityProvider object
String clientId = getClientId();
String redirectUri = getRedirectUri();
IdentityProvider identityProvider = FacebookIdentityProvider.startBuilding()
        .apiVersion(FacebookApi.DEFAULT_API_VERSION)
        .permissions(FacebookPermissions.EMAIL, FacebookPermissions.PUBLIC_PROFILE)
        .clientId(clientId)
        .redirectUri(redirectUri)
        .build();
        
// initiating authorization flow
identityProvider.initiateAuthorizationFlow(this);
```

```initiateAuthorizationFlow(Context context)``` method and it's overloaded versions directs the user to a browser like activity - 
either by issuing an ```CustomTabsIntent``` or opening the custom ```Activity``` with ```WebView``` inside. The content of the opened page
depends on the ```IdentityProvider``` used. But in general it asks the user to confirm the requested permissions if the user is already 
logged in, or requests to log in otherwise.

After successful authentication and authorization the intent with ```AuthorizationResult``` object inside will be delivered to the app
through ```onNewIntent(Intent intent)``` callback method:

```java
@Override
protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    AuthorizationResult result = identityProvider.extractAuthorizationResultFromIntent(intent);
    if(result != null && result.isAuthorized()){
        loadProfileInfo(result);
    }
}
```

Authorization succeeded. Now we can load user profile info:

```java
private FacebookProfile loadProfileInfo(AuthorizationResult result){
    String[] fields = new String[]{FacebookProfileFields.NAME, FacebookProfileFields.EMAIL};
    AccesToken accessToken = result.getAccessToken();
    FacebookApiManager apiManager = new FacebookApiManager.Builder(accessToken).build();
    return apiManager.getProfile(fields);
}
```

```apiManager.getProfile(fields)``` method must be called on the worker thread since this method performs the actual request to 
Facebook API.

Search Wiki for detailed guide for other identity providers.

## When to use Idgie?
1. When you need to integrate multiple social networks in your app. If you have only one social network in your app, the native sdk
of this social network is the preferred way.
2. When you want to have a single, understandable API.

## Integration
1. Edit your project level build.gradle file. Add a repository if it is not added yet.
```
allprojects {
    repositories {
        jcenter()
    }
}
```
2. Edit your app level build.gradle file. Add dependencies for the modules you want to use in your app. Possible artifacts include:
* ```compile 'com.appcraftlab:idgie-facebook:{version}'```
* ```compile 'com.appcraftlab:idgie-google:{version}'```
* ```compile 'com.appcraftlab:idgie-vk:{version}'```
* ```compile 'com.appcraftlab:idgie-yandex:{version}'```

The latest version of the library is **1.0.6**. Each module has the same version.

## Contributing
One of the distinguishing features of this library is that it can expand almost infinitely. By implementing new identity providers and by adding new features to api managers. So, welcome to contribute!

## License
```Copyright 2017 Vadim Zasovin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.```
