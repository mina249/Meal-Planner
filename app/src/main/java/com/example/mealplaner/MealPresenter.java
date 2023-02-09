package com.example.mealplaner;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;

public class MealPresenter implements MealPresenterInterface,NetworkDelegate{

    RemoteSource remoteSource;
    MealViewInterface mealViewInterface;
    ArrayList<meals> meals= new ArrayList<>();
    Context context;

    public MealPresenter(RemoteSource remoteSource, MealViewInterface mealViewInterface, Context context) {
        this.remoteSource = remoteSource;
        this.mealViewInterface = mealViewInterface;
        this.context = context;
    }

    @Override
    public void getMeal() {
        remoteSource.enqueueCall(this);

    }


    @Override
    public void onSuccess(ArrayList<meals>meals) {
            mealViewInterface.showData(meals);
            MealService.liveMeals.observe((LifecycleOwner) context,mealViewInterface::showData);

    }

    @Override
    public void onFailure(String error) {

    }
}
