package com.example.mealplaner;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class ViewPager extends AppCompatActivity {
    ViewPager2 viewPager ;
    String  [] descriptions;
    ArrayList<ViewPagerIteam> iteams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        viewPager = findViewById(R.id.viewPager);

        descriptions= new String[]{"test 1", "test 2", "test 3", "test 4", "test 5"};
        iteams =new ArrayList<>();
        for (int i =0 ; i<descriptions.length;i++){
            ViewPagerIteam pagerIteam = new ViewPagerIteam(descriptions[i]);
            iteams.add(pagerIteam);
        }
        VPAdapter adapter = new VPAdapter(iteams,this);
        viewPager.setAdapter(adapter);
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(2);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

    }
}