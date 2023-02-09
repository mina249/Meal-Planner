package com.example.mealplaner;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;

public interface NetworkDelegate {
    public void onSuccess(ArrayList<meals> meals);
    public void onFailure(String error);
}
