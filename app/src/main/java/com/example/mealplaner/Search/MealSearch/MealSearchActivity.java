package com.example.mealplaner.Search.MealSearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealplaner.Calendar.CalendarActivity;
import com.example.mealplaner.Category.View.CategoryMealAdapter;
import com.example.mealplaner.FavouriteMeals.View.FavouriteMealActivity;
import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.Login.View.LoginActivity;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MealSearchActivity extends AppCompatActivity implements SearchforMeal {
    SearchView mealSearch;
    RecyclerView rvMeals;
    CategoryMealAdapter mealAdapter;
    MealsPresenter mealsPresenter;
    String userType = "loggedUser";
    BottomNavigationView bottomNavigationView;
    Button btnBack;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_search);
        mealSearch = findViewById(R.id.country_search);
        rvMeals = findViewById(R.id.rv_search_meal);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        mealAdapter = new CategoryMealAdapter(new ArrayList<>());
        rvMeals.setLayoutManager(new LinearLayoutManager(this));
        rvMeals.setAdapter(mealAdapter);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        btnBack=findViewById(R.id.btn_back);
        navigationBar();
        mealsPresenter = new MealsPresenter(this);

        mealSearch.setMaxWidth(700);
        mealSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() > 0)
                    mealsPresenter.getMeals(s);


                return false;
            }
        });
        btnBack.setOnClickListener(view->{
            this.finish();
        });
    }

    @Override
    public void search(ArrayList<Meal> meals) {
        mealAdapter.setMeals(meals);
    }
    private void navigationBar() {


        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(MealSearchActivity.this, MainActivity.class);
                        intent.putExtra("checkUserType", userType);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        return true;
                    case R.id.love:
                        if (user==null) {
                            showConfirmationDialog();
                        } else {
                            startActivity(new Intent(getApplicationContext(), FavouriteMealActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                    case R.id.calendar:
                        if (user==null) {
                         showConfirmationDialog();
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
    private void showConfirmationDialog() {
        AlertDialog builder = new AlertDialog.Builder(this).create();
        ViewGroup viewGroup = new LinearLayout(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.delete_iteam, viewGroup,false);
        Button registerAsGest =view.findViewById(R.id.btn_delete_Meal);
        TextView tvConfirmation = view.findViewById(R.id.tv_confirmation);
        tvConfirmation.setText(getString(R.string.message_for_login));

        registerAsGest.setText("Login");
        registerAsGest.setOnClickListener(view1 -> {
            startActivity(new Intent(MealSearchActivity.this, LoginActivity.class));
            finish();
            builder.dismiss();

        });
        Button btnCancle =view.findViewById(R.id.btn_cancle);
        btnCancle.setOnClickListener(view1 -> {
            builder.dismiss();
        });

        builder.setView(view);
        builder.show();

    }
}