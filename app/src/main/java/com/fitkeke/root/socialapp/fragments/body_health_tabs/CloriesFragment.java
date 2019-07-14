package com.fitkeke.root.socialapp.fragments.body_health_tabs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitkeke.root.socialapp.R;
import com.fitkeke.root.socialapp.utilities.GetArticle;
import java.util.List;

public class CloriesFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    public static List<String> keys;
    private View view;
    private Activity context;



    public CloriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calori, container, false);
        context = getActivity();
        initViews(view);
        //..........................................
        GetArticle getArticle = new GetArticle(context, mRecyclerView, "calories");
        getArticle.getAllArticles();

        return view;
    }




    private void initViews(View v) {
        mRecyclerView = v.findViewById(R.id.main_screen_recycler4);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

}