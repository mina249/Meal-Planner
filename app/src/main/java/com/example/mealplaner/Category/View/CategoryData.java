package com.example.mealplaner.Category.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mealplaner.Category.InterFace.MealInter;
import com.example.mealplaner.Category.Presenter.MealsPresenter;
import com.example.mealplaner.Models.Category;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.R;

import java.util.ArrayList;

public class CategoryData extends AppCompatActivity implements MealInter {
    Category category;
    ImageView ivCategory;
    TextView tvCatName,tvDiscription;
    RecyclerView rvMeals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_data);
        Bundle bundle=getIntent().getBundleExtra("category");
        category= (Category) bundle.getSerializable("category");
        ivCategory=findViewById(R.id.iv_Ingridoant);
        tvCatName=findViewById(R.id.tv_ing_name);
        tvDiscription=findViewById(R.id.tv_ing_disc);
        rvMeals=findViewById(R.id.rv_meals);

       Glide.with(this).load(category.getStrCategoryThumb()).into(ivCategory);
        tvCatName.setText(category.getStrCategory());
        tvDiscription.setText(category.getStrCategoryDescription());
        MealsPresenter presenter= new MealsPresenter(this,this,category.getStrCategory());
    }

    @Override
    public void setMeal(ArrayList<Meal> meals) {
        CategoryMealAdapter categoryAdapter = new CategoryMealAdapter(meals);
        rvMeals.setLayoutManager(new LinearLayoutManager(this));
        rvMeals.setAdapter(categoryAdapter);
    }
}