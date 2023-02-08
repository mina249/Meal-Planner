package com.example.mealplaner.Clender.View;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Models.Meals;
import com.example.mealplaner.R;

import java.util.ArrayList;


public class Clender extends Fragment {
    DayMealsAdapter dayMealsAdapter;

    ArrayList<Meals> dayMeals;
    RecyclerView view;
    Context context;
    ArrayList<ArrayList<Meal>> weekMeals;

    public Clender(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        weekMeals = new ArrayList<>();

        dayMeals = new ArrayList<>();
        dayMeals.add(new Meals("Chikeen1", "Health"));
        dayMeals.add(new Meals("Chikeen2", "Health"));
        dayMeals.add(new Meals("Chikeen2", "Health"));
        dayMeals.add(new Meals("Chikeen3", "Health"));
        dayMeals.add(new Meals("Chikeen4", "Health"));
        dayMeals.add(new Meals("Chikeen5", "Health"));

        dayMealsAdapter = new DayMealsAdapter(getActivity(), weekMeals);

        return inflater.inflate(R.layout.fragment_clender, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view.findViewById(R.id.rv_daily_meals);
        this.view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        this.view.setAdapter(dayMealsAdapter);
    }


}