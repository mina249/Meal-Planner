package com.example.mealplaner.Search.FlagSearch.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.SearchView;

import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.FlagSearch.Interfaces.FilterCountries;
import com.example.mealplaner.Search.FlagSearch.Interfaces.FlagInterf;
import com.example.mealplaner.Search.FlagSearch.Presenter.FlagPrester;


import java.util.ArrayList;

public class FlagActivity extends AppCompatActivity implements FlagInterf , FilterCountries {
    FlagAdapter flagAdapter;
    RecyclerView rvFlags;
    FlagPrester flagPrester;
    SearchView countySearch;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag);
        countySearch=findViewById(R.id.country_search);
        countySearch.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        btnBack=findViewById(R.id.btn_back);
        flagAdapter = new FlagAdapter();
        rvFlags=findViewById(R.id.rv_search_meal);

        rvFlags.setLayoutManager(new LinearLayoutManager(this));
        rvFlags.setAdapter(flagAdapter);
       flagPrester =new FlagPrester(this,this);
        flagPrester.setData();
        countySearch.setMaxWidth(700);
        countySearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                flagPrester.filterCountries(s);
                return false;
            }
        });
        btnBack.setOnClickListener(view->{
            this.finish();
        });
    }

    @Override
    public void setFlag(ArrayList<Meal> meals) {
        flagAdapter.setCountry(meals);
    }

    @Override
    public void filter(ArrayList<Meal> meals) {
        flagAdapter.setCountry(meals);
    }
}