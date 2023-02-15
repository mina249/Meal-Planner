package com.example.mealplaner;

import java.util.List;

import io.reactivex.Observable;

public interface FavouriteViewInterface {
    public void remove(Meal meal);
    public void showFav(Observable<List<Meal>> products);
}
