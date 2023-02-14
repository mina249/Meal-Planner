package com.example.mealplaner.Search.Category.Presenter;


import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.mealplaner.Models.Category;
import com.example.mealplaner.Network.Repositry;
import com.example.mealplaner.Search.Category.InterFaces.CategoryInter;

import java.util.ArrayList;


public class CategoryPresenter {
    private CategoryInter categoryInter;
    private static MutableLiveData<ArrayList<Category>> mutableLiveData;

    public CategoryPresenter(CategoryInter categoryInter, Context context) {

        this.categoryInter = categoryInter;
        mutableLiveData = Repositry.getCategories();
        mutableLiveData.observe((LifecycleOwner) context,categoryInter::setCategory);
    }

}
