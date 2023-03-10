package com.example.mealplaner.RxNetwork;

import com.example.mealplaner.Models.AllIngrediantModel;
import com.example.mealplaner.Models.Meals;

import io.reactivex.Observable;

public class RxRepositry {
    public  static Observable getMeal(String id){
        Observable<Meals> mealObservable =RxConnection.getPostApi().getMeal(id);


        return mealObservable;
    }
    public static  Observable  getIngrediants(String ingridiant){
        Observable<AllIngrediantModel> ingrediantModelObservable =
                RxConnection.getPostApi().getIngrediants(ingridiant);
        return ingrediantModelObservable;
    }
    public static  Observable  getIngrediantMeals(String ingridiant){
        Observable<Meals> ingrediantModelObservable =
                RxConnection.getPostApi().getIngrediantMeals(ingridiant);
        return ingrediantModelObservable;
    }
    public static  Observable getCountries(String country){
        Observable<Meals> contryObservable=
                RxConnection.getPostApi().getCountries(country);
        return contryObservable;
    }
    public static  Observable getCountriesMeals(String country){
        Observable<Meals> contryObservable=
                RxConnection.getPostApi().getCountriesMeals(country);
        return contryObservable;
    }
    public static  Observable getSearchMeals(String meal){
        Observable<Meals> contryObservable=
                RxConnection.getPostApi().getSearchMeals(meal);
        return contryObservable;
    }

}
