package com.example.mealplaner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mealplaner.FavouriteMeals.View.FavouriteMealActivity;
import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.Search.Category.View.CategorySearch;
import com.example.mealplaner.Search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CalendarActivity extends AppCompatActivity {
    RecyclerView rvSunday,rvMonday,rvTuesday,rvThurasday,rvWednesday,rvFriday,rvSatrday;
    ClenderAdapter sundayAdapter,mondayAdapter,tuesdayAdapter
            ,thurasdayAdapter,wednesdayAdapter,fridayAdapter,satrdayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rvSunday=findViewById(R.id.rv_sunday);
        rvMonday=findViewById(R.id.rv_monday);
        rvTuesday=findViewById(R.id.rv_tuesday);
        rvThurasday=findViewById(R.id.rv_tharsday);
        rvWednesday=findViewById(R.id.rv_wednesday);
        rvFriday=findViewById(R.id.rv_friday);
        rvSatrday=findViewById(R.id.rv_satarday);

        sundayAdapter=new ClenderAdapter();
        mondayAdapter=new ClenderAdapter();
        tuesdayAdapter=new ClenderAdapter();
        thurasdayAdapter=new ClenderAdapter();
        wednesdayAdapter=new ClenderAdapter();
        fridayAdapter=new ClenderAdapter();
        satrdayAdapter=new ClenderAdapter();


        setContentView(R.layout.activity_calendar);
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.calendar);

        // Perform item selected listener

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.calendar:
                        return true;
                    case R.id.love:
                        startActivity(new Intent(getApplicationContext(), FavouriteMealActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), CategorySearch.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}


