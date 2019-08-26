package com.fitkeke.root.socialapp.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.fitkeke.root.socialapp.R;
import com.fitkeke.root.socialapp.activities.UserProfile;
import com.fitkeke.root.socialapp.activities.WaterLandingPageActivity;
import com.fitkeke.root.socialapp.activities.WaterProgramActivity;
import com.fitkeke.root.socialapp.generalVars;
import com.fitkeke.root.socialapp.utilities.WaterAlarm;
import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

public class NotificationWaterReciver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Start Alarm ...", Toast.LENGTH_SHORT).show();
        // create everyday alarms
        // reset water level
        /*SharedPreferences.Editor editor = context.getSharedPreferences("water_level", MODE_PRIVATE).edit();
        editor.putInt("level", 0);
        editor.apply();*/

        /*SharedPreferences prefs = context.getSharedPreferences("water_liters", MODE_PRIVATE);
        float liters = prefs.getFloat("liters",0);
        final long interval = (long) (generalVars.totalHours / (liters / 0.25));*/
        //WaterAlarm waterAlarm = new WaterAlarm(interval, context);

        Intent repeating_intent = new Intent(context, WaterLandingPageActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_alarm_white)
                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.drink))
                .setVibrate(new long[] {1000,500,1000,500,1000,500})
                .setContentTitle("لياقه الكوكب")
                .setContentText("تذكير لشرب كوب من الماء")
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100, builder.build());

        // repeat alarm
        fireNextAlarm(context);

    }

    private void fireNextAlarm(Context context) {

        // Alarm Info
        SharedPreferences sh = context.getSharedPreferences("water_alarm_data", MODE_PRIVATE);
        long interval = sh.getLong("interval", 60 * 60 * 1000);
        long start = sh.getLong("start", 60 * 60 * 1000);
        long end = sh.getLong("start", 2 * 60 * 60 * 1000);

        long next = 0;
        if (System.currentTimeMillis() + interval < end){
            next = start;
            // Reset water level
            SharedPreferences.Editor editor = context.getSharedPreferences("water_level", MODE_PRIVATE).edit();
            editor.putFloat("myLevel", 0);
            editor.apply();
        }else {
            next = System.currentTimeMillis() + interval;
        }

        AlarmManager alarmManager;
        PendingIntent pendingIntent;
        Intent intent;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(context, NotificationWaterReciver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(SDK_INT > LOLLIPOP) {
            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(next, pendingIntent), pendingIntent);
        } else if(SDK_INT > KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, next, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, next, pendingIntent);
        }

    }
}