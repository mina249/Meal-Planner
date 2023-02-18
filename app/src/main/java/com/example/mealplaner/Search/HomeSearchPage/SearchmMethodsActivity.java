package com.example.mealplaner.Search.HomeSearchPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.mealplaner.R;
import com.example.mealplaner.Search.Category.View.CategorySearch;
import com.example.mealplaner.Search.Ingrediant.View.IngrediantSearch;

public class SearchmMethodsActivity extends AppCompatActivity {
    Button btnCat,btnIngr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
       /* btnCat=findViewById(R.id.btn_category);
       // btnIngr=findViewById(R.id.btn_ingridiant);
        /*btnCat.setOnClickListener(view->{
            Intent intent = new Intent(this, CategorySearch.class);
            startActivity(intent);
        });
        btnIngr.setOnClickListener(view->{
            Intent intent = new Intent(this, IngrediantSearch.class);
            startActivity(intent);
        });*/
    }
}