package com.example.mealplaner.FavouriteMeals.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.FavouriteMeals.Intercafaces.OnDeleteFromFavClickListener;
import com.example.mealplaner.R;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    List<Meal> Meals;
    Context context;
    OnDeleteFromFavClickListener listener;

    public FavoriteAdapter(Context context, List<Meal> Meals, OnDeleteFromFavClickListener listener) {
        this.context = context;
        this.Meals = Meals;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteAdapter.FavoriteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Meal meal = Meals.get(position);
        holder.mealName.setText(meal.getStrMeal());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.mealImg);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClick(meal);
            }
        });
    }

    public void setList(List<Meal> Meals){
        this.Meals = Meals;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Meals.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder{
        TextView mealName;
        ImageView mealImg;
        Button delete;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImg = itemView.findViewById(R.id.meal_img_fav);
            mealName = itemView.findViewById(R.id.tv_fav_meal_name);
            delete= itemView.findViewById(R.id.delete_btn_fav);
        }
    }
}
