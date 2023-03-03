package com.example.mealplaner.Search.HomeSearchPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealplaner.Calendar.CalendarActivity;
import com.example.mealplaner.FavouriteMeals.View.FavouriteMealActivity;
import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.Login.View.LoginActivity;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.Category.View.CategorySearch;
import com.example.mealplaner.Search.FlagSearch.View.FlagActivity;
import com.example.mealplaner.Search.Ingrediant.View.IngrediantSearch;
import com.example.mealplaner.Search.MealSearch.MealSearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SearchActivity extends AppCompatActivity {
    Button btnCategory,btnCountry,btnIngridiant,btnMeals;
String userType = "loggedUser";
    BottomNavigationView bottomNavigationView;

    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        setContentView(R.layout.activity_search);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        navigationBar();
        btnCountry=findViewById(R.id.btn_country);
        btnIngridiant=findViewById(R.id.btn_ingrediant);
        btnCategory=findViewById(R.id.btn_category);
        btnMeals=findViewById(R.id.btn_meal);

        userType = getIntent().getStringExtra("checkUserType");
        if (userType == null){
            userType = "loggedUser";
        }
        btnIngridiant.setOnClickListener(view -> {
            Intent intent =new Intent(this, IngrediantSearch.class);
            startActivity(intent);
        });
        btnCountry.setOnClickListener(view -> {
            Intent intent =new Intent(this, FlagActivity.class);
            startActivity(intent);
        });
        btnCategory.setOnClickListener(view -> {
            Intent intent =new Intent(this, CategorySearch.class);
            startActivity(intent);
        });
        btnMeals.setOnClickListener(view -> {
            Intent intent =new Intent(this, MealSearchActivity.class);
            startActivity(intent);
        });

    }


    private void navigationBar(){



        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                      Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                      intent.putExtra("checkUserType",userType);
                      startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        return true;
                    case R.id.love:
                        if(user==null){
                          showConfirmationDialog();
                        }else  {
                            startActivity(new Intent(getApplicationContext(), FavouriteMealActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                    case R.id.calendar:
                        if (user==null){
                           showConfirmationDialog();
                        }else {
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
            startActivity(new Intent(SearchActivity.this, LoginActivity.class));
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
