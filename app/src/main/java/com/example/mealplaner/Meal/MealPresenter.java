package com.example.mealplaner.Meal;

import android.util.Log;

import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Models.Meals;
import com.example.mealplaner.RxNetwork.RxRepositry;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MealPresenter {
    MealInterface mealInterface;
    Observable<Meals> mealObservable;
    Meal meal;

    public MealPresenter(MealInterface mealInterface) {

        this.mealInterface = mealInterface;
    System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");

    }
    void getData(String id){
        System.out.println(id);
        mealObservable= RxRepositry.getMeal(id);
        mealObservable.subscribeOn(Schedulers.io()).map(Meals::getMeals).observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                            mealInterface.SetMealData(meal.get(0), "ssssssss");
                        },er->{
                            System.out.println( "MealPresenter: erorrrrrrrrrr");
                        }
                );
    }

}
