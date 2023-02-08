package com.example.mealplaner.Category.Presenter;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.mealplaner.Category.InterFace.MealInter;
import com.example.mealplaner.Models.Category;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Network.Repositry;
import com.example.mealplaner.Search.InterFaces.CategoryInter;

import java.util.ArrayList;

public class MealsPresenter {
    private MealInter mealInter;
    private static MutableLiveData<ArrayList<Meal>> mutableLiveData;

    public MealsPresenter( MealInter mealInter, Context context,String mealName) {

        this.mealInter = mealInter;
        mutableLiveData = Repositry.getCategoryMeals(mealName);
        mutableLiveData.observe((LifecycleOwner) context,mealInter::setMeal);
}
}
