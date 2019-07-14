package com.fitkeke.root.socialapp.notifications;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.fitkeke.root.socialapp.R;
import com.fitkeke.root.socialapp.activities.UserProfile;

import static android.content.Context.MODE_PRIVATE;

public class AlarmReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent repeating_intent = new Intent(context, UserProfile.class);

        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 101, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.drink))
                .setContentTitle("لياقه الكوكب")
                .setContentText("تذكير لشرب كوب من الماء")
                .addAction(R.drawable.ic_eat_now_black_24dp, "اشرب الان", PendingIntent.getActivity(context, 0,
                        drinkIntent(context), PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(R.drawable.ic_close_black_24dp, "مليش نفس", PendingIntent.getActivity(context, 1,
                        NotdrinkIntent(context), PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(R.drawable.ic_delay_black_24dp, "نأجلها شويه", PendingIntent.getActivity(context, 2,
                        DelaydrinkIntent(context), PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true);

        Toast.makeText(context, "Running...", Toast.LENGTH_SHORT).show();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(101, builder.build());
    }


    private Intent drinkIntent(Context context){

        Intent intent = new Intent(context, UserProfile.class);
        intent.putExtra("type", "notify");
        intent.putExtra("state", "drink");

        return intent;
    }

    private Intent NotdrinkIntent(Context context){

        Intent intent = new Intent(context, UserProfile.class);
        intent.putExtra("type", "notify");
        intent.putExtra("state", "nodrink");

        return intent;
    }


    private Intent DelaydrinkIntent(Context context){


        Intent intent = new Intent(context, UserProfile.class);
        intent.putExtra("type", "notify");
        intent.putExtra("state", "wait");

        return intent;
    }
}
