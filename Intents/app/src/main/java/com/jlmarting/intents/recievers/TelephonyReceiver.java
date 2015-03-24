package com.jlmarting.intents.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TelephonyReceiver extends BroadcastReceiver {
    private static final String RECEIVER = "RECEIVER";

    public TelephonyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(RECEIVER, "Telephony Reciever onRecieve..");
        if(intent.getAction() == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            Log.d(RECEIVER, "ACTION " + intent.getAction());
        }
    }
}
