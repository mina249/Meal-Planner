package com.example.mealplaner.FavouriteMeals.Intercafaces;

import com.example.mealplaner.Models.Meal;

import java.util.List;

import io.reactivex.Observable;

public interface FavouriteViewInterface {
    public void remove(Meal meal);
    public void showFav(Observable<List<Meal>> products);
}
