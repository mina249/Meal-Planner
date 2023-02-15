package com.example.mealplaner.Calendar;

import com.example.mealplaner.DataBase.LocalSource;
import com.example.mealplaner.Models.Meal;

public class CalendarPresenter implements CalendarPresenterInterface{

    CalendarViewInterface calendarViewInterface;
    LocalSource localSource;

    public CalendarPresenter(CalendarViewInterface calendarViewInterface, LocalSource localSource) {
        this.calendarViewInterface = calendarViewInterface;
        this.localSource = localSource;
    }

    @Override
    public void getSaturdayMeals() {
        calendarViewInterface.showSaturday(localSource.getSaturdayProducts());
    }

    @Override
    public void getSundayMeals() {
        calendarViewInterface.showSunday(localSource.getSundayProducts());
    }

    @Override
    public void getMondayMeals() {
        calendarViewInterface.showMonday(localSource.getMondayProducts());
    }

    @Override
    public void getTuesDayMeals() {
        calendarViewInterface.showTuesday(localSource.getTuesdayProducts());
    }

    @Override
    public void getWednesdayMeals() {
        calendarViewInterface.showWednesday(localSource.getWednesdayProducts());
    }

    @Override
    public void getThursdayMeals() {
            calendarViewInterface.showThursday(localSource.getThursdayProducts());
    }

    @Override
    public void getFridayMeals() {
        calendarViewInterface.showFriday(localSource.getFridayProducts());
    }

    @Override
    public void removeFromCalender(Meal meal) {
        localSource.delete(meal);
    }


}
