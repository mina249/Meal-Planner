package com.example.mealplaner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    TabLayout loginTabLayout;
    ViewPager loginViewPager;
    FloatingActionButton fb, google, twitter;
    LoginFragmentAdapter loginAdapter;
    float alpha = 0;
    LoginFragment loginFragment;
    SignupFragment signupFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginFragment = new LoginFragment();
        signupFragment = new SignupFragment();
        inflateUi();
        settingTableLayout();
        animateUi();
    }

    public void animateUi() {
        fb.setTranslationY(300);
        google.setTranslationY(300);
        twitter.setTranslationY(300);
        loginTabLayout.setTranslationY(300);
        fb.setAlpha(alpha);
        google.setAlpha(alpha);
        twitter.setAlpha(alpha);
        loginTabLayout.setAlpha(alpha);
        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        loginTabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }

    public void inflateUi() {
        loginTabLayout = findViewById(R.id.tab_layout);
        loginViewPager = findViewById(R.id.view_pager);
        fb = findViewById(R.id.fab_fb);
        google = findViewById(R.id.fab_google);
        twitter = findViewById(R.id.fab_twitter);
    }

    public void settingTableLayout() {

        loginTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        loginAdapter = new LoginFragmentAdapter(getSupportFragmentManager(), this);
        loginAdapter.setData(new LoginFragment(), "Login");
        loginAdapter.setData(new SignupFragment(), "Signup");
        loginTabLayout.setupWithViewPager(loginViewPager);

        loginViewPager.setAdapter(loginAdapter);

    }
}