package com.example.mealplaner.HomePage.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.R;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder> {
    ArrayList<Meal> meals;
    ViewPager2 viewPager;

    public SliderAdapter(ViewPager2 viewPager) {
        meals=new ArrayList<>();
        this.viewPager=viewPager;
    }
    public void setMeals(ArrayList<Meal> meals){
        this.meals=meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recommended_home_rv,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setMeal(meals.get(position));
        holder.btnFavourit.setOnClickListener(view->{

        });
        if(position==meals.size()-2){
        viewPager.post(holder.runnable);
        }

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatAutoCompleteTextView daysDroplist;
        ImageView ivMeal;
        Button btnFavourit;
        private Runnable runnable= new Runnable() {
            @Override
            public void run() {
                meals.addAll(meals);
                notifyDataSetChanged();
            }
        };

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            daysDroplist=itemView.findViewById(R.id.recomended_dp);
            ivMeal=itemView.findViewById(R.id.iv_meal_pic);
            btnFavourit=itemView.findViewById(R.id.btn_favourit);
        }
        public void setMeal(Meal meal){
            Glide.with(ivMeal.getContext()).load(meal.getStrMealThumb()).into(ivMeal);
        }



    }
}
