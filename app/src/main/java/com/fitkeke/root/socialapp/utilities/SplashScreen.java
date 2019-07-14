package com.fitkeke.root.socialapp.utilities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.fitkeke.root.socialapp.MainActivity;
import com.fitkeke.root.socialapp.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        final TextView txtSplash = findViewById(R.id.txt_splash);
        final Animation animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.fade_in);
        Animation animation2 = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.fade_in);

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtSplash.clearAnimation();
                txtSplash.startAnimation(animation);
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }.start();
    }
}
