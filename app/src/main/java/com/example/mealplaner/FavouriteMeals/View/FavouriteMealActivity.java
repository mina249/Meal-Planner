package com.example.mealplaner.FavouriteMeals.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.mealplaner.Calendar.CalendarActivity;
import com.example.mealplaner.DataBase.ConcreteLocalSource;
import com.example.mealplaner.FavouriteMeals.Presenter.FavouriteMealPresenter;
import com.example.mealplaner.FavouriteMeals.Intercafaces.FavouriteMealPresenterInterface;
import com.example.mealplaner.FavouriteMeals.Intercafaces.FavouriteViewInterface;
import com.example.mealplaner.HomePage.Interfaces.MealPresenterInterface;
import com.example.mealplaner.HomePage.Interfaces.MealViewInterface;
import com.example.mealplaner.HomePage.Interfaces.OnAddToFavouriteClickListener;
import com.example.mealplaner.HomePage.Presenter.MealPresenter;
import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.FavouriteMeals.Intercafaces.OnDeleteFromFavClickListener;
import com.example.mealplaner.Network.FireBaseData;
import com.example.mealplaner.Network.MealService;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.HomeSearchPage.SearchActivity;
import com.example.mealplaner.Search.MealSearch.MealSearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavouriteMealActivity extends AppCompatActivity implements FavouriteViewInterface, OnDeleteFromFavClickListener, OnAddToFavouriteClickListener, MealViewInterface {

    RecyclerView favRV;
    FavoriteAdapter favAdapter;
    FavouriteMealPresenterInterface favouriteMealPresenterInterface;
    MealPresenterInterface mealPresenterInterface;
    FloatingActionButton btnAddFromFav;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loved);
        navigationBar();
        auth = FirebaseAuth.getInstance();
        mealPresenterInterface = new MealPresenter(MealService.getInstance(), this, this, ConcreteLocalSource.getInstance(this));
        favRV = findViewById(R.id.fav_rv);
        favAdapter = new FavoriteAdapter(this,new ArrayList<>(),this,this);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this,2);
        favouriteMealPresenterInterface = new FavouriteMealPresenter(this, ConcreteLocalSource.getInstance(this));
        favRV.setLayoutManager(lm);
        favRV.setAdapter(favAdapter);
        favouriteMealPresenterInterface.getFavouriteMeals();
        btnAddFromFav=findViewById(R.id.btn_add_from_fav);
        btnAddFromFav.setOnClickListener(view->{
            Intent intent = new Intent(this, MealSearchActivity.class);
            startActivity(intent);
        });
        FireBaseData.getFavouriteFromFirebase(this,auth.getCurrentUser());


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
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.calendar:
                            startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                    }
                    return false;
                }
            });
        }

    @Override
    public void remove(Meal meal) {
        favouriteMealPresenterInterface.removeFav(meal);
    }

    @SuppressLint("CheckResult")
    @Override
    public void showFav(Observable<List<Meal>> meal) {
        meal.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o->favAdapter.setList(o));

        favAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDeleteClick(Meal meal) {
        remove(meal);
    }

    @Override
    public void showData(ArrayList<Meal> meals) {

    }

    @Override
    public void addToFav(Meal meal) {
        mealPresenterInterface.addToFav(meal);
    }

    @Override
    public void showRecommendedMeals(ArrayList<Meal> recommendedMeals) {

    }

    @Override
    public void onClick(Meal meal) {
            addToFav(meal);
    }
}


