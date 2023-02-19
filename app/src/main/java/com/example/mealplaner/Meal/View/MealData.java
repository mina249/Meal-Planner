package com.example.mealplaner.Meal.View;


import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.mealplaner.Meal.Controllers.YouTubeID;
import com.example.mealplaner.Meal.Interfaces.MealInterface;
import com.example.mealplaner.Meal.Presenter.MealPresenter;

import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Network.NetworkListener;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.Ingrediant.Presenter.IngrediantPresenter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Date;
import java.util.LinkedList;

public class MealData extends AppCompatActivity implements MealInterface {
    Button btnAddToFav;
    ;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_data);


        bundle = getIntent().getBundleExtra("id");
        mealID = bundle.getString("id");


        rvIngrediant = findViewById(R.id.rv_ingridiants);
        btnAddToFav = findViewById(R.id.btn_add_to_fav);
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


        btnAddToFav.setOnClickListener(view -> {
            Toast.makeText(this, "Meal Added To Favourite ", Toast.LENGTH_LONG).show();
            addToCalender();
        });
        checkNetwork();
    }

    @Override
    public void SetMealData(Meal meal, LinkedList<Pair<String, String>> ingridient) {
        tvCategory.setText(meal.getStrCategory());
        load.setVisibility(View.GONE);
        tvMealName.setText(meal.getStrMeal());
        tvCategory.setText(meal.getStrCategory());
        tvInstructions.setText(meal.getStrInstructions());
        if (meal.getStrTags() == null || meal.getStrTags().isEmpty()) {
            tvTag.setVisibility(View.GONE);
            tvTagStr.setVisibility(View.GONE);
        }
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
            mealPresenter = new MealPresenter(this);
            mealPresenter.getData(mealID);

            btnAddToFav.setVisibility(View.VISIBLE);
            ivMealPic.setVisibility(View.VISIBLE);
            tvCategory.setVisibility(View.VISIBLE);
            tvCountry.setVisibility(View.VISIBLE);
            tvTagStr.setVisibility(View.VISIBLE);

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
            btnAddToFav.setVisibility(View.GONE);
            ivMealPic.setVisibility(View.GONE);
            tvCategory.setVisibility(View.GONE);
            tvCountry.setVisibility(View.GONE);
            tvTagStr.setVisibility(View.GONE);

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
}