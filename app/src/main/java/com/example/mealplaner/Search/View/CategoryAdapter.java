package com.example.mealplaner.Search.View;

import android.content.Context;
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
import com.example.mealplaner.Category.View.CategoryData;
import com.example.mealplaner.Models.Category;
import com.example.mealplaner.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<Category> categoryList;
    Context context;

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context= parent.getContext();
        View view = inflater.inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setCategory(categoryList.get(position));
        holder.cvCategory.setOnClickListener(view->{
            Intent intent =new Intent(context, CategoryData.class);
            Bundle bundle =new Bundle();
            Category c= categoryList.get(position);
            bundle.putSerializable("category",c);
            intent.putExtra("category",bundle);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivCategory;
        TextView tvCatName;
        TextView tvCatDisc;
        CardView cvCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCategory =itemView.findViewById(R.id.category_iv);
            tvCatName=itemView.findViewById(R.id.tv_category_name);
            tvCatDisc=itemView.findViewById(R.id.tv_catigory_discript);
            cvCategory=itemView.findViewById(R.id.cv_category_item);
        }
        public void setCategory(Category category){
            tvCatName.setText(category.getStrCategory());
          //  tvCatDisc.setText(category.getStrCategoryDescription());
            tvCatDisc.setVisibility(View.GONE);
            Glide.with(ivCategory.getContext()).load(category.getStrCategoryThumb()).into(ivCategory);

        }
    }
}
