package com.example.mealplaner.Calendar;

import com.example.mealplaner.Models.Meal;

public interface CalendarPresenterInterface {
    public void getSaturdayMeals();
    public void getSundayMeals();
    public void getMondayMeals();
    public void getTuesDayMeals();
    public void getWednesdayMeals();
    public void getThursdayMeals();
    public void getFridayMeals();
    public void removeFromCalender(Meal meal);
}
