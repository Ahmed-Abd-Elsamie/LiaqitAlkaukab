package com.fitkeke.root.socialapp.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.fitkeke.root.socialapp.MainActivity;
import com.fitkeke.root.socialapp.R;
import com.fitkeke.root.socialapp.generalVars;
import com.fitkeke.root.socialapp.modules.ItemFood;
import com.fitkeke.root.socialapp.notifications.AlarmReciver;
import com.fitkeke.root.socialapp.notifications.NotificationFoodReciver;
import com.fitkeke.root.socialapp.notifications.NotificationWaterReciver;
import com.fitkeke.root.socialapp.notifications.ResetAlarms;
import com.fitkeke.root.socialapp.utilities.DBHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private String uid;
    private Button BtnLogout;
    private int GALARY_REQUEST = 100;
    private Uri imgurl;
    private CircleImageView UserProfileImg;
    private StorageReference storageReference;
    private TextView txtInfoEmail;
    private Button btnLogout;
    private FirebaseAuth mAuth;
    private TextView txtName;
    private EditText txtAge;
    private EditText txtHeight;
    private EditText txtWeight;
    private TextView txtWaterPercent;
    private TextView txtWaterNeeds;
    private Button btnWater;
    private Button btnFood;
    private TextView txtBodyState;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        // init views
        initViews();

        // getting water liters needed
        getWaterLiters();

        // handle intent
        // getting sent intent
        Intent intentSent = getIntent();
        String type = intentSent.getStringExtra("type");
        // check type
        if (type.equals("notify")){
            if (intentSent.getStringExtra("state").equals("drink")){

                // get water level
                SharedPreferences prefs = getSharedPreferences("water_level", MODE_PRIVATE);
                int totalLev = prefs.getInt("total",0);
                int level = prefs.getInt("level", 0);
                // save new value
                SharedPreferences.Editor editor = getSharedPreferences("water_level", MODE_PRIVATE).edit();
                editor.putInt("level", level + 1);
                editor.apply();

            }else if (intentSent.getStringExtra("state").equals("nodrink")){
                // get water level
                SharedPreferences prefs = getSharedPreferences("water_level", MODE_PRIVATE);
                int totalLev = prefs.getInt("total",0);
                int level = prefs.getInt("level", 0);
                // save new value
                SharedPreferences.Editor editor = getSharedPreferences("water_level", MODE_PRIVATE).edit();
                editor.putInt("level", level);
                editor.apply();

            }else {
                //delay

                // get water level
                SharedPreferences prefs = getSharedPreferences("water_level", MODE_PRIVATE);
                int totalLev = prefs.getInt("total",0);
                int level = prefs.getInt("level", 0);
                // save new value
                SharedPreferences.Editor editor = getSharedPreferences("water_level", MODE_PRIVATE).edit();
                editor.putInt("level", level);
                editor.apply();

                // delay 10 Mins
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent i = new Intent(UserProfile.this, AlarmReciver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(UserProfile.this, 101, i, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 60 * 1000, pendingIntent);

            }
        }else {

        }


        // getting water levels to handle percent
        getWaterLevel();

        // init firebase
        mAuth = FirebaseAuth.getInstance();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(UserProfile.this, Login.class));
                LoginManager.getInstance().logOut();

                finish();
            }
        });

        btnWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, WaterProgramActivity.class));
            }
        });

        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfile.this, FoodProgramActivity.class);
                i.putExtra("type","other");
                startActivity(i);
            }
        });

        // resetting all day data
        AlarmManager alarmManager;
        PendingIntent pendingIntent;
        Intent intent;

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(UserProfile.this, ResetAlarms.class);
        pendingIntent = PendingIntent.getBroadcast(UserProfile.this, 1000, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //generalVars.GeneralCalendarEnd = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        if (generalVars.GeneralCalendarEnd == null){
            // set default value to end day at 12 PM
            calendar.set(Calendar.HOUR, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, generalVars.GeneralCalendarEnd.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }



    }

    private void getWaterLiters() {
        SharedPreferences prefs1 = getSharedPreferences("water_liters", MODE_PRIVATE);
        float liters = prefs1.getFloat("liters",0);
        txtWaterNeeds.setText("جسمك بحاجه الي شرب " + liters + " لتر من الماء يوميا");
    }

    private void getWaterLevel() {
        SharedPreferences prefs = getSharedPreferences("water_level", MODE_PRIVATE);
        int totalLev = prefs.getInt("total",1);
        int level = prefs.getInt("level", 0);
        float percent = ((float)level / (float)totalLev) * 100;
        txtWaterPercent.setText(percent + " % ");
        // setting color
        if (percent > 33.33 && percent <= 66.66){
            txtWaterPercent.setTextColor(getResources().getColor(R.color.waterColorMid));
            txtBodyState.setText("جسمك لسه محتاج ميه");
            txtBodyState.setTextColor(getResources().getColor(R.color.waterColorMid));
        }else if (percent > 66.66 && percent <= 100){
            txtWaterPercent.setTextColor(getResources().getColor(R.color.waterColorLight));
            txtBodyState.setText("جسمك ممتاز");
            txtBodyState.setTextColor(getResources().getColor(R.color.waterColorLight));
        }else if (percent < 33.33){
            txtWaterPercent.setTextColor(getResources().getColor(R.color.waterColorDark));
            txtBodyState.setText("جسمك لسه محتاج ميه");
            txtBodyState.setTextColor(getResources().getColor(R.color.waterColorDark));
        }
    }

    private void initViews() {
        btnLogout = findViewById(R.id.btn_logout);
        btnWater = findViewById(R.id.btn_water);
        btnFood = findViewById(R.id.btn_food);

        txtName = findViewById(R.id.profile_name);
        txtWaterNeeds = findViewById(R.id.txt_water_needs);
        /*txtAge = findViewById(R.id.txt_age);
        txtHeight = findViewById(R.id.txt_height);
        txtWeight = findViewById(R.id.txt_weight);
        */
        txtWaterPercent = findViewById(R.id.txt_water_percent);
        txtBodyState = findViewById(R.id.txt_body_state);
    }


    @Override
    protected void onStart() {
        super.onStart();
        // get user Info
        getuserInfo();

    }

    private void getuserInfo() {
        uid = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtName.setText(dataSnapshot.child("name").getValue().toString());
                /*txtAge.setText(dataSnapshot.child("age").getValue().toString());
                txtHeight.setText(dataSnapshot.child("height").getValue().toString());
                txtWeight.setText(dataSnapshot.child("weight").getValue().toString());
                */

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        // water needs
        // getting water liters needed
        getWaterLiters();


        // water level
        getWaterLevel();


    }
}