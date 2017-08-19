package com.inspiration.makrandpawar.quotesdukan;

import android.app.Application;

import io.realm.Realm;

public class QuotesDukan extends Application {
    public static boolean isConnectionAvailable = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
