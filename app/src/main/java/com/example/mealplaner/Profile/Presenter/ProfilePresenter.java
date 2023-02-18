package com.example.mealplaner.Profile.Presenter;

import android.content.Context;

import com.example.mealplaner.DataBase.LocalSource;

public class ProfilePresenter implements ProfilePresenyterInterface{
    LocalSource localSource;

    public ProfilePresenter(LocalSource localSource) {
        this.localSource = localSource;
    }

    @Override
    public void deleteTable() {
        localSource.deleteAllTable();
    }
}
