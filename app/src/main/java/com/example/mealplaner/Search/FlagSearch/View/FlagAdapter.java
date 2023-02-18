package com.example.mealplaner.Search.FlagSearch.View;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.R;

import java.util.ArrayList;

public class FlagAdapter extends RecyclerView.Adapter<FlagAdapter.ViewHolder> {
    ArrayList<Meal> meals;

    public FlagAdapter() {
        meals=new ArrayList<>();
    }

    @NonNull
    @Override
    public FlagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.flag_iteam,parent,false);
        return new ViewHolder( view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlagAdapter.ViewHolder holder, int position) {
        if(!meals.get(position).getStrArea().equals("Unknown"))
            holder.setCountry(meals.get(position));
        holder.cvCountry.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("country",meals.get(position).getStrArea());
            Intent intent = new Intent(holder.iv.getContext(), CountryData.class);
            intent.putExtra("country",bundle);
            holder.cvCountry.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
    public void setCountry(ArrayList<Meal> meals){
        this.meals=meals;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvCountryName;
        ImageView iv ;
        Resources res;
        CardView cvCountry;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountryName=itemView.findViewById(R.id.tv_country_name);
            iv=itemView.findViewById(R.id.iv_flag);
            res=itemView.getResources();
            cvCountry=itemView.findViewById(R.id.cv_country_iteam);
        }
        void setCountry(Meal country){

          //  String imgPath="res/drawable/"+country.getStrArea().toLowerCase();
                tvCountryName.setText(country.getStrArea());
                int resourceId = res.getIdentifier(country.getStrArea().toLowerCase(), "drawable",
                        iv.getContext().getPackageName());//initialize res and context in adapter's contructor
                iv.setImageResource(resourceId);

        }
    }
}
