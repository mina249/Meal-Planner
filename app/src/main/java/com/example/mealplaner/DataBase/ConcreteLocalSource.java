package com.example.mealplaner.DataBase;

import android.content.Context;

import com.example.mealplaner.Models.Meal;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ConcreteLocalSource implements LocalSource{

    private Context context;
    private MealDAO dao;
    private Observable<List<Meal>> favouriteMeals;
    private static ConcreteLocalSource repo = null;


    private ConcreteLocalSource(Context context) {
        this.context = context;
        AppDataBase db = AppDataBase.getInstance(context.getApplicationContext());
        dao = db.mealDAO();
        favouriteMeals = dao.getFavouriteMeals();
    }
    public static  ConcreteLocalSource getInstance(Context context){
        if(repo ==null){
            repo = new ConcreteLocalSource(context);
        }
        return repo;
    }
    @Override
    public void delete(Meal meal) {
        dao.deleteMeal(meal).subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void insert(Meal meal) {
        dao.insertMeal(meal).subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }

    @Override
    public Observable<List<Meal>> getFavoriteProducts() {
        return favouriteMeals;
    }

    @Override
    public Observable<List<Meal>> getSaturdayProducts() {
        return dao.getSaturdayMeals();
    }

    @Override
    public Observable<List<Meal>> getSundayProducts() {
        return dao.getSundayMeals();
    }

    @Override
    public Observable<List<Meal>> getMondayProducts() {
        return dao.getMondayMeals();
    }

    @Override
    public Observable<List<Meal>> getTuesdayProducts() {
        return dao.getTuesdayMeals();
    }

    @Override
    public Observable<List<Meal>> getWednesdayProducts() {
        return dao.getWednesdayMeals();
    }

    @Override
    public Observable<List<Meal>> getThursdayProducts() {
        return dao.getThursdayMeals();
    }

    @Override
    public Observable<List<Meal>> getFridayProducts() {
        return dao.getFridayMeals();
    }

    @Override
    public void deleteAllTable() {
        dao.deleteTableRecords().subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public Observable<List<Meal>> getMeal(String id) {
        return dao.getMeal(id);
    }


}
