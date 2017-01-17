package com.example.huanghaojian.weather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by huanghaojian on 16/10/10.
 */

public class AutoUpdateReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent){
        Intent i=new Intent(context,AutoUpdateReceiver.class);
        context.startService(i);
    }
}
