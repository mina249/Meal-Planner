package com.example.mealplaner.OnBoardind.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.OnBoardind.Interface.MoveFragment;
import com.example.mealplaner.Clender.View.Clender;

import com.example.mealplaner.Login.View.LoginActivity;
import com.example.mealplaner.R;
import com.example.mealplaner.SplashScree.View.SplashScreen;


import java.util.ArrayList;

public class OnBoarding extends AppCompatActivity implements MoveFragment {
    private static final String PREFS_NAME ="myPref" ;
    ViewPager2 viewPager ;
    String  [] descriptions;
    ArrayList<OnBoardinfIteam> iteams;
    String [] lotteNames;
    SharedPreferences settings;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        viewPager = findViewById(R.id.viewPager);
         settings = getSharedPreferences(PREFS_NAME, 0);



       if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task
            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        }else{
            startActivity(new Intent(OnBoarding.this, LoginActivity.class));
        }

        descriptions= new String[]{"لو انت عازب \n و بتدور علي طريقة تعمل بيها اكلة سهلة ؟؟",
                "لو انت ست بيت \n و محتارة تطبخي ايه النهاردة ؟؟ ",
                "اهل بيك \n انت هنا في المكان الصح "};
        lotteNames= new String[]{"meals", "wommen", "foodies"};


        iteams =new ArrayList<>();
        for (int i =0 ; i<descriptions.length;i++){
            OnBoardinfIteam pagerIteam = new OnBoardinfIteam(descriptions[i],lotteNames[i]);
            iteams.add(pagerIteam);
        }
        OnBoardingAdapter adapter = new OnBoardingAdapter(iteams,this);
        viewPager.setAdapter(adapter);
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(2);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);





    }

    @Override
    public void move() {
        viewPager.removeAllViewsInLayout();
        Clender clender = new Clender(this);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction =manager.beginTransaction();
        transaction.add(R.id.container, clender, "clender");
       // transaction.commit();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}