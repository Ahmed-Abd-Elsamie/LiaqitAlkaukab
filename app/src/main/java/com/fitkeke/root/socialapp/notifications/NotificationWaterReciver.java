package com.fitkeke.root.socialapp.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.widget.Toast;

import com.fitkeke.root.socialapp.generalVars;
import com.fitkeke.root.socialapp.utilities.WaterAlarm;
import static android.content.Context.MODE_PRIVATE;

public class NotificationWaterReciver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        // this method is executed every 24 hours
        Toast.makeText(context, "Start Alarm ...", Toast.LENGTH_SHORT).show();
        // create everyday alarms
        // reset water level
        SharedPreferences.Editor editor = context.getSharedPreferences("water_level", MODE_PRIVATE).edit();
        editor.putInt("level", 0);
        editor.apply();

        SharedPreferences prefs = context.getSharedPreferences("water_liters", MODE_PRIVATE);
        float liters = prefs.getFloat("liters",0);
        final long interval = (long) (generalVars.totalHours / (liters / 0.25));

        WaterAlarm waterAlarm = new WaterAlarm(interval, context);

    }
}