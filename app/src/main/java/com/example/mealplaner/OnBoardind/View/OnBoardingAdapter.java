package com.example.mealplaner.OnBoardind.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplaner.Clender.InterFaces.MoveFragment;
import com.example.mealplaner.Login.View.LoginActivity;
import com.example.mealplaner.R;

import java.util.ArrayList;

public class OnBoardingAdapter extends RecyclerView.Adapter<OnBoardingAdapter.ViewHolder> {
    ArrayList<OnBoardinfIteam> onBoardinfIteams;
    Context context;
    MoveFragment moveFragment;

    public OnBoardingAdapter(ArrayList<OnBoardinfIteam> onBoardinfIteams, Context context) {
        this.onBoardinfIteams = onBoardinfIteams;
        this.context= context;
        moveFragment= (MoveFragment) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_pager_iteam, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(onBoardinfIteams.get(position).getTvDiscript());
        if(position== onBoardinfIteams.size()-1){
            holder.btnNext.setVisibility(View.VISIBLE);
            holder.btnNext.setOnClickListener(view -> {
                Intent intent = new Intent(context, LoginActivity.class);
               //context.startActivity(intent);
                moveFragment.move();

            });
        }


    }


    @Override
    public int getItemCount() {
        return onBoardinfIteams.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView  tvDescript;
        Button btnNext;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescript= itemView.findViewById(R.id.tvDescription);
            btnNext=itemView.findViewById(R.id.next);
        }
        public void setData(String descrip){
            tvDescript.setText(descrip);
        }
    }
}
