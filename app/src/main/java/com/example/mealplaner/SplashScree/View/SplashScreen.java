package com.example.mealplaner.SplashScree.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mealplaner.Login.View.LoginActivity;
import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.OnBoardind.View.OnBoarding;
import com.example.mealplaner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    LottieAnimationView splashLottie;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashLottie = findViewById(R.id.lottieSplash);
        firebaseAuth = FirebaseAuth.getInstance();


        splashLottie.animate().translationX(1400).setDuration(1000).setStartDelay(5700);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUserStatus();
                finish();
            }
        }, 6700);
        Configuration configuration = new Configuration();
        int currentNightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
               AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }


    }
    private void checkUserStatus(){

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){
            startActivity(new Intent(SplashScreen.this, MainActivity.class));

        }else{
            startActivity(new Intent(SplashScreen.this, OnBoarding.class));
            finish();
        }
    }

}