package com.example.mealplaner;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VPAdapter extends RecyclerView.Adapter<VPAdapter.ViewHolder> {
    ArrayList<ViewPagerIteam> viewPagerIteams;
    Context context;
    MoveFragment moveFragment;

    public VPAdapter(ArrayList<ViewPagerIteam> viewPagerIteams, Context context) {
        this.viewPagerIteams = viewPagerIteams;
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
        holder.setData(viewPagerIteams.get(position).getTvDiscript());
        if(position==viewPagerIteams.size()-1){
            holder.btnNext.setVisibility(View.VISIBLE);
            holder.btnNext.setOnClickListener(view -> {
                moveFragment.move();
            });
        }


    }


    @Override
    public int getItemCount() {
        return viewPagerIteams.size();
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
