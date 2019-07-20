package com.fitkeke.root.socialapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fitkeke.root.socialapp.activities.AddFoodDay;
import com.fitkeke.root.socialapp.activities.FoodProgramActivity;
import com.fitkeke.root.socialapp.activities.Login;
import com.fitkeke.root.socialapp.activities.UserProfile;
import com.fitkeke.root.socialapp.activities.WaterProgramActivity;
import com.fitkeke.root.socialapp.activities.body_health.BodyHealth;
import com.fitkeke.root.socialapp.activities.general_articles.GeneralArticles;
import com.fitkeke.root.socialapp.activities.online_programs.OnlinePrograms;
import com.fitkeke.root.socialapp.admin.Dashboard;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private String uid;
    private FirebaseUser user;

    private LinearLayout btnFollow;

    private LinearLayout btnMainMenu;
    private CircleImageView btnProfile;
    private FloatingActionButton btnDashboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OneSignal.startInit(this).init();
        OneSignal.sendTag("all_users", "all_users");

        // init views
        initViews();


        // hide for users
        btnDashboard.setClickable(false);
        btnDashboard.setVisibility(View.GONE);



        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View view = inflater.inflate(R.layout.follow_captin, null);
                builder.setView(view);
                ImageView inst = view.findViewById(R.id.btnInst);
                ImageView face = view.findViewById(R.id.btnFace);
                ImageView whats = view.findViewById(R.id.btnWhats);
                ImageView youtube = view.findViewById(R.id.btnYoutube);
                ImageView tweet = view.findViewById(R.id.btnTweet);

                inst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/fit_keke/")));
                    }
                });

                face.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/fit.keke")));
                    }
                });
                whats.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String contactNum = "+201225056743";
                        String url = "https://api.whatsapp.com/send?phone=" + contactNum;
                        try {
                            PackageManager pm = MainActivity.this.getPackageManager();
                            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);

                        }catch (Exception e){

                        }

                    }
                });
                youtube.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/user/POMBULK?sub_confirmation=1")));
                    }
                });
                tweet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/legend0777?s=09")));
                    }
                });




                builder.show();
            }
        });



        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*mAuth = FirebaseAuth.getInstance();
                if(mAuth.getCurrentUser() == null){
                    startActivity(new Intent(MainActivity.this, Login.class));
                }else {
                    Intent intent = new Intent(MainActivity.this, UserProfile.class);
                    intent.putExtra("type", "other");
                    startActivity(intent);
                }*/
                Intent intent = new Intent(MainActivity.this, UserProfile.class);
                intent.putExtra("type", "other");
                startActivity(intent);


            }
        });


        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View view = inflater.inflate(R.layout.main_menu_dialog, null);
                builder.setView(view);
                LinearLayout btnSports = view.findViewById(R.id.btn_sports_train);
                LinearLayout btnHealth = view.findViewById(R.id.btn_body_health);
                LinearLayout btnMyBook = view.findViewById(R.id.btn_my_book);
                LinearLayout btnOnlinebtnOnline = view.findViewById(R.id.btn_online);
                LinearLayout btnArticlbtnOnline = view.findViewById(R.id.btn_articles);
                LinearLayout btnFoodProg = view.findViewById(R.id.btn_food_prog);
                LinearLayout btnWaterProg = view.findViewById(R.id.btn_water_prog);


                btnSports.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                        View view = inflater.inflate(R.layout.comming_soon_dialog, null);
                        builder.setView(view);
                        builder.show();
                    }
                });

                btnHealth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, BodyHealth.class));
                        generalVars.post = "health_recipe";
                    }
                });

                btnMyBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                        View view = inflater.inflate(R.layout.comming_soon_dialog, null);
                        builder.setView(view);
                        builder.show();
                    }
                });

                btnOnlinebtnOnline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, OnlinePrograms.class));
                        generalVars.post = "onlineProg";
                    }
                });

                btnArticlbtnOnline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, GeneralArticles.class));
                        generalVars.post = "genArticles";
                    }
                });


                btnFoodProg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, FoodProgramActivity.class);
                        i.putExtra("type","other");
                        startActivity(i);
                    }
                });

                btnWaterProg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, WaterProgramActivity.class));
                    }
                });


                builder.show();

            }
        });

        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Dashboard.class));
            }
        });

        // init firebase
        mAuth = FirebaseAuth.getInstance();

    }

    private void tempFUNCT() {

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String key = snapshot.getRef().getKey();
                    reference.child(key).child("key").setValue(key);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("body_health").child("articles");
        final DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("body_health").child("supp");
        final DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference().child("body_health").child("calories");
        final DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference().child("body_health").child("recipe");

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String key = snapshot.getRef().getKey();
                    reference1.child(key).child("key").setValue(key);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String key = snapshot.getRef().getKey();
                    reference2.child(key).child("key").setValue(key);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String key = snapshot.getRef().getKey();
                    reference3.child(key).child("key").setValue(key);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        reference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String key = snapshot.getRef().getKey();
                    reference4.child(key).child("key").setValue(key);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final DatabaseReference referenceX = FirebaseDatabase.getInstance().getReference().child("articles");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String key = snapshot.getRef().getKey();
                    referenceX.child(key).child("key").setValue(key);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void initViews() {


        btnFollow = findViewById(R.id.btn_follow);
        btnProfile = findViewById(R.id.fab_profile);
        btnMainMenu = findViewById(R.id.btn_main_menu);
        btnDashboard = findViewById(R.id.dashboard);


    }


    @Override
    protected void onStart() {
        super.onStart();

        /*user = mAuth.getCurrentUser();
        if(user == null) {
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }else {
            if (user.getEmail().equals("ahmed@g.com")){
                btnDashBoard.setVisibility(View.VISIBLE);
                btnDashBoard.setClickable(true);
                btnDashBoard.setEnabled(true);
                btnDashBoard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, MainActivity.class));

                    }
                });

            }else{
                btnDashBoard.setVisibility(View.INVISIBLE);
                btnDashBoard.setClickable(false);
                btnDashBoard.setEnabled(false);
            }

            uid = mAuth.getCurrentUser().getUid().toString();
            reference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        */
    }
}