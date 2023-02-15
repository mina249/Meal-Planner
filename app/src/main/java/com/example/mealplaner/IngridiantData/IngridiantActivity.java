package com.example.mealplaner.IngridiantData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mealplaner.Category.InterFace.MealInter;
import com.example.mealplaner.Category.View.CategoryMealAdapter;
import com.example.mealplaner.Models.IngrediantModel;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.R;

import java.util.ArrayList;

public class IngridiantActivity extends AppCompatActivity implements MealInter {
    IngrediantModel ingridiant;
    ImageView ivIngridant;
    TextView tvDiscription,tvName;
    final String INGRIDIANT_URL="https://www.themealdb.com/images/ingredients/";
    String imgUrl;
    RecyclerView rvMeals;
    IngridiantMealPresenter mealPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingridiant);
        Bundle bundle =getIntent().getBundleExtra("Ingrediant");
        ingridiant= (IngrediantModel) bundle.getSerializable("Ingrediant");
        imgUrl=INGRIDIANT_URL+ingridiant.getStrIngredient()+".png";
        ivIngridant=findViewById(R.id.iv_Ingridoant);
        tvDiscription=findViewById(R.id.tv_ing_disc);
        tvName=findViewById(R.id.tv_ing_name);
        rvMeals=findViewById(R.id.rv_ingrediant_meals);

        tvName.setText(ingridiant.getStrIngredient());
        tvDiscription.setText(ingridiant.getStrDescription());
        Glide.with(this).load(imgUrl).into(ivIngridant);
        mealPresenter = new IngridiantMealPresenter(this);
        mealPresenter.setMeals(ingridiant.getStrIngredient());



    }

    @Override
    public void setMeal(ArrayList<Meal> meals) {
        CategoryMealAdapter categoryAdapter = new CategoryMealAdapter(meals);
        rvMeals.setLayoutManager(new LinearLayoutManager(this));
        rvMeals.setAdapter(categoryAdapter);
    }
}