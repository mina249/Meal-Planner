package com.example.mealplaner;

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

import java.util.ArrayList;


public class Clender extends Fragment {
    ClenderIteamsAdapter iteamsAdapter;
    ArrayList<Meals> meals;
    RecyclerView view;
    Context context;

    public Clender(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        meals = new ArrayList<>();
        meals.add(new Meals("Chikeen", "Health"));
        meals.add(new Meals("Chikeen", "Health"));
        meals.add(new Meals("Chikeen", "Health"));
        meals.add(new Meals("Chikeen", "Health"));
        meals.add(new Meals("Chikeen", "Health"));
        meals.add(new Meals("Chikeen", "Health"));
        iteamsAdapter = new ClenderIteamsAdapter(meals);

        return inflater.inflate(R.layout.fragment_clender, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       this. view = view.findViewById(R.id.rv_selected_meals);
        this.view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        this.view.setAdapter(iteamsAdapter);
    }
}