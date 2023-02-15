package com.example.mealplaner;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealService implements RemoteSource{

    private static final String BASE_URL = "https://www.themealdb.com/";

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


    @Override
    public void enqueueCall(NetworkDelegate networkDelegate) {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        APIMeal api = retrofit.create(APIMeal.class);
        Call<Meals> call = api.getMeal();
        callRandomMeal(call,networkDelegate);

    }
    public void callRandomMeal(Call<Meals>call, NetworkDelegate networkDelegate){



            call.enqueue(new Callback<Meals>() {
                @Override
                public void onResponse(Call<Meals> call, Response<Meals> response) {
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


                public void onFailure(Call<Meals> call, Throwable t) {
                    networkDelegate.onFailure(t.toString());
                }
            });
    }


}
