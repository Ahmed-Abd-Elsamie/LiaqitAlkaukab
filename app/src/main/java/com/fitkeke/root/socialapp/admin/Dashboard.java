package com.fitkeke.root.socialapp.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fitkeke.root.socialapp.R;

public class Dashboard extends AppCompatActivity {

    private Button btnAddSport;
    private Button btnAddBodyHealth;
    private Button btnAddMyBook;
    private Button btnAddOnlineProg;
    private Button btnAddGenArticle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // init views
        initViews();

        // buttons events

        btnAddSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // init sports activity
            }
        });

        btnAddBodyHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, ActivityAddHealth.class));

            }
        });

        btnAddMyBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnAddOnlineProg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, ActivityAddOnlineProg.class);// means that post have no video
                intent.putExtra("type", "add");
                intent.putExtra("childRef", "recipe");
                startActivity(intent);
            }
        });

        btnAddGenArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, ActivityAddGenArticles.class);// means that post have no video
                intent.putExtra("type", "add");
                startActivity(intent);
            }
        });


    }

    private void initViews() {

        btnAddSport = findViewById(R.id.btn_add_sport);
        btnAddBodyHealth = findViewById(R.id.btn_add_health);
        btnAddMyBook = findViewById(R.id.btn_add_book);
        btnAddOnlineProg = findViewById(R.id.btn_add_online_prog);
        btnAddGenArticle = findViewById(R.id.btn_add_gen_article);

    }
}
