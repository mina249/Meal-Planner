package com.example.mealplaner.OnBoardind.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mealplaner.OnBoardind.Interface.MoveFragment;
import com.example.mealplaner.Clender.View.Clender;

import com.example.mealplaner.Login.View.LoginActivity;
import com.example.mealplaner.R;


import java.util.ArrayList;

public class OnBoarding extends AppCompatActivity implements MoveFragment {
    ViewPager2 viewPager ;
    String  [] descriptions;
    ArrayList<OnBoardinfIteam> iteams;
    String [] lotteNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        viewPager = findViewById(R.id.viewPager);

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