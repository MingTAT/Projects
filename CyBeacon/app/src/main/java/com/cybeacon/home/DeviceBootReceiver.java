package com.cybeacon.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class DeviceBootReceiver extends BroadcastReceiver {

    public Context con;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            con = context;
            StartAlarm();
        }
    }

    public void StartAlarm(){
        AlarmManager AlarmMgr = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);

        //Intent ReceiverIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, ReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent ReceiverIntent = new Intent("ALARM");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(con.getApplicationContext(), 0, ReceiverIntent, 0);

        long interval = 2000;
        AlarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+interval, interval, pendingIntent);

        Log.i("ALARM SET","ALARM SET");
    }
}
