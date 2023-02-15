package com.example.mealplaner.FavouriteMeals.Presenter;

import com.example.mealplaner.DataBase.LocalSource;
import com.example.mealplaner.FavouriteMeals.Intercafaces.FavouriteMealPresenterInterface;
import com.example.mealplaner.FavouriteMeals.Intercafaces.FavouriteViewInterface;
import com.example.mealplaner.Models.Meal;

public class FavouriteMealPresenter implements FavouriteMealPresenterInterface {
    FavouriteViewInterface favouriteViewInterface;
    LocalSource localSource;

    public FavouriteMealPresenter(FavouriteViewInterface favouriteViewInterface,LocalSource localSource){
        this.favouriteViewInterface= favouriteViewInterface;
        this.localSource = localSource;
    }
    @Override
    public void getFavouriteMeals() {
        favouriteViewInterface.showFav(localSource.getFavoriteProducts());
    }

    @Override
    public void removeFav(Meal meal) {
        localSource.delete(meal);
    }


}
