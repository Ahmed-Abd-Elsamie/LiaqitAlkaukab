package com.fitkeke.root.socialapp.utilities;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.fitkeke.root.socialapp.ArticleItemAdapter;
import com.fitkeke.root.socialapp.ArticleItemAdapterCalories;
import com.fitkeke.root.socialapp.modules.ItemArticle;
import com.fitkeke.root.socialapp.modules.ItemArticleCalories;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.LinkedList;

public class GetArticle {

    DatabaseReference reference;
    LinkedList list;
    Activity context;
    RecyclerView.Adapter mAdapter;
    RecyclerView mRecyclerView;
    String childRef;
    int type;

    public GetArticle(Activity context, RecyclerView mRecyclerView, String childRef){
        this.reference = FirebaseDatabase.getInstance().getReference();
        if (childRef.equals("calories")){
            this.reference = reference.child("body_health").child(childRef);
            list = new LinkedList<ItemArticleCalories>();
        }else if (childRef.equals("posts")){
            // for online programs activity
            this.reference = reference.child("posts");
            list = new LinkedList<ItemArticleCalories>();
        }else {
            this.reference = reference.child("body_health").child(childRef);
            list = new LinkedList<ItemArticle>();
        }
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        this.childRef = childRef;
    }

    public void getAllArticles(){

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.hasChildren()){
                    if (childRef.equals("calories")){
                        ItemArticleCalories article = dataSnapshot.getValue(ItemArticleCalories.class);
                        list.add(article);
                        type = 1;
                    }else if(childRef.equals("posts")){
                        ItemArticleCalories article = dataSnapshot.getValue(ItemArticleCalories.class);
                        list.add(article);
                        type = 1;
                    }else {
                        ItemArticle article = dataSnapshot.getValue(ItemArticle.class);
                        list.add(article);
                        type = 0;
                    }

                }

                viewArticle(mRecyclerView, type);
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

    private void viewArticle(RecyclerView recyclerView, int type) {
        Collections.reverse(list);
        if (type == 1){
            mAdapter = new ArticleItemAdapterCalories(list, context);
        }else {
            mAdapter = new ArticleItemAdapter(list, context);
        }
        recyclerView.setAdapter(mAdapter);
    }


}