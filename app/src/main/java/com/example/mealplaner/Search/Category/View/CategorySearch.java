package com.example.mealplaner.Search.Category.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.mealplaner.Calendar.CalendarActivity;
import com.example.mealplaner.FavouriteMeals.View.FavouriteMealActivity;
import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.Models.Category;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.Category.InterFaces.CategoryInter;
import com.example.mealplaner.Search.Category.Presenter.CategoryPresenter;
import com.example.mealplaner.Search.FlagSearch.FlagActivity;
import com.example.mealplaner.Search.Ingrediant.View.IngrediantSearch;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class CategorySearch extends AppCompatActivity implements CategoryInter {
    RecyclerView categoryRecyclView;
    CategoryPresenter presenter;
    String userType = "loggedUser";
    BottomNavigationView bottomNavigationView;
    Button btnCountry, btnIngridiant, btnMeals, btnCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_category);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navigationBar();
        categoryRecyclView = findViewById(R.id.rv_country);
        presenter = new CategoryPresenter(this, this);

        btnCategory = findViewById(R.id.btn_category);
        btnMeals = findViewById(R.id.btn_meal);
        btnIngridiant = findViewById(R.id.btn_ingrediant);
        btnCountry = findViewById(R.id.btn_country);

        btnIngridiant.setOnClickListener(view -> {
            Intent intent =new Intent(this, IngrediantSearch.class);
            startActivity(intent);
        });
        btnCountry.setOnClickListener(view -> {
            Intent intent =new Intent(this, FlagActivity.class);
            startActivity(intent);
        });


    }

    @Override
    public void setCategory(ArrayList<Category> categories) {
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
        categoryRecyclView.setLayoutManager(new GridLayoutManager(this, 3));
        categoryRecyclView.setAdapter(categoryAdapter);

    }

    private void navigationBar() {


        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(CategorySearch.this, MainActivity.class);
                        intent.putExtra("checkUserType", userType);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        return true;
                    case R.id.love:
                        if (userType.equals("guest")) {
                            Toast.makeText(CategorySearch.this, "You should login first", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(getApplicationContext(), FavouriteMealActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                    case R.id.calendar:
                        if (userType.equals("guest")) {
                            Toast.makeText(CategorySearch.this, "You should login first", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                }
                return false;
            }
        });
    }
}