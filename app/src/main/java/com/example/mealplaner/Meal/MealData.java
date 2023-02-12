package com.example.mealplaner.Meal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.R;

public class MealData extends AppCompatActivity implements MealInterface{
    Button btnAddToFav;;
    ImageView ivMealPic;
    TextView tvIngrediant;
    TextView tvMealName;
    TextView tvTag;
    TextView tvInstructions;
    TextView tvCategory;
    MealPresenter mealPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_data);
        btnAddToFav=findViewById(R.id.btn_add_to_fav);
        ivMealPic=findViewById(R.id.iv_meal);
        tvCategory=findViewById(R.id.tv_meal_category);
        tvIngrediant=findViewById(R.id.tv_Ingridiant);
        tvMealName=findViewById(R.id.tv_meal_name);
        tvTag=findViewById(R.id.tv_meal_tag);
        tvInstructions=findViewById(R.id.tv_Instructions);
        btnAddToFav.setOnClickListener(view->{
            Toast.makeText(this, "Meal Added To Favourite ", Toast.LENGTH_LONG).show();
           mealPresenter = new MealPresenter(this,"52772");

        });
    }

    @Override
    public void SetMealData(Meal meal,String ingridiant ) {
        tvCategory.setText(meal.getStrCategory());
        tvMealName.setText(meal.getStrMeal());
        tvCategory.setText(meal.getStrCategory());
        tvInstructions.setText(meal.getStrInstructions());
        tvIngrediant.setText(ingridiant);
        tvTag.setText(meal.getStrTags());
    }
}