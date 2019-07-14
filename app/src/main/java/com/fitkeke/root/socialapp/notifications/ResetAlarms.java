package com.fitkeke.root.socialapp.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.fitkeke.root.socialapp.activities.UserProfile;
import com.fitkeke.root.socialapp.modules.ItemFood;
import com.fitkeke.root.socialapp.utilities.DBHelper;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ResetAlarms extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "The Day End !", Toast.LENGTH_SHORT).show();

        // Stop Alarms
        AlarmManager alarmManager;
        PendingIntent pendingIntent;
        Intent i;

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        i = new Intent(context, ResetAlarms.class);
        pendingIntent = PendingIntent.getBroadcast(context, 101, i, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);

        // Reset food counter
        SharedPreferences.Editor editor = context.getSharedPreferences("food_alarm_counter", MODE_PRIVATE).edit();
        editor.putInt("count", 102);
        editor.apply();

        // change all food state to wait again
        DBHelper dbHelper = new DBHelper(context);

        ArrayList<ItemFood> list = dbHelper.getAllFoods();

        for (int x = 0; x < list.size(); x++){
            ItemFood itemFood = list.get(x);
            itemFood.setState("wait");
        }

        // Reset water level
        SharedPreferences.Editor editor2 = context.getSharedPreferences("water_level", MODE_PRIVATE).edit();
        editor2.putInt("level", 0);
        editor2.apply();
    }
}