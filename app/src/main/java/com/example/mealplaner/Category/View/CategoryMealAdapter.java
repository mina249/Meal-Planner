package com.example.mealplaner.Category.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.R;

import java.util.ArrayList;

public class CategoryMealAdapter extends RecyclerView.Adapter<CategoryMealAdapter.ViewHolder> {
    ArrayList<Meal> meals;

    public CategoryMealAdapter(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setMeal(meals.get(position));


    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivMeal;
        TextView tvMealName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMeal=itemView.findViewById(R.id.category_iv);
            tvMealName=itemView.findViewById(R.id.tv_category_name);
        }
        public void setMeal(Meal meal){
            tvMealName.setText(meal.getStrMeal());
            Glide.with(ivMeal.getContext()).load(meal.getStrMealThumb()).into(ivMeal);

        }
    }
}
