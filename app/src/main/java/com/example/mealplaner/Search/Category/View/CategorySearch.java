package com.example.mealplaner.Search.Category.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mealplaner.Calendar.CalendarActivity;
import com.example.mealplaner.DataBase.ConcreteLocalSource;
import com.example.mealplaner.FavouriteMeals.View.FavouriteMealActivity;
import com.example.mealplaner.HomePage.Presenter.MealPresenter;
import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.Login.View.LoginActivity;
import com.example.mealplaner.Models.Category;
import com.example.mealplaner.Network.MealService;
import com.example.mealplaner.Network.NetworkListener;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.Category.InterFaces.CategoryInter;
import com.example.mealplaner.Search.Category.InterFaces.FilterCategory;
import com.example.mealplaner.Search.Category.Presenter.CategoryPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;

public class CategorySearch extends AppCompatActivity implements CategoryInter, FilterCategory {
    RecyclerView categoryRecyclView;
    CategoryPresenter presenter;
    String userType = "loggedUser";
    BottomNavigationView bottomNavigationView;
    SearchView categorySearch;
    CategoryAdapter categoryAdapter;
    Button btnBack;
    TextView oops;
    TextView noInternet;
    Button retry;
    LottieAnimationView load;
    ImageView wifi;
    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_category);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navigationBar();
        btnBack=findViewById(R.id.btn_back);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        categoryRecyclView = findViewById(R.id.rv_search_meal);
        oops = findViewById(R.id.oops_tv_category);
        noInternet = findViewById(R.id.noInternet_tv_category);
        retry = findViewById(R.id.btn_rety_category);
        wifi = findViewById(R.id.wifi_img_category);
        load = findViewById(R.id.load_cat);
        categorySearch=findViewById(R.id.country_search);
        categorySearch.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        categorySearch.setMaxWidth(700);
        checkNetwork();
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
        load.setVisibility(View.GONE);

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

    @Override
    public void filter(ArrayList<Category> categories) {
        categoryAdapter = new CategoryAdapter(categories);
        categoryRecyclView.setLayoutManager(new GridLayoutManager(this, 3));
        categoryRecyclView.setAdapter(categoryAdapter);
    }
    private void checkNetwork(){

        if(NetworkListener.getConnectivity(this)){
            noInternet.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            oops.setVisibility(View.GONE);
            retry.setVisibility(View.GONE);
            load.setVisibility(View.VISIBLE);
            categoryRecyclView.setVisibility(View.VISIBLE);
            presenter = new CategoryPresenter(this, this);
        }else{
            wifi.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.VISIBLE);
            oops.setVisibility(View.VISIBLE);
            retry.setVisibility(View.VISIBLE);
            load.setVisibility(View.GONE);
            categoryRecyclView.setVisibility(View.GONE);

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkNetwork();

                }
            });
        }
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
            startActivity(new Intent(CategorySearch.this, LoginActivity.class));
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