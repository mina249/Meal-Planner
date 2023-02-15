package com.example.mealplaner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealplaner.DataBase.ConcreteLocalSource;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MealViewInterface, OnAddToFavouriteClickListener, OnDeleteFromFavClickListener {

    RecyclerView recyclerView;
    HomeRvAdapter homeAdapter;
    ArrayList<Meal> meals = new ArrayList<>();
    ViewPager2 homePager;
    FloatingActionButton signout;
    MealPresenterInterface mealPresenterInterface;
    TextView loggedUserName;

    FirebaseAuth firebaseAuth;
    String userType;

    ImageView noInternet;
    TextView oops;
    TextView tv_noInternet;
    Button retry;


    ConnectivityManager connectivityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationBar();
        inflateUI();


        mealPresenterInterface = new MealPresenter(MealService.getInstance(), this, this, ConcreteLocalSource.getInstance(this));
        mealPresenterInterface.getMeal();

        firebaseAuth = FirebaseAuth.getInstance();
        setHomeViewPager();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUserStatus();
            }
        });
        userType = getIntent().getStringExtra("checkUserType");
        if (userType == null) {
            userType = "loggedUser";
        }
    }

    private void navigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra("checkUserType", userType);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.love:
                        if (userType.equals("guest")) {
                            Toast.makeText(MainActivity.this, "You should login first", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(MainActivity.this, FavouriteMealActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                    case R.id.calendar:
                        if (userType.equals("guest")) {
                            Toast.makeText(MainActivity.this, "You should login first", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }

                }
                return false;
            }
        });
    }


    @Override
    public void showData(ArrayList<Meal> meals) {
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        homeAdapter = new HomeRvAdapter(this, meals, homePager, this, this);
        homePager.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();

    }

    @Override
    public void addToFav(Meal meal) {
        mealPresenterInterface.addToFav(meal);
    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {

        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void inflateUI() {
        signout = findViewById(R.id.prof_img);
        homePager = findViewById(R.id.viewpager_home);
        loggedUserName = findViewById(R.id.logged_user_name);
        noInternet = findViewById(R.id.wifi_img_main);
        oops = findViewById(R.id.oops_tv_main);
        tv_noInternet = findViewById(R.id.btn_rety_main);
        retry = findViewById(R.id.btn_rety_main);
    }

    private void setHomeViewPager() {
        homePager.setClipToPadding(false);
        homePager.setClipChildren(false);
        homePager.setOffscreenPageLimit(3);
        homePager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        homePager.setPageTransformer(compositePageTransformer);

    }

    @Override
    public void onClick(Meal meal) {
        addToFav(meal);

    }

    @Override
    public void onDeleteClick(Meal meal) {

    }
}
