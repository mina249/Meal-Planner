package com.example.mealplaner.Network;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Models.Meals;
import com.example.mealplaner.Network.Interfaces.APIs;
import com.example.mealplaner.Network.Interfaces.NetworkDelegate;
import com.example.mealplaner.Network.Interfaces.RemoteSource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MealService implements RemoteSource {

    private  final String BASE_URL = "https://www.themealdb.com/";

    private static MealService mealService = null;

    public  static MutableLiveData<ArrayList<Meal>>  liveMeals = new MutableLiveData<ArrayList<Meal>>();
    int number = 10;
    ArrayList<Meal> meals = new ArrayList<>();
    private MealService(){

    }

    public static MealService getInstance(){
        if(mealService==null){
            mealService = new MealService();
        }
        return mealService;
    }



    public void callRandomMeal(Call<Meals>call, NetworkDelegate networkDelegate){

        call.enqueue(new Callback<Meals>() {
                @Override
                public void onResponse(@NonNull Call<Meals> call, Response<Meals> response) {
                    if(number>0)
                    {
                        enqueueCall(networkDelegate);
                        meals.add(response.body().getMeals().get(0));
                        number--;
                    }else {
                        networkDelegate.onSuccess(meals);
                        liveMeals.postValue(meals);
                    }
                }


                public void onFailure(@NonNull Call<Meals> call, Throwable t) {
                    networkDelegate.onFailure(t.toString());
                }
            });
    }


    @Override
    public void enqueueCall(NetworkDelegate networkDelegate) {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        APIs api = retrofit.create(APIs.class);
        Call<Meals> call = api.getRandomMeal();
        callRandomMeal(call,networkDelegate);
    }
}
