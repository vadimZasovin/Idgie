package com.appcraftlab.idgie.sample;

import android.app.Application;
import android.content.Context;

/**
 * Created by vadim on 4/2/17.
 */

public class IdgieDemoApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    static Context getAppContext(){
        return appContext;
    }
}
