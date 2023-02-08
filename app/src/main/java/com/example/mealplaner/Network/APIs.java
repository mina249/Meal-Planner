package com.example.mealplaner.Network;

import com.example.mealplaner.Models.AllCategories;
import com.example.mealplaner.Models.Meals;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIs {
    @GET("/api/json/v1/1/categories.php")
    Call<AllCategories> getCategories();
    @GET("/api/json/v1/1/search.php?")
    Call<Meals> getCategoryMeals(@Query("s") String mealName);

}
