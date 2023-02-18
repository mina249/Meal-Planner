package com.example.mealplaner.Search.Category.Presenter;


import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.mealplaner.Models.Category;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Network.Repositry;
import com.example.mealplaner.Search.Category.InterFaces.CategoryInter;
import com.example.mealplaner.Search.Category.InterFaces.FilterCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CategoryPresenter {
    private CategoryInter categoryInter;
    FilterCategory filterCategory;
    private static MutableLiveData<ArrayList<Category>> mutableLiveData;

    public CategoryPresenter(CategoryInter categoryInter, Context context) {
        filterCategory= (FilterCategory) context;
        this.categoryInter = categoryInter;
        mutableLiveData = Repositry.getCategories();
        mutableLiveData.observe((LifecycleOwner) context,categoryInter::setCategory);

    }
    public void filterList(String filterStr){
       ArrayList<Category> filteredMeals= (ArrayList<Category>) mutableLiveData.getValue().stream()
               .filter(iteam->{
            return iteam.getStrCategory().startsWith(filterStr);
        }).collect(Collectors.toList());
       filterCategory.filter(filteredMeals);
    }

}
