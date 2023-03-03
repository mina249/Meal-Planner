package com.example.mealplaner.Search.FlagSearch.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import com.airbnb.lottie.LottieAnimationView;
import com.example.mealplaner.Calendar.CalendarActivity;
import com.example.mealplaner.FavouriteMeals.View.FavouriteMealActivity;
import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.Login.View.LoginActivity;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Network.NetworkListener;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.Category.Presenter.CategoryPresenter;
import com.example.mealplaner.Search.FlagSearch.Interfaces.FilterCountries;
import com.example.mealplaner.Search.FlagSearch.Interfaces.FlagInterf;
import com.example.mealplaner.Search.FlagSearch.Presenter.FlagPrester;
import com.example.mealplaner.Search.HomeSearchPage.SearchActivity;
import com.example.mealplaner.Search.Ingrediant.View.IngrediantSearch;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;

public class FlagActivity extends AppCompatActivity implements FlagInterf , FilterCountries {
    FlagAdapter flagAdapter;
    RecyclerView rvFlags;
    FlagPrester flagPrester;
    SearchView countySearch;
    Button btnBack;
    TextView oops;
    TextView noInternet;
    Button retry;
    LottieAnimationView load;
    ImageView wifi;
    FirebaseAuth auth;
    FirebaseUser user;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag);
        countySearch=findViewById(R.id.country_search);
        countySearch.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        btnBack=findViewById(R.id.btn_back);
        flagAdapter = new FlagAdapter();
        rvFlags=findViewById(R.id.rv_search_meal);
        oops = findViewById(R.id.oops_tv_country);
        noInternet = findViewById(R.id.noInternet_tv_country);
        retry = findViewById(R.id.btn_rety_country);
        wifi = findViewById(R.id.wifi_img_country);
        load = findViewById(R.id.load_country);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        navigationBar();

        rvFlags.setLayoutManager(new LinearLayoutManager(this));
        rvFlags.setAdapter(flagAdapter);

        countySearch.setMaxWidth(700);
        countySearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                flagPrester.filterCountries(s);
                return false;
            }
        });
        btnBack.setOnClickListener(view->{
            this.finish();
        });
        checkNetwork();
    }

    @Override
    public void setFlag(ArrayList<Meal> meals) {
        flagAdapter.setCountry(meals);
        load.setVisibility(View.GONE);
    }

    @Override
    public void filter(ArrayList<Meal> meals) {
        flagAdapter.setCountry(meals);
    }
    private void checkNetwork(){

        if(NetworkListener.getConnectivity(this)){
            noInternet.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            oops.setVisibility(View.GONE);
            retry.setVisibility(View.GONE);
            load.setVisibility(View.VISIBLE);
            rvFlags.setVisibility(View.VISIBLE);
            flagPrester =new FlagPrester(this,this);
            flagPrester.setData();
        }else{
            wifi.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.VISIBLE);
            oops.setVisibility(View.VISIBLE);
            retry.setVisibility(View.VISIBLE);
            load.setVisibility(View.GONE);
            rvFlags.setVisibility(View.GONE);

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkNetwork();

                }
            });
        }
    }
    private void navigationBar(){

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        Intent intent = new Intent(FlagActivity.this, MainActivity.class);
                        //intent.putExtra("checkUserType",userType);
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
            startActivity(new Intent(FlagActivity.this, LoginActivity.class));
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