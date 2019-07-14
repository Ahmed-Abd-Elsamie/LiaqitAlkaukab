package com.fitkeke.root.socialapp.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fitkeke.root.socialapp.MainActivity;
import com.fitkeke.root.socialapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {

    private Button registerBtn;
    private Button loginBtn;
    private SignInButton googleBtn;
    private LoginButton facebookBtn;
    private EditText txtPassword;
    private EditText txtEmail;
    private FirebaseAuth mAuth;
    private ProgressBar pb;
    private String uid;
    private DatabaseReference reference;
    private GoogleSignInOptions gso;
    private GoogleSignInClient googleSignInClient;
    private int RESULT_CODE_GOOGLE = 9001;
    private CallbackManager callbackManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //printKeyHash();


        // Assign Views

        txtPassword = (EditText) findViewById(R.id.txt_user_password_login);
        txtEmail = (EditText) findViewById(R.id.txt_user_email_login);
        pb = (ProgressBar) findViewById(R.id.prog_login);
        registerBtn = (Button) findViewById(R.id.registerButton);
        loginBtn = (Button) findViewById(R.id.login_button);
        googleBtn = (SignInButton)findViewById(R.id.btn_google);
        facebookBtn = findViewById(R.id.btn_facebook);

        // init firebase

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();


        // google sign in
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("738151235302-bklmkm16vttmti33e4nghl4vktdj548c.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(Login.this, gso);


        // facebook sign in
        callbackManager = CallbackManager.Factory.create();

        facebookBtn.setPermissions("email", "public_profile");
        facebookBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Toast.makeText(LoginActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googlesignin();
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this , Register.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right , R.anim.slide_out_right);
                finish();
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String email = txtEmail.getText().toString().trim();
                final String pass = txtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                pb.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            pb.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Failed Please Check your Data!", Toast.LENGTH_SHORT).show();
                        }else{

                            // Handle Notification

                            uid = mAuth.getCurrentUser().getUid().toString();

                            mAuth.getCurrentUser().getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                                @Override
                                public void onSuccess(GetTokenResult getTokenResult) {

                                    String tokenId = getTokenResult.getToken();
                                    reference.child("users").child(uid).child("token_id").setValue(tokenId).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            pb.setVisibility(View.INVISIBLE);
                                            Intent i = new Intent(Login.this , UserProfile.class);
                                            i.putExtra("type", "other");
                                            startActivity(i);
                                            finish();

                                        }
                                    });

                                }
                            });

                        }
                    }
                });



            }
        });


    }


    private void googlesignin(){
        pb.setVisibility(View.VISIBLE);
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RESULT_CODE_GOOGLE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_CODE_GOOGLE) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                signInFirebase(account);
            } catch (ApiException e) {
                pb.setVisibility(View.INVISIBLE);
            }
        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }


    }


    private void signInFirebase(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        pb.setVisibility(View.INVISIBLE);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // check exist
                    reference.child("users").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            if (dataSnapshot.hasChild(mAuth.getCurrentUser().getUid())){
                                Intent i = new Intent(Login.this , UserProfile.class);
                                i.putExtra("type", "other");
                                startActivity(i);
                                finish();
                            }else {
                                // not registered yet
                                RegisterUser();
                                //start data entry users
                                Intent i = new Intent(Login.this , UserProfile.class);
                                i.putExtra("type", "other");
                                startActivity(i);
                                finish();
                            }
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


                }else {
                    Toast.makeText(getApplicationContext(), "Authentication Failed !", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // check exist
                            reference.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChildren()){
                                        Intent i = new Intent(Login.this , UserProfile.class);
                                        i.putExtra("type", "other");
                                        startActivity(i);
                                        finish();
                                    }else {
                                        // not registered yet
                                        RegisterUser();
                                        //start data entry users
                                        Intent i = new Intent(Login.this , UserProfile.class);
                                        i.putExtra("type", "other");
                                        startActivity(i);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }else {
                            Toast.makeText(getApplicationContext(), "Authentication Failed !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    public void RegisterUser(){

        final String name = mAuth.getCurrentUser().getDisplayName();
        final String email = mAuth.getCurrentUser().getEmail();
        final String UID = mAuth.getCurrentUser().getUid().toString();
        reference.child("users").child(UID).child("name").setValue(name);
        reference.child("users").child(UID).child("email").setValue(email);
        reference.child("users").child(UID).child("id").setValue(UID);
        reference.child("users").child(UID).child("img").setValue("default");
        reference.child("users").child(UID).child("age").setValue("default");
        reference.child("users").child(UID).child("height").setValue("default");
        reference.child("users").child(UID).child("weight").setValue("default");



    }

    /*Z3Fs8amI3FZhUaoItKhu8LAB*/


    private void printKeyHash() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.fitkeke.root.socialapp", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash:", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", e.toString());
        }
    }
}

/*

738151235302-bklmkm16vttmti33e4nghl4vktdj548c.apps.googleusercontent.com

*/