package com.example.mealplaner.Meal.Interfaces;

import android.util.Pair;

import com.example.mealplaner.Models.Meal;

import java.util.LinkedList;

public interface MealInterface {
    void SetMealData(Meal meal , LinkedList<Pair<String, String>> ingridient);
}
