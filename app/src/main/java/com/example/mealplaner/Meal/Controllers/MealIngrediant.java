package com.example.mealplaner.Meal.Controllers;

import android.util.Pair;

import com.example.mealplaner.Models.Meal;

import java.util.LinkedList;

public class MealIngrediant {
    private Meal meal;

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public LinkedList<Pair<String, String>> getMealIngriant() {
        LinkedList<Pair<String, String>> ingrediant = new LinkedList<>();

        if(!meal.getStrIngredient1().isEmpty()&&meal.getStrIngredient1()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient1(), meal.getStrMeasure1()));

        }
        if(!meal.getStrIngredient2().isEmpty()&&meal.getStrIngredient2()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient2(), meal.getStrMeasure2()));

        }
        if(!meal.getStrIngredient3().isEmpty()&&meal.getStrIngredient3()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient3(), meal.getStrMeasure3()));

        }
        if(!meal.getStrIngredient4().isEmpty()||meal.getStrIngredient4()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient4(), meal.getStrMeasure4()));

        }
        if(!meal.getStrIngredient5().isEmpty()&&meal.getStrIngredient5()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient5(), meal.getStrMeasure5()));

        }
        if(!meal.getStrIngredient6().isEmpty()&&meal.getStrIngredient6()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient6(), meal.getStrMeasure6()));

        }
        if(!meal.getStrIngredient7().isEmpty()&&meal.getStrIngredient7()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient7(), meal.getStrMeasure7()));

        }
        if(!meal.getStrIngredient8().isEmpty()&&meal.getStrIngredient8()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient8(), meal.getStrMeasure8()));

        }
        if(!meal.getStrIngredient9().isEmpty()&&meal.getStrIngredient9()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient9(), meal.getStrMeasure9()));

        }
        if(!meal.getStrIngredient10().isEmpty()&&meal.getStrIngredient10()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient10(), meal.getStrMeasure10()));

        }
        if(!meal.getStrIngredient11().isEmpty()&&meal.getStrIngredient11()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient11(), meal.getStrMeasure11()));

        }
        if(!meal.getStrIngredient12().isEmpty()&&meal.getStrIngredient12()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient12(), meal.getStrMeasure12()));

        }
        if(!meal.getStrIngredient13().isEmpty()&&meal.getStrIngredient13()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient13(), meal.getStrMeasure13()));

        }
        if(!meal.getStrIngredient14().isEmpty()&&meal.getStrIngredient14()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient14(), meal.getStrMeasure14()));

        }
        if(!meal.getStrIngredient15().isEmpty()&&meal.getStrIngredient15()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient15(), meal.getStrMeasure15()));

        }
        if(!meal.getStrIngredient16().isEmpty()&&meal.getStrIngredient16()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient16(), meal.getStrMeasure16()));

        }
        if(!meal.getStrIngredient17().isEmpty()&&meal.getStrIngredient17()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient17(), meal.getStrMeasure17()));

        }
        if(!meal.getStrIngredient18().isEmpty()&&meal.getStrIngredient18()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient18(), meal.getStrMeasure18()));

        }
        if(!meal.getStrIngredient19().isEmpty()&&meal.getStrIngredient19()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient19(), meal.getStrMeasure19()));

        }
        if(!meal.getStrIngredient20().isEmpty()&&meal.getStrIngredient20()!=null){
            ingrediant.add(new Pair<>(meal.getStrIngredient20(), meal.getStrMeasure20()));

        }




        return ingrediant;
    }
}
