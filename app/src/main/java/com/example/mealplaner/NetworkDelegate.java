package com.example.mealplaner;

import androidx.lifecycle.LiveData;

import com.example.mealplaner.Models.Meal;

import java.util.ArrayList;

public interface NetworkDelegate {
    public void onSuccess(ArrayList<Meal> meals);
    public void onFailure(String error);
}
