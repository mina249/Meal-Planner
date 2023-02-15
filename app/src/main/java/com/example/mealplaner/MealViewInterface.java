package com.example.mealplaner;

import java.util.ArrayList;

public interface MealViewInterface {
    public void showData(ArrayList<Meal>meals);
    public void addToFav(Meal meal);
}
