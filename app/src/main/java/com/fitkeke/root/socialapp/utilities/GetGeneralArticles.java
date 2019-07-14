package com.fitkeke.root.socialapp.utilities;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.fitkeke.root.socialapp.ArticleItemAdapter;
import com.fitkeke.root.socialapp.modules.ItemArticle;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.LinkedList;

public class GetGeneralArticles {

    DatabaseReference reference;
    LinkedList<ItemArticle> list;
    Activity context;
    RecyclerView.Adapter mAdapter;
    RecyclerView mRecyclerView;

    public GetGeneralArticles(Activity context, RecyclerView mRecyclerView){
        this.reference = FirebaseDatabase.getInstance().getReference().child("articles");
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        this.list = new LinkedList<>();
    }

    public void getAllArticles(final int type){

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.hasChildren()){

                    switch (type){
                        case 0:
                            if (dataSnapshot.child("type").getValue().toString().equals("مقالات عامه")){
                                ItemArticle article = dataSnapshot.getValue(ItemArticle.class);
                                list.add(article);
                            }
                        case 1:
                            // get sports articles
                            if (dataSnapshot.child("type").getValue().toString().equals("وصفات طعام")){
                                ItemArticle article = dataSnapshot.getValue(ItemArticle.class);
                                list.add(article);
                            }
                        case 2:
                            if (dataSnapshot.child("type").getValue().toString().equals("بطولات رياضيه")){
                                ItemArticle article = dataSnapshot.getValue(ItemArticle.class);
                                list.add(article);
                            }
                        case 3:
                            if (dataSnapshot.child("type").getValue().toString().equals("عضلات الجسم")){
                                ItemArticle article = dataSnapshot.getValue(ItemArticle.class);
                                list.add(article);
                            }
                        case 4:
                            if (dataSnapshot.child("type").getValue().toString().equals("مكملات غذائيه")){
                                ItemArticle article = dataSnapshot.getValue(ItemArticle.class);
                                list.add(article);
                            }
                    }

                }

                viewArticle(mRecyclerView);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void viewArticle(RecyclerView recyclerView) {
        Collections.reverse(list);
        mAdapter = new ArticleItemAdapter(list, context);
        recyclerView.setAdapter(mAdapter);
    }


}
