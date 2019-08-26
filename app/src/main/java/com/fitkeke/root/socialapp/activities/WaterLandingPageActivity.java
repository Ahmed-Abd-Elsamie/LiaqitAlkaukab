package com.fitkeke.root.socialapp.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.fitkeke.root.socialapp.R;
import com.fitkeke.root.socialapp.notifications.NotificationWaterReciver;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

public class WaterLandingPageActivity extends AppCompatActivity {

    private Button btnEatNow, btnNotNow, btnDelay;
    private ImageView imgFood;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_alarm_landing_page);

        // init views
        initViews();

        // get data
        final SharedPreferences sharedPreferences = getSharedPreferences("water_level", MODE_PRIVATE);
        final float myLevel = sharedPreferences.getFloat("myLevel", 0);
        // buttons events

        btnEatNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // save new data
                SharedPreferences.Editor editor = getSharedPreferences("water_level", MODE_PRIVATE).edit();
                editor.putFloat("myLevel", (float) (myLevel + 1));
                editor.apply();

                startActivity(new Intent(WaterLandingPageActivity.this, UserProfile.class));
                finish();

            }
        });

        btnNotNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // no change
                startActivity(new Intent(WaterLandingPageActivity.this, UserProfile.class));
                finish();

            }
        });

        btnDelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // delay for 10 minutes
                AlarmManager alarmManager;
                PendingIntent pendingIntent;
                Intent intent;
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                intent = new Intent(WaterLandingPageActivity.this, NotificationWaterReciver.class);
                pendingIntent = PendingIntent.getBroadcast(WaterLandingPageActivity.this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                if(SDK_INT > LOLLIPOP) {
                    alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(System.currentTimeMillis() + 10 * 60 * 1000, pendingIntent), pendingIntent);
                } else if(SDK_INT > KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 60 * 1000, pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 60 * 1000, pendingIntent);
                }

                finish();

            }
        });

    }

    private void initViews() {

        btnEatNow = findViewById(R.id.eat_now);
        btnNotNow = findViewById(R.id.not_now);
        btnDelay = findViewById(R.id.delay_btn);

        imgFood = findViewById(R.id.img_food);

        imgFood.setImageResource(R.drawable.water_glass);

        btnEatNow.setText("اشربها دلوقتي");

    }
}
