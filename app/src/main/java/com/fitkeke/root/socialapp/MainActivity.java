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
import com.fitkeke.root.socialapp.activities.Login;
import com.fitkeke.root.socialapp.activities.UserProfile;
import com.fitkeke.root.socialapp.activities.body_health.BodyHealth;
import com.fitkeke.root.socialapp.activities.general_articles.GeneralArticles;
import com.fitkeke.root.socialapp.activities.online_programs.OnlinePrograms;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.onesignal.OneSignal;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private String uid;
    private FirebaseUser user;
    private LinearLayout btnSports;
    private LinearLayout btnHealth;
    private LinearLayout btnMyBook;
    private LinearLayout btnFollow;
    private LinearLayout btnOnline;
    private LinearLayout btnArticles;
    private CircleImageView btnProfile;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AIzaSyChK8O1C74hBSmy_ATuIoChSymfqBCEdf8";
    final private String contentType = "application/json";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OneSignal.startInit(this).init();
        OneSignal.sendTag("all_users", "all_users");

        // init views
        initViews();

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

        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View view = inflater.inflate(R.layout.follow_captin, null);

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



                builder.setView(view);
                builder.show();
            }
        });

        btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OnlinePrograms.class));
            }
        });

        btnArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GeneralArticles.class));
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth = FirebaseAuth.getInstance();
                if(mAuth.getCurrentUser() == null){
                    startActivity(new Intent(MainActivity.this, Login.class));
                }else {
                    Intent intent = new Intent(MainActivity.this, UserProfile.class);
                    intent.putExtra("type", "other");
                    startActivity(intent);
                }


            }
        });

        // init firebase
        mAuth = FirebaseAuth.getInstance();

    }

    private void initViews() {

        btnSports = findViewById(R.id.btn_sports_train);
        btnHealth = findViewById(R.id.btn_body_health);
        btnMyBook = findViewById(R.id.btn_my_book);
        btnFollow = findViewById(R.id.btn_follow);
        btnOnline = findViewById(R.id.btn_online);
        btnArticles = findViewById(R.id.btn_articles);
        btnProfile = findViewById(R.id.fab_profile);


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