package com.fitkeke.root.socialapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fitkeke.root.socialapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDataEntry extends AppCompatActivity {

    private EditText txt_name;
    private EditText txt_age;
    private EditText txt_height;
    private EditText txt_weight;
    private Button btnNext;
    private int n = 0;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private String name;
    private String age;
    private String height;
    private String weight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_data_entry);

        // init views
        txt_name = findViewById(R.id.txt_name);
        txt_age = findViewById(R.id.txt_age);
        txt_height = findViewById(R.id.txt_height);
        txt_weight = findViewById(R.id.txt_weight);
        btnNext = findViewById(R.id.btn_next);



        // init firebase
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("users");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (n==0) {
                    name = txt_name.getText().toString();
                    txt_name.setVisibility(View.GONE);
                    txt_age.setVisibility(View.VISIBLE);
                }else if (n == 1) {
                    age = txt_age.getText().toString();
                    txt_age.setVisibility(View.GONE);
                    txt_height.setVisibility(View.VISIBLE);
                }else if (n == 2) {
                    height = txt_height.getText().toString();
                    txt_height.setVisibility(View.GONE);
                    txt_weight.setVisibility(View.VISIBLE);
                }else if (n == 3){
                    weight = txt_weight.getText().toString();
                    saveData();
                    startActivity(new Intent(UserDataEntry.this, UserProfile.class));
                    finish();
                }
                n++;

            }
        });






    }

    private void saveData() {
        String uid = mAuth.getCurrentUser().getUid();
        reference.child(uid).child("name").setValue(name);
        reference.child(uid).child("age").setValue(age);
        reference.child(uid).child("height").setValue(height);
        reference.child(uid).child("weight").setValue(weight);
    }

}
