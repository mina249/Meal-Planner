package com.example.mealplaner.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mealplaner.Models.Meal;
    @Database(entities = {Meal.class},version = 3)
    public abstract class AppDataBase extends RoomDatabase {
        private static AppDataBase instance = null;
        public abstract MealDAO mealDAO();
        public static synchronized AppDataBase getInstance(Context context){
            if (instance == null){
                instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "MealPlanner3")
                        .build(); }
            return instance; }
}
