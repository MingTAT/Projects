package com.ttu.ttu.board;

/**
 * Created by wind1209 on 2016/12/7.
 */

import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;

public class ReceiveBootCompleted extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //we double check here for only boot complete event
        if(intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED))
        {
            //here we start the service
            Intent serviceIntent = new Intent(context, NotificationService.class);
            context.startService(serviceIntent);
        }
    }
}