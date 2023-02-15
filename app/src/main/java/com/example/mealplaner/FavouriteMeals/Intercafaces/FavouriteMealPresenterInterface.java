package com.example.mealplaner.FavouriteMeals.Intercafaces;

import com.example.mealplaner.Models.Meal;

public interface FavouriteMealPresenterInterface {
    public void getFavouriteMeals();
    public void removeFav(Meal meal);
}
