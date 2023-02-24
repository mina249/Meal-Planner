package com.example.mealplaner.Meal.View;


import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.mealplaner.DataBase.ConcreteLocalSource;
import com.example.mealplaner.FavouriteMeals.Intercafaces.FavouriteMealPresenterInterface;
import com.example.mealplaner.FavouriteMeals.Intercafaces.FavouriteViewInterface;
import com.example.mealplaner.FavouriteMeals.Intercafaces.OnDeleteFromFavClickListener;
import com.example.mealplaner.FavouriteMeals.Presenter.FavouriteMealPresenter;
import com.example.mealplaner.HomePage.Interfaces.MealPresenterInterface;
import com.example.mealplaner.HomePage.Interfaces.MealViewInterface;
import com.example.mealplaner.HomePage.Interfaces.OnAddToFavouriteClickListener;
import com.example.mealplaner.Meal.Controllers.YouTubeID;
import com.example.mealplaner.Meal.Interfaces.MealInterface;
import com.example.mealplaner.Meal.Presenter.MealPresenter;

import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Network.FireBaseData;
import com.example.mealplaner.Network.MealService;
import com.example.mealplaner.Network.NetworkListener;
import com.example.mealplaner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;

public class MealData extends AppCompatActivity implements MealInterface , MealViewInterface, FavouriteViewInterface, OnAddToFavouriteClickListener, OnDeleteFromFavClickListener {
    Button btnAddToCalendar, btnAddtoToFav;

