package com.example.mealplaner.Search.FlagSearch.Presenter;



import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Models.Meals;
import com.example.mealplaner.RxNetwork.RxRepositry;
import com.example.mealplaner.Search.FlagSearch.Interfaces.FilterCountries;
import com.example.mealplaner.Search.FlagSearch.Interfaces.FlagInterf;

import java.util.ArrayList;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FlagPrester {
    FlagInterf country;
    ArrayList<Meal>countries;
    FilterCountries filterCountries;

    public FlagPrester(FlagInterf country, FilterCountries filterCountries) {
        this.country = country;
        countries= new ArrayList<>();
        this.filterCountries=filterCountries;
    }
    public  void setData(){
        Observable <Meals> observable = RxRepositry.getCountries("list");
        observable.subscribeOn(Schedulers.io()).map(Meals::getMeals)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(country->{
                    this.country.setFlag(country);
                    countries=country;
                });

    }
    public void filterCountries(String countryName){
        ArrayList<Meal> tempCountries= (ArrayList<Meal>) countries.stream().filter(iteam->{return iteam.getStrArea().startsWith(countryName);})
                        .collect(Collectors.toList());
        filterCountries.filter(tempCountries);
    }
}
