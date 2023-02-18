package com.example.mealplaner.FavouriteMeals.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplaner.FavouriteMeals.Intercafaces.OnDeleteFromFavClickListener;
import com.example.mealplaner.HomePage.Interfaces.OnAddToFavouriteClickListener;
import com.example.mealplaner.Meal.View.MealData;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.FavouriteMeals.Intercafaces.OnDeleteFromFavClickListener;
import com.example.mealplaner.Network.FireBaseData;
import com.example.mealplaner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    List<Meal> Meals;
    Context context;
    OnDeleteFromFavClickListener listener;
    OnAddToFavouriteClickListener favouriteClickListener;

    FirebaseAuth auth;
    FirebaseUser user;


    public FavoriteAdapter(Context context, List<Meal> Meals, OnDeleteFromFavClickListener listener, OnAddToFavouriteClickListener favouriteClickListener) {
        this.context = context;
        this.Meals = Meals;
        this.listener = listener;
        notifyDataSetChanged();
        this.favouriteClickListener = favouriteClickListener;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteAdapter.FavoriteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Meal meals = Meals.get(position);
        holder.mealName.setText(meals.getStrMeal());
        Glide.with(context).load(meals.getStrMealThumb()).into(holder.mealImg);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClick(meals);
                FireBaseData.removeFavouriteFromFirebase(context,meals);
            }
        });
        String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, days);
        holder.autoCompleteTextView.setAdapter(adapter);
        holder.autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user != null) {
                    holder.autoCompleteTextView.showDropDown();
                } else {
                    Toast.makeText(context, "You Should Login first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String day = parent.getItemAtPosition(position).toString();
                switch (day) {
                    case "Saturday":
                        meals.setStatus("saturday");
                        favouriteClickListener.onClick(meals);
                        Toast.makeText(context, "Meal added to " + meals.getStatus(), Toast.LENGTH_SHORT).show();

                        break;
                    case "Sunday":
                        meals.setStatus("sunday");
                        favouriteClickListener.onClick(meals);
                        Toast.makeText(context, "Meal added to " + meals.getStatus(), Toast.LENGTH_SHORT).show();
                        break;
                    case "Monday":
                        meals.setStatus("monday");
                        favouriteClickListener.onClick(meals);
                        Toast.makeText(context, "Meal added to " + meals.getStatus(), Toast.LENGTH_SHORT).show();
                        break;
                    case "Tuesday":
                        meals.setStatus("tuesday");
                        favouriteClickListener.onClick(meals);
                        Toast.makeText(context, "Meal added to " + meals.getStatus(), Toast.LENGTH_SHORT).show();
                        break;

                    case "Wednesday":
                        meals.setStatus("wednesday");
                        favouriteClickListener.onClick(meals);
                        Toast.makeText(context, "Meal added to " + meals.getStatus(), Toast.LENGTH_SHORT).show();
                        break;

                    case "Thursday":
                        meals.setStatus("thursday");
                        favouriteClickListener.onClick(meals);
                        Toast.makeText(context, "Meal added to " + meals.getStatus(), Toast.LENGTH_SHORT).show();
                        break;
                    case "Friday":
                        meals.setStatus("friday");
                        favouriteClickListener.onClick(meals);
                        Toast.makeText(context, "Meal added to " + meals.getStatus(), Toast.LENGTH_SHORT).show();
                        break;


                }
            }
        });
        holder.cvMealPlanIteam.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", Meals.get(position).getIdMeal());
            Intent intent = new Intent(holder.delete.getContext(), MealData.class);
            intent.putExtra("id", bundle);
            holder.delete.getContext().startActivity(intent);
        });


    }

    public void setList(List<Meal> Meals) {
        this.Meals = Meals;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Meals.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImg;
        Button delete;
        AutoCompleteTextView autoCompleteTextView;
        CardView cvMealPlanIteam;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImg = itemView.findViewById(R.id.meal_img_fav);
            mealName = itemView.findViewById(R.id.tv_fav_meal_name);
            delete = itemView.findViewById(R.id.delete_btn_fav);
            autoCompleteTextView = itemView.findViewById(R.id.dp_fav);
            cvMealPlanIteam = itemView.findViewById(R.id.cv_meal_plan_iteam);

        }
    }
}
