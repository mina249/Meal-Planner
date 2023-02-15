package com.example.mealplaner;


import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Models.Meals;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIMeal {
    @GET("api/json/v1/1/search.php?s")
    Call<Meals> getMeal();
    @GET("api/json/v1/1/random.php")
    Call<Meals> getMeal();

}
