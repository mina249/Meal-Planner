package com.example.mealplaner.Clender.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.R;

import java.util.ArrayList;

public  class DayMealsAdapter extends RecyclerView.Adapter<DayMealsAdapter.ViewHolder> {
    ArrayList<String> daysList;
    Context context;

    ArrayList<ArrayList<Meal>> meals;

    public DayMealsAdapter(Context context, ArrayList<ArrayList<Meal>> meals) {
        daysList = new ArrayList<>();
        daysList.add("Sunday");
        daysList.add("Monday");
        daysList.add("Tuesday");
        daysList.add("Wednesday");
        daysList.add("Thursday");
        daysList.add("Friday");
        daysList.add("Saturday");
        this.context = context;
        this.meals = meals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cleander_iteam, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClenderIteamsAdapter iteamsAdapter = new ClenderIteamsAdapter(meals.get(position));
        holder.tvDay.setText(daysList.get(position));
        holder.rvMeals.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        holder.rvMeals.setAdapter(iteamsAdapter);
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay;
        RecyclerView rvMeals;
        ImageView addMeal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tv_day);
            rvMeals = itemView.findViewById(R.id.rv_meals_per_day);
            addMeal = itemView.findViewById(R.id.iv_add_meal);
        }
    }
}