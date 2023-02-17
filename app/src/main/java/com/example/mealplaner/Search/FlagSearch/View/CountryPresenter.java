package com.example.mealplaner.Search.FlagSearch.View;



import com.example.mealplaner.Category.InterFace.MealInter;

import com.example.mealplaner.Models.Meals;
import com.example.mealplaner.RxNetwork.RxRepositry;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CountryPresenter {
    MealInter mealInter;

    public CountryPresenter(MealInter mealInter) {
        this.mealInter = mealInter;
    }
    public void setMeals(String country){
       Observable<Meals> observable= RxRepositry.getCountriesMeals(country);
       observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
               .subscribe(iteam->{
                   mealInter.setMeal(iteam.getMeals());
               });
    }
}
