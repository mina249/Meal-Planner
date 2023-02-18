package com.example.mealplaner.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealplaner.Models.Meal;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
@Dao
public interface MealDAO {
    @Query("SELECT * From Meals WHERE STATUS LIKE 'favourite'")
    Observable<List<Meal>> getFavouriteMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMeal (Meal meal);
    @Delete
    Completable deleteMeal (Meal meal);

    @Query("SELECT * From Meals WHERE STATUS LIKE 'saturday'")
    Observable<List<Meal>> getSaturdayMeals();
    @Query("SELECT * From Meals WHERE STATUS LIKE 'sunday'")
    Observable<List<Meal>> getSundayMeals();
    @Query("SELECT * From Meals WHERE STATUS LIKE 'monday'")
    Observable<List<Meal>> getMondayMeals();
    @Query("SELECT * From Meals WHERE STATUS LIKE 'tuesday'")
    Observable<List<Meal>> getTuesdayMeals();
    @Query("SELECT * From Meals WHERE STATUS LIKE 'wednesday'")
    Observable<List<Meal>> getWednesdayMeals();
    @Query("SELECT * From Meals WHERE STATUS LIKE 'thursday'")
    Observable<List<Meal>> getThursdayMeals();
    @Query("SELECT * From Meals WHERE STATUS LIKE 'friday'")
    Observable<List<Meal>> getFridayMeals();

    @Query("DELETE FROM Meals")
    Completable deleteTableRecords();

}
