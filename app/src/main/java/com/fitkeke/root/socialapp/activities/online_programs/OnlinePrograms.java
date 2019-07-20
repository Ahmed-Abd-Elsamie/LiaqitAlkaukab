package com.fitkeke.root.socialapp.activities.online_programs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fitkeke.root.socialapp.R;
import com.fitkeke.root.socialapp.generalVars;
import com.fitkeke.root.socialapp.utilities.GetArticle;

public class OnlinePrograms extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_programs);

        generalVars.post = "onlineProg";

        initViews();
        //..........................................
        GetArticle getArticle = new GetArticle(OnlinePrograms.this, mRecyclerView, "posts");
        getArticle.getAllArticles();


    }


    private void initViews() {
        mRecyclerView = findViewById(R.id.recycler_programs);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(OnlinePrograms.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}
