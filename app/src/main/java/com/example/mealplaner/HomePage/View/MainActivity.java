package com.example.mealplaner.HomePage.View;

import static com.example.mealplaner.Profile.View.ProfileActivity.editor;
import static com.example.mealplaner.Profile.View.ProfileActivity.sharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;

import com.bumptech.glide.Glide;
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
import com.example.mealplaner.Network.FireBaseData;
import com.example.mealplaner.Network.NetworkListener;
import com.example.mealplaner.Network.MealService;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.FavouriteMeals.Intercafaces.OnDeleteFromFavClickListener;
import com.example.mealplaner.Profile.View.ProfileActivity;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.HomeSearchPage.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    ViewPager2 vpRecommended;
    SliderAdapter sliderAdapter;
    CompositePageTransformer pageTransformer;
    private Handler handler;
    private Runnable runnable;

    TextView daily;

    FirebaseAuth aut;
    FirebaseUser user;
    ArrayList<Meal> random;
    TextView tvRecomnded,tvDailyIns;
    BottomNavigationView bottomNavigationView;
    FirebaseDatabase database;
    DatabaseReference reference;

    public SharedPreferences sharedPreferences;
    public  SharedPreferences.Editor editor;
    boolean nightMode;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler=new Handler();
        aut = FirebaseAuth.getInstance();
        user = aut.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        inflateUI();
        navigationBar();
        checkNetwork();
        favouriteMealPresenterInterface = new FavouriteMealPresenter(this, ConcreteLocalSource.getInstance(this));
        firebaseAuth = FirebaseAuth.getInstance();
        setHomeViewPager();
        random = new ArrayList<>();



        userType = getIntent().getStringExtra("checkUserType");
        if (userType == null) {
            userType = "loggedUser";
        }
        profImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                }else{
                    Toast.makeText(MainActivity.this, "You should login first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getUserDataFromFireBase();
        runnable = new Runnable() {
            @Override
            public void run() {
            vpRecommended.setCurrentItem(vpRecommended.getCurrentItem()+1);
            }
        };
        vpRecommended.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,2000);
            }
        });

        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FireBaseData.getPlanFromFireBase(MainActivity.this,user,"sunday");
                FireBaseData.getFavouriteFromFirebase(MainActivity.this,user);
            }
        });


    }

    private void navigationBar() {

        bottomNavigationView.setSelectedItemId(R.id.home);
       Menu m = bottomNavigationView.getMenu();
       m.findItem(R.id.home).setIcon(R.drawable.fav);
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
                        if (user == null) {
                           showConfirmationDialog();
                        } else {
                            startActivity(new Intent(MainActivity.this, FavouriteMealActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                    case R.id.calendar:
                        if (user == null) {
                            showConfirmationDialog();
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
        vpRecommended.setVisibility(View.VISIBLE);
        tvRecomnded.setVisibility(View.VISIBLE);
        daily.setVisibility(View.VISIBLE);

    }

    @Override
    public void addToFav(Meal meal) {
        mealPresenterInterface.addToFav(meal);
    }

    @Override
    public void showRecommendedMeals(ArrayList<Meal> recommendedMeals) {
        Random rand = new Random();

        for (int i =0 ; i<10 ; i++){
            int s =    rand.nextInt(24);
            random.add(recommendedMeals.get(s));
        }

        sliderAdapter=new SliderAdapter(vpRecommended,random,this,this,this);
        vpRecommended.setAdapter(sliderAdapter);
        sliderAdapter.notifyDataSetChanged();

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
        vpRecommended =findViewById(R.id.viewpager_recommended);
        daily = findViewById(R.id.tv_inspiration);
        tvRecomnded =findViewById(R.id.tv_recomended);
        bottomNavigationView = findViewById(R.id.bottom_navigation);



        vpRecommended.setAdapter(sliderAdapter);
        vpRecommended.setClipChildren(false);
        vpRecommended.setClipToPadding(false);
        vpRecommended.setOffscreenPageLimit(3);
        vpRecommended.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
         pageTransformer =new CompositePageTransformer();
         pageTransformer.addTransformer(new MarginPageTransformer(30));
         pageTransformer.addTransformer(new ViewPager2.PageTransformer() {
             @Override
             public void transformPage(@NonNull View page, float position) {
                 float r = 1-Math.abs(position);
                 page.setScaleY(0.85f+r*0.15f);
             }
         });
         vpRecommended.setPageTransformer(pageTransformer);


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
            loadingBar.setRepeatCount(1000);
            mealPresenterInterface = new MealPresenter(MealService.getInstance(), this, this, ConcreteLocalSource.getInstance(this));
            mealPresenterInterface.getMeal();
            mealPresenterInterface.getRecommendedMeal();
            vpRecommended.setVisibility(View.GONE);
            tvRecomnded.setVisibility(View.GONE);
            daily.setVisibility(View.GONE);

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

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable,500);
    }
    private void getUserDataFromFireBase() {
        if (user != null) {
            Query query = reference.orderByChild("email").equalTo(user.getEmail());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String uname = "" + ds.child("name").getValue();
                        String uemail = "" + ds.child("email").getValue();
                        String uimage = "" + ds.child("image").getValue();
                        loggedUserName.setText(uname);

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

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
           startActivity(new Intent(MainActivity.this,LoginActivity.class));
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
