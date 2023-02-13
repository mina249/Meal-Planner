package com.example.mealplaner.Meal;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplaner.R;


import java.util.LinkedList;

public class MealIngridientAdapter extends RecyclerView.Adapter<MealIngridientAdapter.ViewHolder> {
    LinkedList<Pair<String, String>> mealIngrediants;

    public MealIngridientAdapter(LinkedList<Pair<String, String>> mealIngrediants) {
        this.mealIngrediants = mealIngrediants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view = inflater.inflate( R.layout.ingrediant_iteam,parent,false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.setIngridiant(mealIngrediants.get(position));
    }

    @Override
    public int getItemCount() {
        return mealIngrediants.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvIngrediantName,tvIngrediantQuantity;
        ImageView ivIngrediantPic;
        final String INGRIDIANT_Url="https://www.themealdb.com/images/ingredients/";

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngrediantName=itemView.findViewById(R.id.tv_ingrediant_name);
            tvIngrediantQuantity=itemView.findViewById(R.id.tv_ingridiant_quantity);
            ivIngrediantPic=itemView.findViewById(R.id.iv_ingrediant);
        }
        void setIngridiant(Pair<String,String> ingridiant){
            String fullIngrediantUrl=INGRIDIANT_Url+ingridiant.first+"-Small.png";
            tvIngrediantName.setText(ingridiant.first);
            tvIngrediantQuantity.setText(ingridiant.second);
            Glide.with(ivIngrediantPic.getContext()).load(fullIngrediantUrl).into(ivIngrediantPic);
        }
    }
}
