package com.example.mealplaner.DataBase;

import com.example.mealplaner.Models.Meal;

import java.util.List;

import io.reactivex.Observable;

public interface LocalSource {
    public void delete(Meal meal);
    public void insert(Meal meal);
    public Observable<List<Meal>> getFavoriteProducts();
}
