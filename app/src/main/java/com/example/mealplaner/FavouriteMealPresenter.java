package com.example.mealplaner;

import com.example.mealplaner.DataBase.LocalSource;

public class FavouriteMealPresenter implements FavouriteMealPresenterInterface{
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
