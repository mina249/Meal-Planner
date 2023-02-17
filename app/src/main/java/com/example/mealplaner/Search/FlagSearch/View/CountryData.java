package com.example.mealplaner.Search.FlagSearch.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mealplaner.Category.InterFace.MealInter;
import com.example.mealplaner.Category.View.CategoryMealAdapter;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.R;

import java.util.ArrayList;

public class CountryData extends AppCompatActivity implements MealInter {
    TextView tvCountryName;
    ImageView ivFlag;
    RecyclerView rvMeals;
    Resources res;
    CountryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_data);
        res = getResources();
        Bundle bundle = getIntent().getBundleExtra("country");
        tvCountryName = findViewById(R.id.tv_can_name);
        String country = bundle.getString("country");
        tvCountryName.setText(country);

        ivFlag = findViewById(R.id.iv_flag_pic);
        rvMeals = findViewById(R.id.rv_flag_meals);
        int resourceId = res.getIdentifier(country.toLowerCase(), "drawable",
                this.getPackageName());
        ivFlag.setImageResource(resourceId);
        presenter = new CountryPresenter(this);
       presenter.setMeals(country);

    }

    @Override
    public void setMeal(ArrayList<Meal> meals) {
        CategoryMealAdapter categoryAdapter = new CategoryMealAdapter(meals);
        rvMeals.setLayoutManager(new LinearLayoutManager(this));
        rvMeals.setAdapter(categoryAdapter);
    }
}