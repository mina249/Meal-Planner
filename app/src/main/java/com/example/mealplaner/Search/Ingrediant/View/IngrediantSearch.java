package com.example.mealplaner.Search.Ingrediant.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mealplaner.Calendar.CalendarActivity;
import com.example.mealplaner.FavouriteMeals.View.FavouriteMealActivity;
import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.Models.IngrediantModel;
import com.example.mealplaner.Network.NetworkListener;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.FlagSearch.Presenter.FlagPrester;
import com.example.mealplaner.Search.Ingrediant.InterFaces.IngrediantFilter;
import com.example.mealplaner.Search.Ingrediant.InterFaces.IngrediantSetData;
import com.example.mealplaner.Search.Ingrediant.Presenter.IngrediantPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;


public class IngrediantSearch extends AppCompatActivity implements IngrediantSetData , IngrediantFilter {

    RecyclerView rvIngrediant;
    IngrediantPresenter presenter;
    IngrediantAdapter ingrediantAdapter;
    SearchView ingrediantSearch;
    String userType = "loggedUser";
    BottomNavigationView bottomNavigationView;
    Button btnBack;
    TextView oops;
    TextView noInternet;
    Button retry;
    LottieAnimationView load;
    ImageView wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingrediant_search);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navigationBar();
        rvIngrediant=findViewById(R.id.rv_search_meal);
         ingrediantAdapter = new IngrediantAdapter();
         ingrediantSearch = findViewById(R.id.country_search);
         btnBack=findViewById(R.id.btn_back);
        oops = findViewById(R.id.oops_tv_ingrediant);
        noInternet = findViewById(R.id.noInternet_tv_ingrediant);
        retry = findViewById(R.id.btn_rety_ingrediant);
        wifi = findViewById(R.id.wifi_img_ingrediant);
        load = findViewById(R.id.load_ingrediant);
         ingrediantSearch.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        presenter= new IngrediantPresenter(this,this);
        presenter.setIngrediant();
        rvIngrediant.setLayoutManager(new GridLayoutManager(this,3));
        rvIngrediant.setAdapter(ingrediantAdapter);
        ingrediantSearch.setMaxWidth(700);
        checkNetwork();
        ingrediantSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                presenter.filterIngrediant(s);
                return false;
            }
        });
        btnBack.setOnClickListener(view->{
            this.finish();
        });
    }

    @Override
    public void setIngrediant(ArrayList<IngrediantModel> ingrediantList) {
       ingrediantAdapter.setList(ingrediantList);
        load.setVisibility(View.GONE);

    }

    @Override
    public void filter(ArrayList<IngrediantModel> ingrediantModels) {
        ingrediantAdapter.setList(ingrediantModels);
    }
    private void navigationBar() {


        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(IngrediantSearch.this, MainActivity.class);
                        intent.putExtra("checkUserType", userType);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        return true;
                    case R.id.love:
                        if (userType.equals("guest")) {
                            Toast.makeText(IngrediantSearch.this, "You should login first", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(getApplicationContext(), FavouriteMealActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                    case R.id.calendar:
                        if (userType.equals("guest")) {
                            Toast.makeText(IngrediantSearch.this, "You should login first", Toast.LENGTH_SHORT).show();
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
    private void checkNetwork(){

        if(NetworkListener.getConnectivity(this)){
            noInternet.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            oops.setVisibility(View.GONE);
            retry.setVisibility(View.GONE);
            load.setVisibility(View.VISIBLE);
            rvIngrediant.setVisibility(View.VISIBLE);
            presenter= new IngrediantPresenter(this,this);
            presenter.setIngrediant();
        }else{
            wifi.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.VISIBLE);
            oops.setVisibility(View.VISIBLE);
            retry.setVisibility(View.VISIBLE);
            load.setVisibility(View.GONE);
            rvIngrediant.setVisibility(View.GONE);

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkNetwork();

                }
            });
        }
    }
}