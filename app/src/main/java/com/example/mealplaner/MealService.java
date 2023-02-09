package com.example.mealplaner;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealService implements RemoteSource{

    private static final String BASE_URL = "https://www.themealdb.com/";

    private static MealService mealService = null;

    public  static MutableLiveData<ArrayList<meals>>  liveMeals = new MutableLiveData<ArrayList<meals>>();

    private MealService(){

    }

    public static MealService getInstance(){
        if(mealService==null){
            mealService = new MealService();
        }
        return mealService;
    }


    @Override
    public void enqueueCall(NetworkDelegate networkDelegate) {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create(gson)).build();
        APIMeal api = retrofit.create(APIMeal.class);
        Call<Meal> call = api.getMeal();

            call.enqueue(new Callback<Meal>() {
                @Override
                public void onResponse(Call<Meal> call, Response<Meal> response) {
                    networkDelegate.onSuccess(response.body().getMeals());
                    liveMeals.postValue(response.body().getMeals());
                }


                public void onFailure(Call<Meal> call, Throwable t) {
                    networkDelegate.onFailure(t.toString());
                }
            });




    }
}
