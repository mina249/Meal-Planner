package com.example.mealplaner.RxNetwork;


import com.example.mealplaner.Models.AllIngrediantModel;
import com.example.mealplaner.Models.Meals;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RxAPI {
    @GET("/api/json/v1/1/lookup.php?")
    Observable<Meals> getMeal(@Query("i") String id);
    @GET("/api/json/v1/1/list.php?")
    Observable<AllIngrediantModel>getIngrediants(@Query("i") String name);
    @GET("/api/json/v1/1/filter.php?")
    Observable<Meals>getIngrediantMeals(@Query("i") String name);
    @GET("/api/json/v1/1/list.php?")
    Observable<Meals>getCountries(@Query("a") String country);

}
