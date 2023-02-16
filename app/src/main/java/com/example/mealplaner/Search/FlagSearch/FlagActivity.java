package com.example.mealplaner.Search.FlagSearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.R;
import com.example.mealplaner.Search.FlagSearch.Interfaces.FlagInterf;
import com.example.mealplaner.Search.FlagSearch.Presenter.FlagPrester;

import java.util.ArrayList;

public class FlagActivity extends AppCompatActivity implements FlagInterf {
    FlagAdapter flagAdapter;
    RecyclerView rvFlags;
    FlagPrester flagPrester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag);

        flagAdapter = new FlagAdapter();
        rvFlags=findViewById(R.id.rv_country);

        rvFlags.setLayoutManager(new LinearLayoutManager(this));
        rvFlags.setAdapter(flagAdapter);
       flagPrester =new FlagPrester(this);
        flagPrester.setData();
    }

    @Override
    public void setFlag(ArrayList<Meal> meals) {
        flagAdapter.setCountry(meals);
    }
}