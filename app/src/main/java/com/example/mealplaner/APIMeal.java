package com.example.mealplaner;


import retrofit2.Call;
import retrofit2.http.GET;

public interface APIMeal {
    @GET("api/json/v1/1/random.php")
    Call<Meals> getMeal();

}
