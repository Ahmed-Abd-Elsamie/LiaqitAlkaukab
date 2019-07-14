package com.fitkeke.root.socialapp.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fitkeke.root.socialapp.activities.WaterProgramActivity;
import com.fitkeke.root.socialapp.generalVars;
import com.fitkeke.root.socialapp.notifications.AlarmReciver;
import com.fitkeke.root.socialapp.notifications.NotificationWaterReciver;

import java.util.Calendar;

public class WaterAlarm {

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent intent;


    public WaterAlarm(long interval, Context context) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(context, AlarmReciver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendarStart = generalVars.GeneralCalendarStart;

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendarStart.getTimeInMillis(),interval * 60 * 60 * 1000, pendingIntent);

    }

    // do some alarms and repeat them as you like.

}