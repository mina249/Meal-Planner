package com.example.mealplaner.Search.Ingrediant.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mealplaner.Models.IngrediantModel;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.Ingrediant.InterFaces.IngrediantSetData;
import com.example.mealplaner.Search.Ingrediant.Presenter.IngrediantPresenter;

import java.util.ArrayList;


public class IngrediantSearch extends AppCompatActivity implements IngrediantSetData {

    RecyclerView rvIngrediant;
    IngrediantPresenter presenter;
    IngrediantAdapter ingrediantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingrediant_search);
        rvIngrediant=findViewById(R.id.rv_ingrediant);
         ingrediantAdapter = new IngrediantAdapter();
        presenter= new IngrediantPresenter(this);
        presenter.setIngrediant();
        rvIngrediant.setLayoutManager(new GridLayoutManager(this,3));
        rvIngrediant.setAdapter(ingrediantAdapter);


    }

    @Override
    public void setIngrediant(ArrayList<IngrediantModel> ingrediantList) {
       ingrediantAdapter.setList(ingrediantList);
    }
}