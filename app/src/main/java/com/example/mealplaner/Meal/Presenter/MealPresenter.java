package com.example.mealplaner.Meal.Presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.mealplaner.DataBase.ConcreteLocalSource;
import com.example.mealplaner.Meal.Controllers.MealIngrediant;
import com.example.mealplaner.Meal.Interfaces.MealInterface;


import com.example.mealplaner.Meal.View.MealData;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Models.Meals;
import com.example.mealplaner.RxNetwork.RxRepositry;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MealPresenter {
    MealInterface mealInterface;
    Observable<Meals> mealObservable;

    MealIngrediant ingrediant;
    Context context;
    Boolean flag;


    public MealPresenter(MealInterface mealInterface, Context context) {
        this.mealInterface = mealInterface;
        ingrediant = new MealIngrediant();
        this.context = context;

    }

    public void getData(String id) {
        flag = false;
        ConcreteLocalSource localSource = ConcreteLocalSource.getInstance(context);
        Observable<List<Meal>> observable = localSource.getMeal(id);
        observable.observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(meals -> {
                    if (meals.size() > 0) {
                        flag = true;
                        ingrediant.setMeal(meals.get(0));
                        mealInterface.SetMealData(meals.get(0), ingrediant.getMealIngriant(),"DB");
                        Toast.makeText(context, "from db", Toast.LENGTH_SHORT).show();

                    }
                },err->{});

        if (flag == false) {
            mealObservable = RxRepositry.getMeal(id);
            mealObservable.subscribeOn(Schedulers.io()).map(Meals::getMeals)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(meal -> {
                                ingrediant.setMeal(meal.get(0));
                                mealInterface.SetMealData(meal.get(0), ingrediant.getMealIngriant(),"Network");
                                Toast.makeText(context, "from network", Toast.LENGTH_SHORT).show();

                            }
                   ,err->{} );
        }
    }

}
