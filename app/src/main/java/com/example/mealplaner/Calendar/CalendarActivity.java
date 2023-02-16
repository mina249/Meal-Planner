package com.example.mealplaner.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mealplaner.Calendar.CalendarPresenter;
import com.example.mealplaner.Calendar.CalendarPresenterInterface;
import com.example.mealplaner.Calendar.CalendarViewInterface;
import com.example.mealplaner.Calendar.ClenderAdapter;
import com.example.mealplaner.DataBase.ConcreteLocalSource;
import com.example.mealplaner.FavouriteMeals.Intercafaces.OnDeleteFromFavClickListener;
import com.example.mealplaner.FavouriteMeals.View.FavouriteMealActivity;
import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.Category.View.CategorySearch;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CalendarActivity extends AppCompatActivity implements CalendarViewInterface, OnDeleteFromFavClickListener {
    RecyclerView rvSunday,rvMonday,rvTuesday,rvThurasday,rvWednesday,rvFriday,rvSatrday;
    ClenderAdapter sundayAdapter,mondayAdapter,tuesdayAdapter,thurasdayAdapter,
            wednesdayAdapter,fridayAdapter,satrdayAdapter;

    CalendarPresenterInterface calendarPresenterInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        rvSunday=findViewById(R.id.rv_sunday);
        rvMonday=findViewById(R.id.rv_monday);
        rvTuesday=findViewById(R.id.rv_tuesday);
        rvThurasday=findViewById(R.id.rv_tharsday);
        rvWednesday=findViewById(R.id.rv_wednesday);
        rvFriday=findViewById(R.id.rv_friday);
        rvSatrday=findViewById(R.id.rv_satarday);

        sundayAdapter=new ClenderAdapter(this);
        mondayAdapter=new ClenderAdapter(this);
        tuesdayAdapter=new ClenderAdapter(this);
        thurasdayAdapter=new ClenderAdapter(this);
        wednesdayAdapter=new ClenderAdapter(this);
        fridayAdapter=new ClenderAdapter(this);
        satrdayAdapter=new ClenderAdapter(this);

        rvSunday.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        rvSunday.setAdapter(sundayAdapter);

        rvMonday.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        rvMonday.setAdapter(mondayAdapter);

        rvFriday.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        rvFriday.setAdapter(fridayAdapter);

        rvThurasday.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        rvThurasday.setAdapter(thurasdayAdapter);

        rvTuesday.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        rvTuesday.setAdapter(tuesdayAdapter);

        rvWednesday.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        rvWednesday.setAdapter(wednesdayAdapter);

        rvSatrday.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        rvSatrday.setAdapter(satrdayAdapter);





      calendarPresenterInterface = new CalendarPresenter(this, ConcreteLocalSource.getInstance(this));
        calendarPresenterInterface.getSaturdayMeals();
        calendarPresenterInterface.getSundayMeals();
        calendarPresenterInterface.getMondayMeals();
        calendarPresenterInterface.getTuesDayMeals();
        calendarPresenterInterface.getWednesdayMeals();
        calendarPresenterInterface.getThursdayMeals();
        calendarPresenterInterface.getFridayMeals();
        navigationBar();
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
        products.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> {satrdayAdapter.setMealList((ArrayList<Meal>) meals);});
    }

    @Override
    public void showSunday(Observable<List<Meal>> products) {
        products.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> {sundayAdapter.setMealList((ArrayList<Meal>) meals);});
    }

    @Override
    public void showMonday(Observable<List<Meal>> products) {
        products.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> {mondayAdapter.setMealList((ArrayList<Meal>) meals);});
    }

    @Override
    public void showTuesday(Observable<List<Meal>> products) {
        products.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> {tuesdayAdapter.setMealList((ArrayList<Meal>) meals);});
    }

    @Override
    public void showWednesday(Observable<List<Meal>> products) {
        products.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> {wednesdayAdapter.setMealList((ArrayList<Meal>) meals);});
    }

    @Override
    public void showThursday(Observable<List<Meal>> products) {
        products.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> {thurasdayAdapter.setMealList((ArrayList<Meal>) meals);});
    }

    @Override
    public void showFriday(Observable<List<Meal>> products) {
        products.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> {fridayAdapter.setMealList((ArrayList<Meal>) meals);});
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


