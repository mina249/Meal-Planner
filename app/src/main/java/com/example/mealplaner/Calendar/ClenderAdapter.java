package com.example.mealplaner.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplaner.FavouriteMeals.Intercafaces.OnDeleteFromFavClickListener;
import com.example.mealplaner.Meal.View.MealData;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Network.FireBaseData;
import com.example.mealplaner.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ClenderAdapter extends RecyclerView.Adapter<ClenderAdapter.ViewHolder> {
    OnDeleteFromFavClickListener delete;
    ArrayList<Meal> mealList;
    CardView dayCV;
    public ClenderAdapter(OnDeleteFromFavClickListener delete, CardView dayCV) {
        this.mealList = new ArrayList<>();
        this.delete=delete;
        this.dayCV=dayCV;
    }
    public void setMealList(ArrayList<Meal> meals){
        mealList=meals;
        if(mealList.size()>0)
            dayCV.setVisibility(View.VISIBLE);
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
            if(mealList.size()-1==0){
                dayCV.setVisibility(View.GONE);
            }
            delete.onDeleteClick(mealList.get(position));
            FireBaseData.removePlanFromFireBase(view.getContext(), mealList.get(position));
            mealList.remove(position);
            notifyDataSetChanged();

        });
        holder.cvMealPlanIteam.setOnClickListener(view->{
            Bundle bundle = new Bundle();
            bundle.putString("id",mealList.get(position).getIdMeal());
            Intent intent = new Intent(holder.btnDelete.getContext(), MealData.class);
            intent.putExtra("id",bundle);
            holder.btnDelete.getContext().startActivity(intent);
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
        AppCompatAutoCompleteTextView droplistPlan;
        CardView cvMealPlanIteam;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealphoto=itemView.findViewById(R.id.meal_img_fav);
            btnDelete=itemView.findViewById(R.id.delete_btn_fav);
            tvMealName=itemView.findViewById(R.id.tv_fav_meal_name);
            droplistPlan=itemView.findViewById(R.id.dp_fav);
            droplistPlan.setVisibility(View.GONE);
            cvMealPlanIteam=itemView.findViewById(R.id.cv_meal_plan_iteam);
        }
        void setDaylyMeal(Meal meal){
            tvMealName.setText(meal.getStrMeal());
            Glide.with(mealphoto.getContext()).load(meal.getStrMealThumb()).into(mealphoto);
        }
    }

}
