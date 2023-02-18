package com.example.mealplaner.Search.Ingrediant.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplaner.IngridiantData.IngridiantActivity;
import com.example.mealplaner.Models.AllIngrediantModel;
import com.example.mealplaner.Models.IngrediantModel;
import com.example.mealplaner.R;

import java.util.ArrayList;

public class IngrediantAdapter extends RecyclerView.Adapter<IngrediantAdapter.ViewHolder> {
    ArrayList<IngrediantModel> ingrediantList;

    public IngrediantAdapter() {
        ingrediantList = new ArrayList<>();
    }
    public void setList(ArrayList<IngrediantModel> list)
    {
        this.ingrediantList=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.igrediant_search_iteam,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setIngrediant(ingrediantList.get(position).getStrIngredient());
        holder.cvIngrediant.setOnClickListener(view->{
            Bundle bundle = new Bundle();
            bundle.putSerializable("Ingrediant",ingrediantList.get(position));
            Intent intent = new Intent(holder.cvIngrediant.getContext(), IngridiantActivity.class);
            intent.putExtra("Ingrediant",bundle);
            holder.cvIngrediant.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ingrediantList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivIngrediant;
        TextView tvIngrediantName;
        CardView cvIngrediant;
        final String INGRIDIANT_Url="https://www.themealdb.com/images/ingredients/";

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIngrediant=itemView.findViewById(R.id.iv_ingreadient_iteam_image);
            tvIngrediantName=itemView.findViewById(R.id.tv_ingrediant_iteam_name);
            cvIngrediant=itemView.findViewById(R.id.cv_ing_item);
        }
        void setIngrediant(String ingrediantName){
            String ingrediantUrl= INGRIDIANT_Url+ingrediantName+"-Small.png";
            tvIngrediantName.setText(ingrediantName);
            Glide.with(ivIngrediant.getContext()).load(ingrediantUrl).into(ivIngrediant);
        }
    }
}
