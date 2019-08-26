package com.fitkeke.root.socialapp.activities.body_health;


import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fitkeke.root.socialapp.R;
import com.fitkeke.root.socialapp.activities.UserProfile;
import com.fitkeke.root.socialapp.generalVars;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class BodyHealth extends AppCompatActivity {

    private TextView btnArticles;
    private TextView btnRoach;
    private TextView btnSupp;
    private TextView btnCalori;

    private ViewPager MainPager;
    private BodyHealthPagerAdapter mPagerViewAdapter;
    private CircleImageView btnProfile;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private String uid;
    private FirebaseUser user;
    private String MyIMG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_health);
        getSupportActionBar().hide();

        // init Views
        btnArticles = findViewById(R.id.btn_artic);
        btnRoach = findViewById(R.id.btn_roach);
        btnSupp = findViewById(R.id.btn_supp);
        btnCalori = findViewById(R.id.btn_calori);
        btnProfile = findViewById(R.id.btn_profile_health);

        MainPager = findViewById(R.id.body_health_pager);

        mPagerViewAdapter = new BodyHealthPagerAdapter(getSupportFragmentManager());
        MainPager.setAdapter(mPagerViewAdapter);
        //MainPager.setCurrentItem(1);

        btnArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainPager.setCurrentItem(0);
            }
        });

        btnRoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainPager.setCurrentItem(1);
            }
        });

        btnSupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainPager.setCurrentItem(2);
            }
        });

        btnCalori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainPager.setCurrentItem(3);
            }
        });

        MainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTaps(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BodyHealth.this , UserProfile.class));
            }
        });


    }


    private void changeTaps(int position) {

        if(position == 0){

            btnArticles.setTextColor(this.getResources().getColor(R.color.colorAccent));
            btnArticles.setBackgroundColor(this.getResources().getColor(R.color.colorPrimaryDark));

            btnRoach.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnRoach.setBackgroundColor(this.getResources().getColor(R.color.colorAccent));

            btnSupp.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnSupp.setBackgroundColor(this.getResources().getColor(R.color.colorAccent));

            btnCalori.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnCalori.setBackgroundColor(this.getResources().getColor(R.color.colorAccent));

            generalVars.post = "health_recipe";

            //edit_post_data.flagTabs = "tab0";
        }

        if(position == 1){

            btnArticles.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnArticles.setBackgroundColor(this.getResources().getColor(R.color.colorAccent));

            btnRoach.setTextColor(this.getResources().getColor(R.color.colorAccent));
            btnRoach.setBackgroundColor(this.getResources().getColor(R.color.colorPrimaryDark));

            btnSupp.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnSupp.setBackgroundColor(this.getResources().getColor(R.color.colorAccent));

            btnCalori.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnCalori.setBackgroundColor(this.getResources().getColor(R.color.colorAccent));

            generalVars.post = "health_articles";

            //edit_post_data.flagTabs = "tab1";
        }

        if(position == 2){

            btnArticles.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnArticles.setBackgroundColor(this.getResources().getColor(R.color.colorAccent));

            btnRoach.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnRoach.setBackgroundColor(this.getResources().getColor(R.color.colorAccent));

            btnSupp.setTextColor(this.getResources().getColor(R.color.colorAccent));
            btnSupp.setBackgroundColor(this.getResources().getColor(R.color.colorPrimaryDark));

            btnCalori.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnCalori.setBackgroundColor(this.getResources().getColor(R.color.colorAccent));

            generalVars.post = "health_supp";

            //edit_post_data.flagTabs = "tab2";
        }

        if(position == 3){

            btnArticles.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnArticles.setBackgroundColor(this.getResources().getColor(R.color.colorAccent));

            btnRoach.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnRoach.setBackgroundColor(this.getResources().getColor(R.color.colorAccent));

            btnSupp.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnSupp.setBackgroundColor(this.getResources().getColor(R.color.colorAccent));

            btnCalori.setTextColor(this.getResources().getColor(R.color.colorAccent));
            btnCalori.setBackgroundColor(this.getResources().getColor(R.color.colorPrimaryDark));

            generalVars.post = "health_calories";

            //edit_post_data.flagTabs = "tab3";

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //edit_post_data.flagTabs = "default";
    }
}