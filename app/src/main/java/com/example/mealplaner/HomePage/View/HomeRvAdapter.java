package com.example.mealplaner.HomePage.View;

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
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.mealplaner.HomePage.Interfaces.OnAddToFavouriteClickListener;
import com.example.mealplaner.Meal.View.MealData;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.FavouriteMeals.Intercafaces.OnDeleteFromFavClickListener;
import com.example.mealplaner.Network.FireBaseData;
import com.example.mealplaner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeRvAdapter extends RecyclerView.Adapter<HomeRvAdapter.HomeRvHolder> {

    ArrayList<Meal> meal;
    Context context;
    ViewPager2 viewPager2;
    OnAddToFavouriteClickListener listener;
    OnDeleteFromFavClickListener deleteListener;

    FirebaseAuth auth;
    FirebaseUser user;


    public HomeRvAdapter(Context context, ArrayList<Meal>meal, ViewPager2 viewPager2, OnAddToFavouriteClickListener listener, OnDeleteFromFavClickListener deleteListener) {
        this.meal = meal;
        this.context = context;
        this.viewPager2= viewPager2;
        this.listener =listener;
        this.deleteListener = deleteListener;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
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

        Meal meals = meal.get(position);
        holder.txt.setText(meals.getStrMeal());
        Glide.with(context).load(meals.getStrMealThumb()).into(holder.imageView);
        holder.itemCard.setOnClickListener(view->{
            Bundle bundle = new Bundle();
            bundle.putString("id",meals.getIdMeal());
            Intent intent = new Intent(context, MealData.class);
            intent.putExtra("id",bundle);
            context.startActivity(intent);
        });
        if(meals.getStatus()=="favourite"){
            holder.fav.setBackgroundResource(R.drawable.heart);
        }
        String [] days ={"Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,days);
        holder.autoCompleteTextView.setAdapter(adapter);
        holder.autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(user!=null) {
                        holder.autoCompleteTextView.showDropDown();
                    }else{
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
                        listener.onClick(meals);
                        FireBaseData.addPlanToFirebase(context,meals);
                        Toast.makeText(context, "Meal added to Saturday", Toast.LENGTH_SHORT).show();

                        break;
                    case "Sunday":
                        meals.setStatus("sunday");
                        listener.onClick(meals);
                        FireBaseData.addPlanToFirebase(context,meals);
                        Toast.makeText(context, "Meal added to Sunday ", Toast.LENGTH_SHORT).show();
                        break;
                    case "Monday":
                        meals.setStatus("monday");
                        listener.onClick(meals);
                        FireBaseData.addPlanToFirebase(context,meals);
                        Toast.makeText(context, "Meal added to Monday", Toast.LENGTH_SHORT).show();
                        break;
                    case "Tuesday":
                        meals.setStatus("tuesday");
                        listener.onClick(meals);
                        FireBaseData.addPlanToFirebase(context,meals);
                        Toast.makeText(context, "Meal added to Tuesday", Toast.LENGTH_SHORT).show();
                        break;

                    case "Wednesday":
                        meals.setStatus("wednesday");
                        listener.onClick(meals);
                        FireBaseData.addPlanToFirebase(context,meals);
                        Toast.makeText(context, "Meal added to Wednesday ", Toast.LENGTH_SHORT).show();
                        break;

                    case "Thursday":
                        meals.setStatus("thursday");
                        listener.onClick(meals);
                        FireBaseData.addPlanToFirebase(context,meals);
                        Toast.makeText(context, "Meal added to Thursday", Toast.LENGTH_SHORT).show();
                        break;
                    case "Friday":
                        meals.setStatus("friday");
                        listener.onClick(meals);
                        FireBaseData.addPlanToFirebase(context,meals);
                        Toast.makeText(context, "Meal added to Friday ", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        holder.fav.setOnClickListener(new View.OnClickListener() {
            boolean isFavorite = false;

            @Override
            public void onClick(View v) {
                if(user!=null) {
                    if (!isFavorite) {
                        holder.fav.setBackgroundResource(R.drawable.heart);
                        meals.setStatus("favourite");
                        listener.onClick(meals);
                        FireBaseData.addFavouriteToFirebase(context,meals);

                        holder.imgMeal.animate().rotationBy(1440).setDuration(1000).setStartDelay(0).start();
                        isFavorite = true;
                    } else {
                        holder.fav.setBackgroundResource(R.drawable.fav);
                        isFavorite = false;
                        deleteListener.onDeleteClick(meals);
                        FireBaseData.removeFavouriteFromFirebase(context,meals);
                    }
                }else {
                    Toast.makeText(context, "You Should Login first", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public void setMealList(ArrayList<Meal>meal) {
       this.meal =meal;
        notifyDataSetChanged();
    }

    class HomeRvHolder extends RecyclerView.ViewHolder {
        TextView txt;
        ImageView imageView;
        Button fav;
        CardView imageCard,itemCard;
        ImageView imgMeal;
        AutoCompleteTextView autoCompleteTextView;



        public HomeRvHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.meal_name);
            imageView = itemView.findViewById(R.id.img_meal);
            fav = itemView.findViewById(R.id.fav_button);
            imageCard = itemView.findViewById(R.id.image_card);
            imgMeal = itemView.findViewById(R.id.img_meal);
            itemCard=itemView.findViewById(R.id.cv_home_meal);
            autoCompleteTextView =itemView.findViewById(R.id.dp_plan);

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

