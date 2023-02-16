package com.example.mealplaner.Search.FlagSearch.Presenter;



import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Models.Meals;
import com.example.mealplaner.RxNetwork.RxRepositry;
import com.example.mealplaner.Search.FlagSearch.Interfaces.FlagInterf;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FlagPrester {
    FlagInterf country;

    public FlagPrester(FlagInterf country) {
        this.country = country;
    }
    public  void setData(){
        Observable <Meals> observable = RxRepositry.getCountries("list");
        observable.subscribeOn(Schedulers.io()).map(Meals::getMeals)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(country->{
                    this.country.setFlag(country);
                });


    }
}
