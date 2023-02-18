package com.example.mealplaner.Search.Ingrediant.InterFaces;

import com.example.mealplaner.Models.AllIngrediantModel;
import com.example.mealplaner.Models.Category;
import com.example.mealplaner.Models.IngrediantModel;

import java.util.ArrayList;

public interface IngrediantFilter {
    public void filter(ArrayList<IngrediantModel> ingrediantModels);

}
