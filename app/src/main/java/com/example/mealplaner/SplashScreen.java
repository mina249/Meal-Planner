package com.example.mealplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {
    LottieAnimationView splashLottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashLottie = findViewById(R.id.lottieSplash);

        splashLottie.animate().translationX(1400).setDuration(1000).setStartDelay(5700);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 6700);
    }
}