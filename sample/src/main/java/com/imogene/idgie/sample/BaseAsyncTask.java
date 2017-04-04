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
package com.imogene.idgie.sample;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import java.lang.ref.WeakReference;

/**
 * Created by vadim on 4/2/17.
 */

abstract class BaseAsyncTask<Param, Progress, Result>
        extends AsyncTask<Param, Progress, Result> {

    private WeakReference<Callbacks> callbacksRef;
    private int taskId;
    private String errorMessage;

    BaseAsyncTask(Callbacks callbacks, int taskId){
        if(callbacks != null){
            callbacksRef = new WeakReference<>(callbacks);
        }
        this.taskId = taskId;
    }

    @Override
    protected final void onPreExecute() {
        Callbacks callbacks = getCallbacks();
        if(callbacks != null){
            callbacks.onTaskStarted(taskId);
        }
    }

    @Override
    protected final void onPostExecute(Result result) {
        Callbacks callbacks = getCallbacks();
        if(callbacks != null){
            callbacks.onTaskFinished(taskId, result);
        }
    }

    final void reportError(String message){
        cancel(false);
        this.errorMessage = message;
    }

    final void reportError(@StringRes int messageRes){
        Context context = getContext();
        String message = context.getString(messageRes);
        reportError(message);
    }

    final Context getContext(){
        return IdgieDemoApplication.getAppContext();
    }

    @Override
    protected final void onCancelled(Result result) {
        if(!TextUtils.isEmpty(errorMessage)){
            Callbacks callbacks = getCallbacks();
            if(callbacks != null){
                callbacks.onTaskError(taskId, errorMessage);
            }
        }
    }

    private Callbacks getCallbacks(){
        if(callbacksRef != null){
            return callbacksRef.get();
        }
        return null;
    }

    interface Callbacks{

        void onTaskStarted(int taskId);

        void onTaskFinished(int taskId, Object results);

        void onTaskError(int taskId, String errorMessage);
    }
}
