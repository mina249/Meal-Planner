package com.example.mealplaner.OnBoardind.View;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.mealplaner.OnBoardind.Interface.MoveFragment;

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

        holder.setData(onBoardinfIteams.get(position).getTvDiscript(),(onBoardinfIteams.get(position).getLotteName()));
        holder.bts[position].setBackgroundResource(R.drawable.onboardin_orange);
        if(position== onBoardinfIteams.size()-1){
            holder.btnNext.setVisibility(View.VISIBLE);
            holder.btnNext.setOnClickListener(view -> {
                Intent intent = new Intent(context, LoginActivity.class);
               moveFragment.move();

            });
        }
    }
    @Override
    public int getItemCount() {
        return onBoardinfIteams.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LottieAnimationView animationView;
        TextView tvDescript;
        Button btnNext;
        Resources res;
        Button b1,b2,b3;
        Button[]bts;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescript= itemView.findViewById(R.id.tvDescription);
            btnNext=itemView.findViewById(R.id.next);
            animationView=itemView.findViewById(R.id.lottie_onboarding);
            res=itemView.getResources();
            b1=itemView.findViewById(R.id.btn_onboadring1);
            b2=itemView.findViewById(R.id.btn_onboadring2);
            b3=itemView.findViewById(R.id.btn_onboadring3);
            bts=new Button[]{b1,b2,b3};
        }
        public void setData(String descrip,String raw){
            tvDescript.setText(descrip);

            int resourceId = res.getIdentifier(raw, "raw",
                    animationView.getContext().getPackageName());//initialize res and context in adapter's contructor
            animationView.setAnimation(resourceId);
            animationView.setRepeatCount(50);

        }
    }
}
