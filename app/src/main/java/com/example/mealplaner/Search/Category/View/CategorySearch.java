package com.example.mealplaner.Search.Category.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mealplaner.Calendar.CalendarActivity;
import com.example.mealplaner.FavouriteMeals.View.FavouriteMealActivity;
import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.Models.Category;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.Category.InterFaces.CategoryInter;
import com.example.mealplaner.Search.Category.InterFaces.FilterCategory;
import com.example.mealplaner.Search.Category.Presenter.CategoryPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;

public class CategorySearch extends AppCompatActivity implements CategoryInter, FilterCategory {
    RecyclerView categoryRecyclView;
    CategoryPresenter presenter;
    String userType = "loggedUser";
    BottomNavigationView bottomNavigationView;
    SearchView categorySearch;
    CategoryAdapter categoryAdapter;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_category);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navigationBar();
        btnBack=findViewById(R.id.btn_back);
        categoryRecyclView = findViewById(R.id.rv_search_meal);
        categorySearch=findViewById(R.id.country_search);
        categorySearch.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        presenter = new CategoryPresenter(this, this);
        categorySearch.setMaxWidth(700);
        categorySearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
            presenter.filterList(s);
                return false;
            }
        });
        btnBack.setOnClickListener(view->{
            this.finish();
        });




    }

    @Override
    public void setCategory(ArrayList<Category> categories) {
         categoryAdapter = new CategoryAdapter(categories);
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

    @Override
    public void filter(ArrayList<Category> categories) {
        categoryAdapter = new CategoryAdapter(categories);
        categoryRecyclView.setLayoutManager(new GridLayoutManager(this, 3));
        categoryRecyclView.setAdapter(categoryAdapter);
    }
}