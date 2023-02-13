package com.example.mealplaner.Meal;

import android.util.Pair;

import com.example.mealplaner.Models.Meal;

import java.util.HashMap;
import java.util.LinkedList;

public interface MealInterface {
    void SetMealData(Meal meal , LinkedList<Pair<String, String>> ingridient);
}
