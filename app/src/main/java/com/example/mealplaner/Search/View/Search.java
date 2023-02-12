package com.example.mealplaner.Search.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mealplaner.Models.Category;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.InterFaces.CategoryInter;
import com.example.mealplaner.Search.Presenter.CategoryPresenter;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity implements CategoryInter {
    RecyclerView categoryRecyclView;
    CategoryPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        categoryRecyclView=findViewById(R.id.rv_category);
         presenter= new CategoryPresenter(this,this);

    }
    @Override
    public void setCategory(ArrayList<Category> categories) {
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
        categoryRecyclView.setLayoutManager(new GridLayoutManager(this,3));
        categoryRecyclView.setAdapter(categoryAdapter);

    }
}