package com.example.mealplaner.Meal.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mealplaner.Meal.Interfaces.MealInterface;

import com.example.mealplaner.Meal.Controllers.YouTubeID;

import com.example.mealplaner.Meal.Presenter.MealPresenter;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.LinkedList;

public class MealData extends AppCompatActivity implements MealInterface {
    Button btnAddToFav;;
    ImageView ivMealPic;
    RecyclerView rvIngrediant;
    TextView tvMealName;
    TextView tvTag;
    TextView tvInstructions;
    TextView tvCategory,tvCountry,tvTagStr;
    MealPresenter mealPresenter;
    String mealID;
    Bundle bundle;
    YouTubePlayerView youTubePlayerView;
    MealIngridientAdapter ingridientAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_data);

         bundle= getIntent().getBundleExtra("id");
         mealID=bundle.getString("id");

       rvIngrediant=findViewById(R.id.rv_ingridiants);
        btnAddToFav=findViewById(R.id.btn_add_to_fav);
        ivMealPic=findViewById(R.id.iv_meal);
        tvCategory=findViewById(R.id.tv_meal_category);
        tvCountry=findViewById(R.id.tv_country);
        tvTagStr=findViewById(R.id.tv_meal_tag_str);

        tvMealName=findViewById(R.id.tv_meal_name);
        tvTag=findViewById(R.id.tv_meal_tag);
        youTubePlayerView=findViewById(R.id.pl_youtube_player);

        tvInstructions=findViewById(R.id.tv_Instructions);
        mealPresenter = new MealPresenter(this);
        mealPresenter.getData(mealID);


        btnAddToFav.setOnClickListener(view->{
            Toast.makeText(this, "Meal Added To Favourite ", Toast.LENGTH_LONG).show();


        });
    }

    @Override
    public void SetMealData( Meal meal, LinkedList<Pair<String, String>> ingridient) {
        tvCategory.setText(meal.getStrCategory());
        tvMealName.setText(meal.getStrMeal());
        tvCategory.setText(meal.getStrCategory());
        tvInstructions.setText(meal.getStrInstructions());
        if(meal.getStrTags()==null||meal.getStrTags().isEmpty()){
            tvTag.setVisibility(View.GONE);
            tvTagStr.setVisibility(View.GONE);
        }
        tvTag.setText(meal.getStrTags());
        tvCountry.setText(meal.getStrArea());
        YouTubeID youTubeID = new YouTubeID(meal.getStrYoutube());
        String vedioID= youTubeID.getVedioID();
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.loadVideo(vedioID,8);
                youTubePlayer.pause();
            }
        });
        Glide.with(this).load(meal.getStrMealThumb()).into(ivMealPic);
        ingridientAdapter= new MealIngridientAdapter(ingridient);
        rvIngrediant.setLayoutManager(new LinearLayoutManager(this));
        rvIngrediant.setAdapter(ingridientAdapter);



    }
}