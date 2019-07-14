package com.fitkeke.root.socialapp.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fitkeke.root.socialapp.FoodAdapter;
import com.fitkeke.root.socialapp.R;
import com.fitkeke.root.socialapp.modules.ItemFood;
import com.fitkeke.root.socialapp.notifications.NotificationFoodReciver;
import com.fitkeke.root.socialapp.utilities.DBHelper;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FoodProgramActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton btnAddFood;
    private TextView txtState;
    private DBHelper dbHelper;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_program_activity);

        // init views
        initViews();

        dbHelper = new DBHelper(FoodProgramActivity.this);
        ArrayList<ItemFood> arrayList = dbHelper.getAllFoods();

        if (arrayList.size() > 0){
            txtState.setVisibility(View.INVISIBLE);
        }else {
            txtState.setVisibility(View.VISIBLE);
        }

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");

        if (type.equals("notify")){
            int request = intent.getIntExtra("request",102);
            if (intent.getStringExtra("state").equals("eaten")){

                DBHelper dbHelper = new DBHelper(FoodProgramActivity.this);
                ArrayList<ItemFood> list = dbHelper.getAllFoods();
                for (int i = 0; i < list.size(); i++){
                    ItemFood itemFood = list.get(i);
                    if (request == itemFood.getRequest()){
                        int id = itemFood.getId();
                        dbHelper.updateFood(id, itemFood.getCarb(), itemFood.getProtin(),itemFood.getFats(),itemFood.getAliaf(), itemFood.getTime(), request, "eaten");
                        break;
                    }
                }

                //increment counter
                SharedPreferences.Editor editor = getSharedPreferences("food_alarm_counter", MODE_PRIVATE).edit();
                editor.putInt("count", request + 1);
                editor.apply();


            }else if (intent.getStringExtra("state").equals("noeat")){
                DBHelper dbHelper = new DBHelper(FoodProgramActivity.this);
                ArrayList<ItemFood> list = dbHelper.getAllFoods();
                for (int i = 0; i < list.size(); i++){
                    ItemFood itemFood = list.get(i);
                    if (request == itemFood.getRequest()){
                        int id = itemFood.getId();
                        dbHelper.updateFood(id, itemFood.getCarb(), itemFood.getProtin(),itemFood.getFats(),itemFood.getAliaf(), itemFood.getTime(), request, "noeat");
                        break;
                    }
                }

                //increment counter
                SharedPreferences.Editor editor = getSharedPreferences("food_alarm_counter", MODE_PRIVATE).edit();
                editor.putInt("count", request + 1);
                editor.apply();

            }else {
                DBHelper dbHelper = new DBHelper(FoodProgramActivity.this);
                ArrayList<ItemFood> list = dbHelper.getAllFoods();
                for (int i = 0; i < list.size(); i++){
                    ItemFood itemFood = list.get(i);
                    if (request == itemFood.getRequest()){
                        int id = itemFood.getId();
                        dbHelper.updateFood(id, itemFood.getCarb(), itemFood.getProtin(),itemFood.getFats(),itemFood.getAliaf(), itemFood.getTime(), request, "wait");
                        // delay 10 Mins
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent delayIntent = new Intent(FoodProgramActivity.this, NotificationFoodReciver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(FoodProgramActivity.this, request, delayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 60 * 1000, pendingIntent);
                        break;
                    }
                }

            }
        }else {

        }

        mLayoutManager = new LinearLayoutManager(FoodProgramActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        FoodAdapter adapter = new FoodAdapter(arrayList, FoodProgramActivity.this);
        recyclerView.setAdapter(adapter);

        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FoodProgramActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.add_food_for_day,null);
                builder.setView(view);
                Button btnAdd = (Button)view.findViewById(R.id.btn_add_food_dialog);
                Button btnDel = (Button)view.findViewById(R.id.btn_del_food_dialog);
                btnDel.setVisibility(View.INVISIBLE);
                final EditText txtCarb = (EditText)view.findViewById(R.id.txt_food_day_carb);
                final EditText txtProtin = (EditText)view.findViewById(R.id.txt_food_day_protin);
                final EditText txtFats = (EditText)view.findViewById(R.id.txt_food_day_fats);
                final EditText txtAliaf = (EditText)view.findViewById(R.id.txt_food_day_aliaf);

                final TimePicker timePicker = (TimePicker)view.findViewById(R.id.time_pick);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // setting alarm
                        // calender
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR, timePicker.getCurrentHour());
                        calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                        // save request code
                        // get last code
                        SharedPreferences prefs = getSharedPreferences("food_alarm", MODE_PRIVATE);
                        int request = prefs.getInt("alarm", 101) + 1;
                        // saving
                        SharedPreferences.Editor editor = getSharedPreferences("food_alarm", MODE_PRIVATE).edit();
                        editor.putInt("alarm", request);
                        editor.apply();
                        Toast.makeText(FoodProgramActivity.this, request + "", Toast.LENGTH_SHORT).show();
                        // save to sqlite
                        dbHelper.insertFood(txtCarb.getText().toString(), txtProtin.getText().toString(), txtFats.getText().toString(), txtAliaf.getText().toString(), timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute(), request, "wait");

                        AlarmManager alarmManager;
                        PendingIntent pendingIntent;
                        Intent intentFood;

                        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        intentFood = new Intent(getApplicationContext(), NotificationFoodReciver.class);
                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), request, intentFood, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);

                        Toast.makeText(FoodProgramActivity.this, "Alarm Set", Toast.LENGTH_SHORT).show();

                    }
                });

                builder.show();
            }
        });

    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_food);
        btnAddFood = findViewById(R.id.btn_add_food);
        txtState = findViewById(R.id.food_state);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<ItemFood> arrayList = dbHelper.getAllFoods();
        if (arrayList.size() > 0){
            txtState.setVisibility(View.INVISIBLE);
        }else {
            txtState.setVisibility(View.VISIBLE);
        }

        mLayoutManager = new LinearLayoutManager(FoodProgramActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        FoodAdapter adapter = new FoodAdapter(arrayList, FoodProgramActivity.this);
        recyclerView.setAdapter(adapter);

    }
}