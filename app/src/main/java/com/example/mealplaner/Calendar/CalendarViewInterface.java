package com.example.mealplaner.Calendar;

import com.example.mealplaner.Models.Meal;

import java.util.List;

import io.reactivex.Observable;

public interface CalendarViewInterface {
    public void showSaturday(Observable<List<Meal>> products);
    public void showSunday(Observable<List<Meal>> products);
    public void showMonday(Observable<List<Meal>> products);
    public void showTuesday(Observable<List<Meal>> products);
    public void showWednesday(Observable<List<Meal>> products);
    public void showThursday(Observable<List<Meal>> products);
    public void showFriday(Observable<List<Meal>> products);
    public void remove(Meal meal);
}
