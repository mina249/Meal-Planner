package com.example.mealplaner.Search.Ingrediant.Presenter;

import com.example.mealplaner.Models.AllIngrediantModel;
import com.example.mealplaner.Models.IngrediantModel;
import com.example.mealplaner.Models.Meals;
import com.example.mealplaner.RxNetwork.RxRepositry;
import com.example.mealplaner.Search.Ingrediant.InterFaces.IngrediantFilter;
import com.example.mealplaner.Search.Ingrediant.InterFaces.IngrediantSetData;
import com.example.mealplaner.Search.Ingrediant.View.IngrediantAdapter;

import java.util.ArrayList;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IngrediantPresenter {
    private IngrediantSetData ingrediantSetData;
    ArrayList<IngrediantModel>ingrediantList;
    Observable<AllIngrediantModel> modelObservable;
    IngrediantFilter ingrediantFilter;



    public IngrediantPresenter(IngrediantSetData ingrediantSetData,IngrediantFilter ingrediantFilter) {
        this.ingrediantSetData = ingrediantSetData;
        this.ingrediantFilter=ingrediantFilter;
    }

    public void setIngrediant() {
       modelObservable=RxRepositry.getIngrediants("list");
        modelObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingrediant -> {
                    ingrediantSetData.setIngrediant(ingrediant.getMeals());
                    ingrediantList=ingrediant.getMeals();
                        }
                );

    }
    public void filterIngrediant(String filter){
      ArrayList<IngrediantModel> temp= (ArrayList<IngrediantModel>) ingrediantList.stream().filter(iteams->{return iteams.getStrIngredient().startsWith(filter);})
                .collect(Collectors.toList());
      ingrediantFilter.filter(temp);
    }

}
