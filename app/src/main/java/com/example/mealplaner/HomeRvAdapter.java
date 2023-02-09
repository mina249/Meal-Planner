package com.example.mealplaner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HomeRvAdapter extends RecyclerView.Adapter<HomeRvAdapter.HomeRvHolder> {

    ArrayList<meals> meal;
    Context context;
    ViewPager2 viewPager2;


    public HomeRvAdapter(Context context,ArrayList<meals>meal,ViewPager2 viewPager2) {
        this.meal = meal;
        this.context = context;
        this.viewPager2= viewPager2;
    }

    @NonNull
    @Override
    public HomeRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater  = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.home_rv , parent , false);
        HomeRvHolder vh = new HomeRvHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRvHolder holder, int position) {
        meals meals = meal.get(position);
        holder.txt.setText(meals.getStrMeal());
        Glide.with(context).load(meals.getStrMealThumb()).into(holder.imageView);
        if(position== meal.size()-2){
            viewPager2.post(runnable);
            position=0;

        }



        holder.fav.setOnClickListener(new View.OnClickListener() {
            boolean isFavorite = false;

            @Override
            public void onClick(View v) {
                if (!isFavorite) {
                    holder.fav.setBackgroundResource(R.drawable.heart);

                    holder.imgMeal.animate().rotation(1440).setDuration(1000).setStartDelay(0).start();
                    isFavorite = true;
                } else {
                    holder.fav.setBackgroundResource(R.drawable.fav);
                    isFavorite = false;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void setMealList(ArrayList<meals>meal) {
       this.meal =meal;
        notifyDataSetChanged();
    }

    class HomeRvHolder extends RecyclerView.ViewHolder {
        TextView txt;
        ImageView imageView;
        Button fav;
        CardView imageCard;
        ImageView imgMeal;

        public HomeRvHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.meal_name);
            imageView = itemView.findViewById(R.id.img_meal);
            fav = itemView.findViewById(R.id.fav_button);
            imageCard = itemView.findViewById(R.id.image_card);
            imgMeal = itemView.findViewById(R.id.img_meal);
        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            meal.addAll(meal);
            notifyDataSetChanged();
        }
    };

}

