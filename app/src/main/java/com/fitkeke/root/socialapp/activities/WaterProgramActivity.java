package com.fitkeke.root.socialapp.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fitkeke.root.socialapp.R;
import com.fitkeke.root.socialapp.generalVars;
import com.fitkeke.root.socialapp.modules.Alarm;
import com.fitkeke.root.socialapp.notifications.NotificationWaterReciver;

import java.util.Calendar;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static com.fitkeke.root.socialapp.activities.AlarmLandingPageActivity.launchIntent;

public class WaterProgramActivity extends AppCompatActivity {

    private Button btnCalc;
    private boolean male = true;
    private EditText txtWeight;
    private EditText txtAge;
    private EditText txtHeight;
    private TextView txtResult;
    private Button btnMale;
    private Button btnFemale;
    private Button btnAlarm;
    private float waterLiters = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.water_program_activity);
        getSupportActionBar().hide();

        // init views
        initViews();

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateWater();
            }
        });

        btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male = true;
                btnMale.setBackgroundResource(R.drawable.btn_edit);
                btnFemale.setBackgroundResource(R.drawable.bg_btns);
            }
        });
        btnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male = false;
                btnMale.setBackgroundResource(R.drawable.bg_btns);
                btnFemale.setBackgroundResource(R.drawable.btn_edit);
            }
        });

        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // show dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(WaterProgramActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.water_set_dialog, null);
                builder.setView(view);
                Button btnSet = view.findViewById(R.id.btn_set_day);
                final TimePicker start = view.findViewById(R.id.time_start);
                final TimePicker end = view.findViewById(R.id.time_end);


                btnSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // start of the day
                        Calendar calendarStart = Calendar.getInstance();
                        calendarStart.set(Calendar.HOUR, start.getCurrentHour());
                        calendarStart.set(Calendar.MINUTE, start.getCurrentMinute());
                        calendarStart.set(Calendar.SECOND, 0);
                        generalVars.GeneralCalendarStart = calendarStart;
                        // end of the day
                        Calendar calendarEnd = Calendar.getInstance();
                        calendarEnd.set(Calendar.HOUR, end.getCurrentHour());
                        calendarEnd.set(Calendar.MINUTE, end.getCurrentMinute());
                        calendarEnd.set(Calendar.SECOND, 0);
                        generalVars.GeneralCalendarEnd = calendarEnd;

                        // start alarm
                        StartAlarm(calendarStart.getTimeInMillis(), calendarEnd.getTimeInMillis());

                    }
                });


                builder.show();

            }
        });

    }

    private void initViews() {
        btnCalc = findViewById(R.id.btn_result);
        txtWeight = findViewById(R.id.txt_weight);
        txtAge = findViewById(R.id.txt_age);
        txtHeight = findViewById(R.id.txt_height);
        txtResult = findViewById(R.id.result);
        btnMale = findViewById(R.id.btn_male);
        btnFemale = findViewById(R.id.btn_female);
        btnAlarm = findViewById(R.id.btn_water_alarm);
    }

    private void StartAlarm(long start, long end) {
        long totalDay = end - start;
        if (totalDay < 0){
            totalDay = -1 * totalDay;
        }
        //get data
        calculateWater();
        long interval = (long) (totalDay / (waterLiters / 0.25));
        // save data
        SharedPreferences.Editor editor = getSharedPreferences("water_alarm_data", MODE_PRIVATE).edit();
        editor.putLong("start", start);
        editor.putLong("end", end);
        editor.putLong("interval", interval);
        editor.apply();



        AlarmManager alarmManager;
        PendingIntent pendingIntent;
        Intent intent;
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(WaterProgramActivity.this, NotificationWaterReciver.class);
        pendingIntent = PendingIntent.getBroadcast(WaterProgramActivity.this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(SDK_INT > LOLLIPOP) {
            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(start, pendingIntent), pendingIntent);
        } else if(SDK_INT > KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, start, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, start, pendingIntent);
        }
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    private void calculateWater() {
        if (!TextUtils.isEmpty(txtWeight.getText())){
            float weight = Float.parseFloat(txtWeight.getText().toString());
            // check
            if(male == true){
                txtResult.setText("تحتاج الي شرب " + 0.033 * weight + " لتر من الماء يوميا");
                waterLiters = (float) (0.033 * weight);
            }else {
                txtResult.setText("تحتاجين الي شرب " + 0.033 * weight + " لتر من الماء يوميا");
                waterLiters = (float) (0.033 * weight);
            }
            // save water liters
            SharedPreferences.Editor editor = getSharedPreferences("water_liters", MODE_PRIVATE).edit();
            editor.putFloat("liters", waterLiters);
            editor.apply();

            // save water level
            SharedPreferences.Editor editor2 = getSharedPreferences("water_level", MODE_PRIVATE).edit();
            editor2.putFloat("total", (float) (waterLiters / 0.25));
            editor2.apply();

        }else {
            Toast.makeText(WaterProgramActivity.this, "دخل وزنك", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        // get state
        SharedPreferences prefs = getSharedPreferences("water_alarm", MODE_PRIVATE);
        String restoredText = prefs.getString("alarm", null);
        if (restoredText != null) {
            String data = prefs.getString("alarm", "No name defined");//"No name defined" is the default value.
            if (data.equals("on")){
                btnAlarm.setText("التنبيه مفعل ايقاف التنبيه ؟   ");

            }else if (data.equals("off")){
                btnAlarm.setText("التنبيه غير مفعل تشغيل التنبيه ؟   ");
            }else {
                btnAlarm.setText("التنبيه غير مفعل تشغيل التنبيه ؟   ");
            }
        }
    }
}