package com.example.mealplaner.HomePage.View;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mealplaner.Calendar.CalendarActivity;
import com.example.mealplaner.DataBase.ConcreteLocalSource;
import com.example.mealplaner.FavouriteMeals.Intercafaces.FavouriteMealPresenterInterface;
import com.example.mealplaner.FavouriteMeals.Intercafaces.FavouriteViewInterface;
import com.example.mealplaner.FavouriteMeals.Presenter.FavouriteMealPresenter;
import com.example.mealplaner.FavouriteMeals.View.FavouriteMealActivity;
import com.example.mealplaner.HomePage.Presenter.MealPresenter;
import com.example.mealplaner.HomePage.Interfaces.MealPresenterInterface;
import com.example.mealplaner.HomePage.Interfaces.MealViewInterface;
import com.example.mealplaner.HomePage.Interfaces.OnAddToFavouriteClickListener;
import com.example.mealplaner.Login.View.LoginActivity;
import com.example.mealplaner.Network.NetworkListener;
import com.example.mealplaner.Network.MealService;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.FavouriteMeals.Intercafaces.OnDeleteFromFavClickListener;
import com.example.mealplaner.Profile.ProfileActivity;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.Category.View.CategorySearch;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity implements MealViewInterface, FavouriteViewInterface, OnAddToFavouriteClickListener, OnDeleteFromFavClickListener {

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
    LottieAnimationView loadingBar;
    FavouriteMealPresenterInterface favouriteMealPresenterInterface;
    ImageButton profImg;

    ConnectivityManager connectivityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationBar();
        inflateUI();
        checkNetwork();
        favouriteMealPresenterInterface = new FavouriteMealPresenter(this, ConcreteLocalSource.getInstance(this));
        firebaseAuth = FirebaseAuth.getInstance();
        setHomeViewPager();

        userType = getIntent().getStringExtra("checkUserType");
        if (userType == null) {
            userType = "loggedUser";
        }
        profImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userType.equals("loggedUser")) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                }else{
                    Toast.makeText(MainActivity.this, "You should login first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void navigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        Intent intent = new Intent(MainActivity.this, CategorySearch.class);
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
        loadingBar.setVisibility(View.GONE);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        homeAdapter = new HomeRvAdapter(this, meals, homePager, this, this);
        homePager.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();

    }

    @Override
    public void addToFav(Meal meal) {
        mealPresenterInterface.addToFav(meal);
    }

    @Override
    public void showRecommendedMeals(ArrayList<Meal> recommendedMeals) {

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
        profImg = findViewById(R.id.prof_img_home);
        homePager = findViewById(R.id.viewpager_home);
        loggedUserName = findViewById(R.id.logged_user_name);
        noInternet = findViewById(R.id.wifi_img_main);
        oops = findViewById(R.id.oops_tv_main);
        tv_noInternet = findViewById(R.id.noInternet_tv_main);
        retry = findViewById(R.id.btn_rety_main);
        loadingBar = findViewById(R.id.load);

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
        remove(meal);
    }

    private void checkNetwork(){

        if(NetworkListener.getConnectivity(this)){
            noInternet.setVisibility(View.GONE);
            tv_noInternet.setVisibility(View.GONE);
            oops.setVisibility(View.GONE);
            retry.setVisibility(View.GONE);
            loadingBar.setVisibility(View.VISIBLE);

            mealPresenterInterface = new MealPresenter(MealService.getInstance(), this, this, ConcreteLocalSource.getInstance(this));
            mealPresenterInterface.getMeal();
        }else{
            noInternet.setVisibility(View.VISIBLE);
            tv_noInternet.setVisibility(View.VISIBLE);
            oops.setVisibility(View.VISIBLE);
            retry.setVisibility(View.VISIBLE);
            loadingBar.setVisibility(View.GONE);

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkNetwork();

                }
            });
        }
    }


    @Override
    public void remove(Meal meal) {
        favouriteMealPresenterInterface.removeFav(meal);
    }

    @Override
    public void showFav(Observable<List<Meal>> products) {

    }
}
