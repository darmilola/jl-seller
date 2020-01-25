package com.dammy.android.emarketvendor.Models;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import com.dammy.android.emarketvendor.openingPageActivity;
import com.dammy.android.emarketvendor.splashscreen;

public class MyApplication extends MultiDexApplication {

    Context context;
    @Override
    public void onCreate() {

        super.onCreate();
        context = this;
        SafetyMethod();
    }

    private void SafetyMethod() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Intent intent = new Intent(getApplicationContext(),splashscreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
