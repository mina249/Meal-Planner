package com.example.mealplaner.Clender.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.R;

import java.util.ArrayList;

public class ClenderIteamsAdapter extends RecyclerView.Adapter<ClenderIteamsAdapter.ViewHolder> {
    ArrayList<Meal> mealsArrayList;

    public ClenderIteamsAdapter(ArrayList<Meal> mealsArrayList) {
        this.mealsArrayList = mealsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cleander_meal_iteam, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setMeal(mealsArrayList.get(position));
        holder.ivRemove.setOnClickListener(view -> {
            mealsArrayList.remove(position);
            notifyDataSetChanged();
        });


    }

    @Override
    public int getItemCount() {
        return mealsArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMeal;
        TextView tvMealName;
        TextView tvMealDescrip;
        ImageView ivRemove;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMeal = itemView.findViewById(R.id.iv_meal);
            tvMealName = itemView.findViewById(R.id.tv_name);
            tvMealDescrip = itemView.findViewById(R.id.tv_Descript);
            ivRemove = itemView.findViewById(R.id.iv_remove);
            ivMeal.setImageResource(R.drawable.iconclos);
            ivRemove.setImageResource(R.drawable.iconclos);

        }

        public void setMeal(Meal meal) {
            tvMealName.setText(meal.getStrMeal());
          //  tvMealDescrip.setText(meal.());
        }
    }
}
