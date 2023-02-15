package com.example.mealplaner;

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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClenderAdapter extends RecyclerView.Adapter<ClenderAdapter.ViewHolder> {
    ArrayList<Meal> mealList;

    public ClenderAdapter() {
        this.mealList = new ArrayList<>();
    }
    public void setMealList(ArrayList<Meal> meals){
        mealList=meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favorite_rv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setDaylyMeal(mealList.get(position));
        holder.btnDelete.setOnClickListener(view->{
            mealList.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView mealphoto;
        Button btnDelete;
        TextView tvMealName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealphoto=itemView.findViewById(R.id.meal_img_fav);
            btnDelete=itemView.findViewById(R.id.delete_btn_fav);
            tvMealName=itemView.findViewById(R.id.tv_fav_meal_name);
        }
        void setDaylyMeal(Meal meal){
            tvMealName.setText(meal.getStrMeal());
            Glide.with(mealphoto.getContext()).load(meal.getStrMealThumb()).into(mealphoto);
        }
    }
}
