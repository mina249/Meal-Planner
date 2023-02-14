package com.example.mealplaner.IngridiantData;

import com.example.mealplaner.Category.InterFace.MealInter;
import com.example.mealplaner.Models.AllIngrediantModel;
import com.example.mealplaner.Models.Meals;
import com.example.mealplaner.RxNetwork.RxRepositry;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IngridiantMealPresenter {
    MealInter mealInter;
    Observable<Meals> observable;

    public IngridiantMealPresenter(MealInter mealInter) {
        this.mealInter = mealInter;
    }
    public void setMeals(String ingridatName){
      observable=  RxRepositry.getIngrediantMeals(ingridatName);
      observable.subscribeOn(Schedulers.io()).map(Meals::getMeals).observeOn(AndroidSchedulers.mainThread())
              .subscribe(meals->{mealInter.setMeal(meals);}
      );
    }
}
