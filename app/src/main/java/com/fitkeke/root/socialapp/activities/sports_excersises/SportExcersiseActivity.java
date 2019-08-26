package com.fitkeke.root.socialapp.activities.sports_excersises;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.fitkeke.root.socialapp.R;

public class SportExcersiseActivity extends AppCompatActivity {

    private LinearLayout BtnGym;
    private LinearLayout BtnCardu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_excersise);
        getSupportActionBar().hide();

        // init views
        initViews();


        // buttons events

        BtnGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SportExcersiseActivity.this, GymActivity.class));


            }
        });


        BtnCardu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SportExcersiseActivity.this, CarduActivity.class));


            }
        });


    }

    private void initViews() {

        BtnGym = findViewById(R.id.btn_gym);
        BtnCardu = findViewById(R.id.btn_cardu);

    }
}
