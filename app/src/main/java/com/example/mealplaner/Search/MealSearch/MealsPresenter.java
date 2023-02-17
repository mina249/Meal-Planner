package com.example.mealplaner.Search.MealSearch;

import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Models.Meals;
import com.example.mealplaner.RxNetwork.RxRepositry;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MealsPresenter {
    private SearchforMeal searchforMeal;

    public MealsPresenter(SearchforMeal searchforMeal) {
        this.searchforMeal = searchforMeal;
    }

    public void getMeals(String mealName){
       Observable<Meals> meals= RxRepositry.getSearchMeals(mealName);
       meals.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
               .subscribe(iteam->{
                           if(!iteam.getMeals().isEmpty())
                         searchforMeal.search(iteam.getMeals());
                       },error-> error.printStackTrace());
    }
}
