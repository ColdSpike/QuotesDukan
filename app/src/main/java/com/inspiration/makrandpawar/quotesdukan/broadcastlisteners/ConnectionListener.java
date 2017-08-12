package com.inspiration.makrandpawar.quotesdukan.broadcastlisteners;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.inspiration.makrandpawar.quotesdukan.QuotesDukan;

import java.util.List;

public class ConnectionListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            QuotesDukan.isConnectionAvailable = false;
        } else {
            if (activeNetworkInfo.isConnected())
                QuotesDukan.isConnectionAvailable = true;
            else
                QuotesDukan.isConnectionAvailable = false;
        }
    }

}
