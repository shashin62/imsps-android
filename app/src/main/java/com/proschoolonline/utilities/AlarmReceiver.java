package com.proschoolonline.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.androidannotations.annotations.EReceiver;

@EReceiver
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("Inside","Hello Receiver");
        Intent background = new Intent(context, BackgroundService_.class);
        context.startService(background);
    }

}
