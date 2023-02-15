package com.example.mealplaner.Network;

import androidx.lifecycle.MutableLiveData;

import com.example.mealplaner.Models.AllCategories;
import com.example.mealplaner.Models.Category;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Models.Meals;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repositry {
    private static MutableLiveData<ArrayList<Category>> mutableLiveData = new MutableLiveData<>();
    private static MutableLiveData<ArrayList<Meal>> mealsMutableLiveData = new MutableLiveData<>();
    private static ArrayList<Category> categoryList;
    private static ArrayList<Meal> mealList;


    public static MutableLiveData<ArrayList<Category>> getCategories() {

        categoryList = null;
        Call<AllCategories> call = CatigoryConn.getPostApi().getCategories();
        call.enqueue(new Callback<AllCategories>() {
            @Override
            public void onResponse(Call<AllCategories> call, Response<AllCategories> response) {
                categoryList = response.body().getCategories();
                if (categoryList != null)
                    mutableLiveData.postValue(categoryList);

            }

            @Override
            public void onFailure(Call<AllCategories> call, Throwable t) {
                call.cancel();

            }
        });

        return mutableLiveData;

    }

    public static MutableLiveData<ArrayList<Meal>> getCategoryMeals(String mealName) {

        mealList = null;
        Call<Meals> call = CatigoryConn.getPostApi().getCategoryMeals(mealName);
        call.enqueue(new Callback<Meals>() {

            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                mealList = response.body().getMeals();
                if (mealList != null)
                    mealsMutableLiveData.postValue(mealList);

            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {

            }

        });

        return mealsMutableLiveData;

    }

}
