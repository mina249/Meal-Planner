package com.example.mealplaner.RxNetwork;

import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Models.Meals;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxRepositry {
    public  static Observable getMeal(String id){
        Observable<Meals> mealObservable =RxConnection.getPostApi().getMeal(id);
        mealObservable.observeOn(Schedulers.io()).map(Meals::getMeals).
                subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> System.out.println(meals));

        return mealObservable;

    }

}