    ImageView ivMealPic;
    RecyclerView rvIngrediant;
    TextView tvMealName;
    TextView tvTag, tvCategoryStr, tvCountryStr, tvMealInstStr, tvIngreStr;
    TextView tvInstructions;
    TextView tvCategory, tvCountry, tvTagStr;
    MealPresenter mealPresenter;
    String mealID;
    Bundle bundle;
    YouTubePlayerView youTubePlayerView;
    MealIngridientAdapter ingridientAdapter;
    TextView oops;
    TextView noInternet;
    Button retry;
    LottieAnimationView load;
    ImageView wifi;
    AutoCompleteTextView autoCompleteTextView;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Meal meal;
    FavouriteMealPresenterInterface favouriteMealPresenterInterface;
    MealPresenterInterface mealPresenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_data);
        firebaseAuth = FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();

        bundle = getIntent().getBundleExtra("id");
        mealID = bundle.getString("id");

        mealPresenterInterface = new com.example.mealplaner.HomePage.Presenter.MealPresenter(MealService.getInstance(), this, this, ConcreteLocalSource.getInstance(this));
        favouriteMealPresenterInterface = new FavouriteMealPresenter(this, ConcreteLocalSource.getInstance(this));

        autoCompleteTextView = findViewById(R.id.dp_plan);
        btnAddtoToFav = findViewById(R.id.btn_add_to_fav_de);
        rvIngrediant = findViewById(R.id.rv_ingridiants);
        btnAddToCalendar = findViewById(R.id.btn_add_to_fav);
        ivMealPic = findViewById(R.id.iv_meal);
        tvCategory = findViewById(R.id.tv_meal_category);
        tvCountry = findViewById(R.id.tv_country);
        tvTagStr = findViewById(R.id.tv_meal_tag_str);

        tvCategoryStr = findViewById(R.id.tv_category);
        tvCountryStr = findViewById(R.id.tv_country_string);
        tvMealInstStr = findViewById(R.id.tv_meal_instruction_str);
        tvIngreStr = findViewById(R.id.tv_Ingridiant_str);


        tvMealName = findViewById(R.id.tv_meal_name);
        tvTag = findViewById(R.id.tv_meal_tag);
        youTubePlayerView = findViewById(R.id.pl_youtube_player);

        tvInstructions = findViewById(R.id.tv_Instructions);

        oops = findViewById(R.id.oops_tv_meal_data);
        noInternet = findViewById(R.id.noInternet_tv_meal_data);
        retry = findViewById(R.id.btn_rety_meal_data);
        wifi = findViewById(R.id.wifi_img_meal_data);
        load = findViewById(R.id.load_meal_data);
        tvCategoryStr.setVisibility(View.GONE);
        tvCountryStr.setVisibility(View.GONE);
        tvMealInstStr.setVisibility(View.GONE);
        tvIngreStr.setVisibility(View.GONE);
        tvMealName.setVisibility(View.GONE);
        tvTag.setVisibility(View.GONE);
        tvTagStr.setVisibility(View.GONE);
        autoCompleteTextView.setVisibility(View.GONE);
        btnAddtoToFav.setVisibility(View.GONE);


        btnAddToCalendar.setOnClickListener(view -> {
            Toast.makeText(this, "Meal Added To Favourite ", Toast.LENGTH_LONG).show();
            addToCalender();
        });
        checkNetwork();
    }

    @Override
    public void SetMealData(Meal meal, LinkedList<Pair<String, String>> ingridient,String getMethod) {
        this.meal=meal;
        tvCategory.setText(meal.getStrCategory());
        load.setVisibility(View.GONE);
        tvMealName.setText(meal.getStrMeal());
        tvCategory.setText(meal.getStrCategory());
        tvInstructions.setText(meal.getStrInstructions());
        if(getMethod.equals("DB")&&meal.getStatus().equals("favourite"))
            btnAddtoToFav.setBackgroundResource(R.drawable.heart);
        if (meal.getStrTags() == null || meal.getStrTags().isEmpty()) {
            tvTag.setVisibility(View.GONE);
            tvTagStr.setVisibility(View.GONE);
        }
        autoCompleteTextView.setVisibility(View.VISIBLE);
        btnAddtoToFav.setVisibility(View.VISIBLE);
        tvTag.setText(meal.getStrTags());
        tvCountry.setText(meal.getStrArea());
        YouTubeID youTubeID = new YouTubeID(meal.getStrYoutube());
        String vedioID = youTubeID.getVedioID();
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.loadVideo(vedioID, 8);
                youTubePlayer.pause();
            }
        });
        Glide.with(this).load(meal.getStrMealThumb()).into(ivMealPic);
        ingridientAdapter = new MealIngridientAdapter(ingridient);
        rvIngrediant.setLayoutManager(new LinearLayoutManager(this));
        rvIngrediant.setAdapter(ingridientAdapter);

        tvCategoryStr.setVisibility(View.VISIBLE);
        tvCountryStr.setVisibility(View.VISIBLE);
        tvMealInstStr.setVisibility(View.VISIBLE);
        tvIngreStr.setVisibility(View.VISIBLE);
        tvMealName.setVisibility(View.VISIBLE);
        tvTag.setVisibility(View.VISIBLE);
        tvTagStr.setVisibility(View.VISIBLE);
        showPlanList();
        addToFavourit();
    }

    void addToCalender() {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, tvMealName.getText().toString());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, tvCountry.getText().toString());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, tvInstructions.getText().toString());
        intent.putExtra(CalendarContract.Events.ALL_DAY, true);
        startActivity(intent);

          /*  Uri.Builder contract =CalendarContract.CONTENT_URI.buildUpon().appendPath("time");
            Uri uris=ContentUris.appendId(contract,new Date().getTime()).build();
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setData(uris);
            intent1.putExtra(CalendarContract.Events.TITLE,tvMealName.getText().toString());
            intent.putExtra(CalendarContract.Events.DESCRIPTION,tvInstructions.getText().toString());
            intent.putExtra(CalendarContract.Events.ALL_DAY,true);
            startActivity(intent1);*/
    }

    private void checkNetwork() {

        if (NetworkListener.getConnectivity(this)) {
            noInternet.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            oops.setVisibility(View.GONE);
            retry.setVisibility(View.GONE);
            load.setVisibility(View.VISIBLE);
            rvIngrediant.setVisibility(View.VISIBLE);
            mealPresenter = new MealPresenter(this,this);
            mealPresenter.getData(mealID);

            btnAddToCalendar.setVisibility(View.VISIBLE);
            ivMealPic.setVisibility(View.VISIBLE);
            tvCategory.setVisibility(View.VISIBLE);
            tvCountry.setVisibility(View.VISIBLE);
            tvTagStr.setVisibility(View.VISIBLE);
            autoCompleteTextView.setVisibility(View.GONE);
            btnAddtoToFav.setVisibility(View.GONE);

            tvMealName.setVisibility(View.VISIBLE);
            tvTag.setVisibility(View.VISIBLE);
            youTubePlayerView.setVisibility(View.VISIBLE);
            tvInstructions.setVisibility(View.VISIBLE);


        } else {
            wifi.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.VISIBLE);
            oops.setVisibility(View.VISIBLE);
            retry.setVisibility(View.VISIBLE);
            load.setVisibility(View.GONE);
            rvIngrediant.setVisibility(View.GONE);
            btnAddToCalendar.setVisibility(View.GONE);
            ivMealPic.setVisibility(View.GONE);
            tvCategory.setVisibility(View.GONE);
            tvCountry.setVisibility(View.GONE);
            tvTagStr.setVisibility(View.GONE);
            autoCompleteTextView.setVisibility(View.GONE);
            btnAddtoToFav.setVisibility(View.GONE);

            tvMealName.setVisibility(View.GONE);
            tvTag.setVisibility(View.GONE);
            youTubePlayerView.setVisibility(View.GONE);
            tvInstructions.setVisibility(View.GONE);


            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkNetwork();

                }
            });
        }
    }
    private void showPlanList(){

        String [] days ={"Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,days);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null) {
                    autoCompleteTextView.showDropDown();
                }else{
                    Toast.makeText(MealData.this, "You Should Login first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String day = parent.getItemAtPosition(position).toString();
                switch (day) {
                    case "Saturday":
                        meal.setStatus("saturday");

                        onClick(meal);
                        FireBaseData.addPlanToFirebase(MealData.this,meal);
                        Toast.makeText(MealData.this, "Meal added to Saturday", Toast.LENGTH_SHORT).show();

                        break;
                    case "Sunday":
                        meal.setStatus("sunday");
                        onClick(meal);
                        FireBaseData.addPlanToFirebase(MealData.this,meal);
                        Toast.makeText(MealData.this, "Meal added to Sunday ", Toast.LENGTH_SHORT).show();
                        break;
                    case "Monday":
                        meal.setStatus("monday");
                        onClick(meal);
                        FireBaseData.addPlanToFirebase(MealData.this,meal);
                        Toast.makeText(MealData.this, "Meal added to Monday", Toast.LENGTH_SHORT).show();
                        break;
                    case "Tuesday":
                        meal.setStatus("tuesday");
                        onClick(meal);
                        FireBaseData.addPlanToFirebase(MealData.this,meal);
                        Toast.makeText(MealData.this, "Meal added to Tuesday", Toast.LENGTH_SHORT).show();
                        break;

                    case "Wednesday":
                        meal.setStatus("wednesday");
                        onClick(meal);
                        FireBaseData.addPlanToFirebase(MealData.this,meal);
                        Toast.makeText(MealData.this, "Meal added to Wednesday ", Toast.LENGTH_SHORT).show();
                        break;

                    case "Thursday":
                        meal.setStatus("thursday");
                        onClick(meal);
                        FireBaseData.addPlanToFirebase(MealData.this,meal);
                        Toast.makeText(MealData.this, "Meal added to Thursday", Toast.LENGTH_SHORT).show();
                        break;
                    case "Friday":
                        meal.setStatus("friday");
                        onClick(meal);
                        FireBaseData.addPlanToFirebase(MealData.this,meal);
                        Toast.makeText(MealData.this, "Meal added to Friday ", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public void remove(Meal meal) {
        favouriteMealPresenterInterface.removeFav(meal);
    }

    @Override
    public void showFav(Observable<List<Meal>> products) {

    }

    @Override
    public void onDeleteClick(Meal meal) {
    remove(meal);
    }

    @Override
    public void showData(ArrayList<Meal> meals) {

    }

    @Override
    public void addToFav(Meal meal) {
        mealPresenterInterface.addToFav(meal);
    }

    @Override
    public void showRecommendedMeals(ArrayList<Meal> recommendedMeals) {

    }
    @Override
    public void onClick(Meal meal) {
        addToFav(meal);
    }
    void addToFavourit(){
        btnAddtoToFav.setOnClickListener(new View.OnClickListener() {
            boolean isFavorite = false;

            @Override
            public void onClick(View v) {
                if(user!=null) {
                    if (!isFavorite) {
                        btnAddtoToFav.setBackgroundResource(R.drawable.heart);
                        meal.setStatus("favourite");
                        MealData.this.onClick(meal);
                        FireBaseData.addFavouriteToFirebase(MealData.this,meal);


                        isFavorite = true;
                    } else {
                        btnAddtoToFav.setBackgroundResource(R.drawable.fav);
                        isFavorite = false;
                        onDeleteClick(meal);
                        FireBaseData.addFavouriteToFirebase(MealData.this,meal);
                    }
                }else {
                    Toast.makeText(MealData.this, "You Should Login first", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}