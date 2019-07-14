package com.fitkeke.root.socialapp.activities.general_articles;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fitkeke.root.socialapp.R;
import com.fitkeke.root.socialapp.activities.UserProfile;
import com.fitkeke.root.socialapp.utilities.GetGeneralArticles;

import de.hdodenhof.circleimageview.CircleImageView;


public class GeneralArticles extends AppCompatActivity {

    private TextView btnTab1;
    private TextView btnTab2;
    private TextView btnTab3;
    private TextView btnTab4;
    private TextView btnTab5;
    private TextView btnContent;
    private LinearLayout layTabs;
    private boolean isopen = false;
    private Animation animationOpen;
    private Animation animationClose;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CircleImageView btnProfile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_articles);

        // init views
        initViews();

        GetGeneralArticles getGeneralArticles = new GetGeneralArticles(GeneralArticles.this, recyclerView);
        getGeneralArticles.getAllArticles(0);

        animationOpen = AnimationUtils.loadAnimation(GeneralArticles.this, R.anim.fab_open);
        animationClose = AnimationUtils.loadAnimation(GeneralArticles.this, R.anim.fab_close);


        btnContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isopen == false){
                    layTabs.setVisibility(View.VISIBLE);
                    btnTab1.setClickable(true);
                    btnTab2.setClickable(true);
                    btnTab3.setClickable(true);
                    btnTab4.setClickable(true);
                    btnTab5.setClickable(true);
                    layTabs.startAnimation(animationOpen);
                    isopen = true;
                }else {
                    layTabs.startAnimation(animationClose);
                    layTabs.setVisibility(View.GONE);
                    btnTab1.setClickable(false);
                    btnTab2.setClickable(false);
                    btnTab3.setClickable(false);
                    btnTab4.setClickable(false);
                    btnTab5.setClickable(false);
                    isopen = false;
                }

            }
        });



        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GeneralArticles.this , UserProfile.class));
            }
        });



        btnTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTaps(0);
                hideMenu();
                GetGeneralArticles getGeneralArticles = new GetGeneralArticles(GeneralArticles.this, recyclerView);
                getGeneralArticles.getAllArticles(0);
            }
        });

        btnTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTaps(1);
                hideMenu();
                GetGeneralArticles getGeneralArticles = new GetGeneralArticles(GeneralArticles.this, recyclerView);
                getGeneralArticles.getAllArticles(1);
            }
        });

        btnTab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTaps(2);
                hideMenu();
                GetGeneralArticles getGeneralArticles = new GetGeneralArticles(GeneralArticles.this, recyclerView);
                getGeneralArticles.getAllArticles(2);

            }
        });

        btnTab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTaps(3);
                hideMenu();
                GetGeneralArticles getGeneralArticles = new GetGeneralArticles(GeneralArticles.this, recyclerView);
                getGeneralArticles.getAllArticles(3);
            }
        });

        btnTab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTaps(4);
                hideMenu();
                GetGeneralArticles getGeneralArticles = new GetGeneralArticles(GeneralArticles.this, recyclerView);
                getGeneralArticles.getAllArticles(4);
            }
        });



    }


    private void initViews() {
        btnTab1 = findViewById(R.id.btn_tab1);
        btnTab2 = findViewById(R.id.btn_tab2);
        btnTab3 = findViewById(R.id.btn_tab3);
        btnTab4 = findViewById(R.id.btn_tab4);
        btnTab5 = findViewById(R.id.btn_tab5);
        btnContent = findViewById(R.id.btn_content);
        layTabs = findViewById(R.id.tabs_articles);
        recyclerView = findViewById(R.id.recycler_articles);
        btnProfile = findViewById(R.id.btn_profile_health);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(GeneralArticles.this);
        recyclerView.setLayoutManager(layoutManager);
    }


    private void hideMenu(){
        layTabs.startAnimation(animationClose);
        layTabs.setVisibility(View.GONE);
        btnTab1.setClickable(false);
        btnTab2.setClickable(false);
        btnTab3.setClickable(false);
        btnTab4.setClickable(false);
        btnTab5.setClickable(false);
        isopen = false;
    }


    private void changeTaps(int position) {

        if(position == 0){

            btnTab1.setTextColor(this.getResources().getColor(R.color.colorAccent));
            btnTab1.setBackgroundColor(this.getResources().getColor(R.color.colorPrimaryDark));

            btnTab2.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab2.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab3.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab3.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab4.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab4.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab5.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab5.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

        }

        if(position == 1){

            btnTab1.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab1.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab2.setTextColor(this.getResources().getColor(R.color.colorAccent));
            btnTab2.setBackgroundColor(this.getResources().getColor(R.color.colorPrimaryDark));

            btnTab3.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab3.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab4.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab4.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab5.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab5.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

        }

        if(position == 2){

            btnTab1.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab1.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab2.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab2.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab3.setTextColor(this.getResources().getColor(R.color.colorAccent));
            btnTab3.setBackgroundColor(this.getResources().getColor(R.color.colorPrimaryDark));

            btnTab4.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab4.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab5.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab5.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));
        }

        if(position == 3){

            btnTab1.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab1.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab2.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab2.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab3.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab3.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab4.setTextColor(this.getResources().getColor(R.color.colorAccent));
            btnTab4.setBackgroundColor(this.getResources().getColor(R.color.colorPrimaryDark));

            btnTab5.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab5.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));
        }

        if(position == 4){

            btnTab1.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab1.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab2.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab2.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab3.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab3.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab4.setTextColor(this.getResources().getColor(R.color.colorGray));
            btnTab4.setBackgroundColor(this.getResources().getColor(R.color.colorLightGray));

            btnTab5.setTextColor(this.getResources().getColor(R.color.colorAccent));
            btnTab5.setBackgroundColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

    }
}
