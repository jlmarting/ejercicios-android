package com.jlmarting.intents.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ConectionReceiver extends BroadcastReceiver {
    private static final String RECEIVER = "RECIEVER";

    public ConectionReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(RECEIVER, "Connection Reciever onRecieve..");
        if(intent.getAction() == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            Log.d(RECEIVER, "ACTION " + intent.getAction());
        }
    }
}
