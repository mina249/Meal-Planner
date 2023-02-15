package com.example.mealplaner.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealplaner.Meal;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
@Dao
public interface MealDAO {
    @Query("SELECT * From Meals")
    Observable<List<Meal>> getFavouriteMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMeal (Meal meal);
    @Delete
    Completable deleteMeal (Meal meal);
}
