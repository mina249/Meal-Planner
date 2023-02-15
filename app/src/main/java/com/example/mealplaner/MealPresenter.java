package com.example.mealplaner;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.example.mealplaner.DataBase.LocalSource;

import com.example.mealplaner.Models.Meal;

import java.util.ArrayList;

public class MealPresenter implements MealPresenterInterface,NetworkDelegate{

    RemoteSource remoteSource;
    MealViewInterface mealViewInterface;
    ArrayList<Meal> meals= new ArrayList<>();
    Context context;
    LocalSource localSource;

    public MealPresenter(RemoteSource remoteSource, MealViewInterface mealViewInterface, Context context,LocalSource localSource) {
        this.remoteSource = remoteSource;
        this.mealViewInterface = mealViewInterface;
        this.context = context;
        this.localSource = localSource;
    }

    @Override
    public void getMeal() {
        remoteSource.enqueueCall(this);

    }

    @Override
    public void addToFav(Meal meal) {
        localSource.insert(meal);
    }


    @Override
    public void onSuccess(ArrayList<Meal>meals) {
            mealViewInterface.showData(meals);
            MealService.liveMeals.observe((LifecycleOwner) context,mealViewInterface::showData);

    }

    @Override
    public void onFailure(String error) {

    }
}
