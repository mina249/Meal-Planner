package com.example.mealplaner.Search.Category.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mealplaner.Models.Category;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.Category.InterFaces.CategoryInter;
import com.example.mealplaner.Search.Category.Presenter.CategoryPresenter;

import java.util.ArrayList;

public class CategorySearch extends AppCompatActivity implements CategoryInter {
    RecyclerView categoryRecyclView;
    CategoryPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_category);
        categoryRecyclView=findViewById(R.id.rv_ingrediant);
         presenter= new CategoryPresenter(this,this);

    }
    @Override
    public void setCategory(ArrayList<Category> categories) {
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
        categoryRecyclView.setLayoutManager(new GridLayoutManager(this,3));
        categoryRecyclView.setAdapter(categoryAdapter);

    }
}