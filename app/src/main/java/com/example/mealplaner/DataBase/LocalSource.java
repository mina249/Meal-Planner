package com.example.mealplaner.DataBase;

import com.example.mealplaner.Models.Meal;

import java.util.List;

import io.reactivex.Observable;

public interface LocalSource {
    public void delete(Meal meal);
    public void insert(Meal meal);
    public Observable<List<Meal>> getFavoriteProducts();
    public Observable<List<Meal>> getSaturdayProducts();
    public Observable<List<Meal>> getSundayProducts();
    public Observable<List<Meal>> getMondayProducts();
    public Observable<List<Meal>> getTuesdayProducts();
    public Observable<List<Meal>> getWednesdayProducts();
    public Observable<List<Meal>> getThursdayProducts();
    public Observable<List<Meal>> getFridayProducts();

    public void deleteAllTable();

}
