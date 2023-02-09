package com.example.mealplaner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplaner.Models.Meal;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    ArrayList<Meal> meals;
    Context context;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteAdapter.FavoriteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
          //  holder.mealImg.setImageResource(meals.get(position).getImg());
           // holder.mealName.setText(meals.get(position).getName());
    }

    public void setList(ArrayList<Meal>meals){
        this.meals = meals;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return meals.size();
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
