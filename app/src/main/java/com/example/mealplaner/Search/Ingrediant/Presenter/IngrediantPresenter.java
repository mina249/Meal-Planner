package com.example.mealplaner.Search.Ingrediant.Presenter;

import com.example.mealplaner.Models.AllIngrediantModel;
import com.example.mealplaner.Models.IngrediantModel;
import com.example.mealplaner.Models.Meals;
import com.example.mealplaner.RxNetwork.RxRepositry;
import com.example.mealplaner.Search.Ingrediant.InterFaces.IngrediantSetData;
import com.example.mealplaner.Search.Ingrediant.View.IngrediantAdapter;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IngrediantPresenter {
    private IngrediantSetData ingrediantSetData;
    ArrayList<IngrediantModel>list;
    Observable<AllIngrediantModel> modelObservable;



    public IngrediantPresenter(IngrediantSetData ingrediantSetData) {
        this.ingrediantSetData = ingrediantSetData;

    }

    public void setIngrediant() {
       modelObservable=RxRepositry.getIngrediants("list");
        modelObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingrediant -> {
                    System.out.println(ingrediant);
                    ingrediantSetData.setIngrediant(ingrediant.getMeals());
                        }
                );

    }

}
