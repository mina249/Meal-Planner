package com.example.mealplaner.HomePage.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.mealplaner.FavouriteMeals.Intercafaces.OnDeleteFromFavClickListener;
import com.example.mealplaner.HomePage.Interfaces.OnAddToFavouriteClickListener;
import com.example.mealplaner.Meal.View.MealData;
import com.example.mealplaner.Models.Meal;
import com.example.mealplaner.Network.FireBaseData;
import com.example.mealplaner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder> {
    ArrayList<Meal> meals;
    ViewPager2 viewPager;
    Context context;

    OnAddToFavouriteClickListener favListener;
    OnDeleteFromFavClickListener deleteListener;

    FirebaseAuth auth;
    FirebaseUser user;
    boolean[] isFavorite ;



    public SliderAdapter(ViewPager2 viewPager,ArrayList<Meal> meals,Context context,OnAddToFavouriteClickListener favListener,  OnDeleteFromFavClickListener deleteListener) {
       this. meals= meals;
        this.viewPager=viewPager;
        this.deleteListener = deleteListener;
        this.favListener = favListener;
        this.context = context;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        isFavorite = new boolean[meals.size()];
    }
    public void setMeals(ArrayList<Meal> meals){
        this.meals=meals;
        notifyDataSetChanged();
        this.context = context;
        isFavorite = new boolean[meals.size()];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recommended_home_rv,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setMeal(meals.get(position));
        holder.btnFavourit.setOnClickListener(view->{

            if(user!=null) {
                if (!isFavorite[position]) {
                    holder.btnFavourit.setBackgroundResource(R.drawable.heart);
                    meals.get(position).setStatus("favourite");
                    favListener.onClick(meals.get(position));
                    FireBaseData.addFavouriteToFirebase(context,meals.get(position));
                    isFavorite[position] = true;
                } else {
                    holder.btnFavourit.setBackgroundResource(R.drawable.fav);
                    isFavorite[position] = false;
                    deleteListener.onDeleteClick(meals.get(position));
                    FireBaseData.removeFavouriteFromFirebase(context,meals.get(position));
                }
            }else {
                Toast.makeText(context, "You Should Login first", Toast.LENGTH_SHORT).show();
            }
        });
        if(position==meals.size()-2){
        viewPager.post(holder.runnable);
        }
        holder.mealName.setText(meals.get(position).getStrMeal());
        String [] days ={"Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,days);
        holder.daysDroplist.setAdapter(adapter);
        holder.daysDroplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null) {
                    holder.daysDroplist.showDropDown();
                }else{
                    Toast.makeText(context, "You Should Login first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.daysDroplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String day = parent.getItemAtPosition(position).toString();
                switch (day) {
                    case "Saturday":
                        meals.get(position).setStatus("saturday");
                        favListener.onClick(meals.get(position));
                        FireBaseData.addPlanToFirebase(context, meals.get(position));
                        Toast.makeText(context, "Meal added to Saturday", Toast.LENGTH_SHORT).show();

                        break;
                    case "Sunday":
                        meals.get(position).setStatus("sunday");
                        favListener.onClick(meals.get(position));
                        FireBaseData.addPlanToFirebase(context, meals.get(position));
                        Toast.makeText(context, "Meal added to Sunday ", Toast.LENGTH_SHORT).show();
                        break;
                    case "Monday":
                        meals.get(position).setStatus("monday");
                        favListener.onClick(meals.get(position));
                        FireBaseData.addPlanToFirebase(context, meals.get(position));
                        Toast.makeText(context, "Meal added to Monday", Toast.LENGTH_SHORT).show();
                        break;
                    case "Tuesday":
                        meals.get(position).setStatus("tuesday");
                        favListener.onClick(meals.get(position));
                        FireBaseData.addPlanToFirebase(context, meals.get(position));
                        Toast.makeText(context, "Meal added to Tuesday", Toast.LENGTH_SHORT).show();
                        break;

                    case "Wednesday":
                        meals.get(position).setStatus("wednesday");
                        favListener.onClick(meals.get(position));
                        FireBaseData.addPlanToFirebase(context, meals.get(position));
                        Toast.makeText(context, "Meal added to Wednesday ", Toast.LENGTH_SHORT).show();
                        break;

                    case "Thursday":
                        meals.get(position).setStatus("thursday");
                        favListener.onClick(meals.get(position));
                        FireBaseData.addPlanToFirebase(context, meals.get(position));
                        Toast.makeText(context, "Meal added to Thursday", Toast.LENGTH_SHORT).show();
                        break;
                    case "Friday":
                        meals.get(position).setStatus("friday");
                        favListener.onClick(meals.get(position));
                        FireBaseData.addPlanToFirebase(context, meals.get(position));
                        Toast.makeText(context, "Meal added to Friday ", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        holder.slideCard.setOnClickListener(view->{
            Bundle bundle = new Bundle();
            bundle.putString("id",meals.get(position).getIdMeal());
            Intent intent = new Intent(context, MealData.class);
            intent.putExtra("id",bundle);
            context.startActivity(intent);
        });






    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatAutoCompleteTextView daysDroplist;
        ImageView ivMeal;
        Button btnFavourit;
        CardView slideCard;
        TextView mealName;
        private Runnable runnable= new Runnable() {
            @Override
            public void run() {
                meals.addAll(meals);
                notifyDataSetChanged();
            }
        };

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            daysDroplist=itemView.findViewById(R.id.recomended_dp);
            ivMeal=itemView.findViewById(R.id.iv_meal_pic);
            btnFavourit=itemView.findViewById(R.id.btn_favourit);
            mealName = itemView.findViewById(R.id.rec_meal_name);
            slideCard = itemView.findViewById(R.id.slide_card);
        }
        public void setMeal(Meal meal){
            Glide.with(ivMeal.getContext()).load(meal.getStrMealThumb()).into(ivMeal);
        }



    }
}
