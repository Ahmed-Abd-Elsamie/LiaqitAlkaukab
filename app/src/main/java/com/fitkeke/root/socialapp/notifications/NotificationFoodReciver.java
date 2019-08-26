package com.fitkeke.root.socialapp.notifications;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.fitkeke.root.socialapp.R;
import com.fitkeke.root.socialapp.activities.FoodProgramActivity;
import com.fitkeke.root.socialapp.activities.UserProfile;
import com.fitkeke.root.socialapp.modules.ItemFood;
import com.fitkeke.root.socialapp.utilities.DBHelper;


import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class NotificationFoodReciver extends BroadcastReceiver {

    private int request = 102;
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent repeating_intent = new Intent(context, UserProfile.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //get request code
        SharedPreferences prefs = context.getSharedPreferences("food_alarm_counter", MODE_PRIVATE);
        request = prefs.getInt("count",102);



        PendingIntent pendingIntent = PendingIntent.getActivity(context, request, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.eat))
                .setContentTitle("لياقه الكوكب")
                .setContentText("تذكير لتناول وجبتك")
                .addAction(R.drawable.ic_eat_now_black_24dp, "تناولها الان", PendingIntent.getActivity(context, 0,
                        eatIntent(context), PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(R.drawable.ic_close_black_24dp, "مليش نفس", PendingIntent.getActivity(context, 1,
                        NoteatIntent(context), PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(R.drawable.ic_delay_black_24dp, "نأجلها شويه", PendingIntent.getActivity(context, 2,
                        DelayeatIntent(context), PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true);


            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(request, builder.build());



    }

    private Intent eatIntent(Context context){

        Intent intent = new Intent(context, FoodProgramActivity.class);
        intent.putExtra("type", "notify");
        intent.putExtra("state", "eaten");
        intent.putExtra("request", request);

        return intent;
    }

    private Intent NoteatIntent(Context context){

        Intent intent = new Intent(context, FoodProgramActivity.class);
        intent.putExtra("type", "notify");
        intent.putExtra("state", "noeat");
        intent.putExtra("request", request);

        return intent;
    }

    private Intent DelayeatIntent(Context context){

        Intent intent = new Intent(context, FoodProgramActivity.class);
        intent.putExtra("type", "notify");
        intent.putExtra("state", "wait");
        intent.putExtra("request", request);

        return intent;
    }


}