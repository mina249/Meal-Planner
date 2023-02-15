package com.example.mealplaner;

import java.util.ArrayList;

public interface NetworkDelegate {
    public void onSuccess(ArrayList<Meal> meals);
    public void onFailure(String error);
}
