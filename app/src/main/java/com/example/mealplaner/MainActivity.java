package com.example.mealplaner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mealplaner.Models.Meal;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MealViewInterface {

    RecyclerView recyclerView;
    HomeRvAdapter homeAdapter;
    ArrayList<Meal> meals = new ArrayList<>();
    ViewPager2 homePager;


   MealPresenterInterface mealPresenterInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationBar();
        //recyclerView = findViewById(R.id.rv_main);
        homePager = findViewById(R.id.viewpager_home);
        mealPresenterInterface = new MealPresenter(MealService.getInstance(),this,this);
        mealPresenterInterface.getMeal();

        homePager.setClipToPadding(false);
        homePager.setClipChildren(false);
        homePager.setOffscreenPageLimit(3);
        homePager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);
            }
        });
        homePager.setPageTransformer(compositePageTransformer);







    }
        private void navigationBar(){
            BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.home);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch(item.getItemId())
                    {
                        case R.id.search:
                            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.home:
                            return true;
                        case R.id.love:
                            startActivity(new Intent(getApplicationContext(), LovedActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.calendar:
                            startActivity(new Intent(getApplicationContext(),CalendarActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                    }
                    return false;
                }
            });
        }


    @Override
    public void showData(ArrayList<Meal> meals) {
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        homeAdapter = new HomeRvAdapter(this,meals,homePager);
        homePager.setAdapter(homeAdapter);

       //recyclerView.setLayoutManager(lm);
       // recyclerView.setAdapter(homeAdapter);

        //homeAdapter.setMealList(meals);
        homeAdapter.notifyDataSetChanged();

    }
}
