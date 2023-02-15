package com.example.mealplaner.HomePage.Interfaces;

import com.example.mealplaner.Models.Meal;

import java.util.ArrayList;

public interface MealViewInterface {
    public void showData(ArrayList<Meal>meals);
    public void addToFav(Meal meal);
}
