package com.example.mealplaner.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mealplaner.DataBase.ConcreteLocalSource;
import com.example.mealplaner.FavouriteMeals.Intercafaces.OnDeleteFromFavClickListener;
import com.example.mealplaner.FavouriteMeals.View.FavouriteMealActivity;
import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.Category.View.CategorySearch;
import com.example.mealplaner.Search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import io.reactivex.Observable;

public class CalendarActivity extends AppCompatActivity implements CalendarViewInterface, OnDeleteFromFavClickListener {


    CalendarPresenterInterface calendarPresenterInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
      calendarPresenterInterface = new CalendarPresenter(this, ConcreteLocalSource.getInstance(this));
        calendarPresenterInterface.getSaturdayMeals();
        calendarPresenterInterface.getSundayMeals();
        calendarPresenterInterface.getMondayMeals();
        calendarPresenterInterface.getTuesDayMeals();
        calendarPresenterInterface.getWednesdayMeals();
        calendarPresenterInterface.getThursdayMeals();
        calendarPresenterInterface.getFridayMeals();
    }

    private void navigationBar(){
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

    @Override
    public void showSaturday(Observable<List<Meal>> products) {

    }

    @Override
    public void showSunday(Observable<List<Meal>> products) {

    }

    @Override
    public void showMonday(Observable<List<Meal>> products) {

    }

    @Override
    public void showTuesday(Observable<List<Meal>> products) {

    }

    @Override
    public void showWednesday(Observable<List<Meal>> products) {

    }

    @Override
    public void showThursday(Observable<List<Meal>> products) {

    }

    @Override
    public void showFriday(Observable<List<Meal>> products) {

    }

    @Override
    public void remove(Meal meal) {
        calendarPresenterInterface.removeFromCalender(meal);
    }

    @Override
    public void onDeleteClick(Meal meal) {
        remove(meal);
    }
}


