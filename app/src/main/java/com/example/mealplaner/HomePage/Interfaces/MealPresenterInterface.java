package com.example.mealplaner.HomePage.Interfaces;

import com.example.mealplaner.Models.Meal;

public interface MealPresenterInterface {
    public void getMeal();
    public void addToFav(Meal meal);
    public void getRecommendedMeal();
}
