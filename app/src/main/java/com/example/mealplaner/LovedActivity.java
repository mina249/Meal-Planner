package com.example.mealplaner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class LovedActivity extends AppCompatActivity {

    RecyclerView favRV;
    FavoriteAdapter favAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loved);
        navigationBar();

        favRV = findViewById(R.id.fav_rv);
        favAdapter = new FavoriteAdapter(this);
        favRV.setAdapter(favAdapter);


        //favAdapter.setList(meals);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this,2);
        favRV.setLayoutManager(lm);

    }

    private void navigationBar(){
            BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.love);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch(item.getItemId())
                    {
                        case R.id.search:
                            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.love:
                            return true;
                        case R.id.home:
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
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
    }


