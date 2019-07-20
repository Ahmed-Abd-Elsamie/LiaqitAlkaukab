package com.fitkeke.root.socialapp.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fitkeke.root.socialapp.R;

public class ActivityAddHealth extends AppCompatActivity {

    private Button btnAddRecipe;
    private Button btnAddArticle;
    private Button btnAddSupp;
    private Button btnAddCaloriy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_health);

        // init views
        initViews();


        // buttons events

        btnAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAddHealth.this, ActivityAddPost.class);// means that post have no video
                intent.putExtra("type", "add");
                intent.putExtra("childRef", "recipe");
                startActivity(intent);
            }
        });

        btnAddArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAddHealth.this, ActivityAddPost.class);// means that post have no video
                intent.putExtra("type", "add");
                intent.putExtra("childRef", "articles");
                startActivity(intent);
            }
        });

        btnAddSupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAddHealth.this, ActivityAddPost.class);// means that post have no video
                intent.putExtra("type", "add");
                intent.putExtra("childRef", "supp");
                startActivity(intent);
            }
        });

        btnAddCaloriy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAddHealth.this, ActivityAddPost.class);// means that post have no video
                intent.putExtra("type", "add");
                intent.putExtra("childRef", "calories");
                startActivity(intent);
            }
        });
    }

    private void initViews() {

        btnAddRecipe = findViewById(R.id.btn_add_rcecipe);
        btnAddArticle = findViewById(R.id.btn_add_articles);
        btnAddSupp = findViewById(R.id.btn_add_supp);
        btnAddCaloriy = findViewById(R.id.btn_add_caloriy);


    }

}