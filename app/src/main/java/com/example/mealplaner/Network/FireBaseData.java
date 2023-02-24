package com.example.mealplaner.Network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mealplaner.DataBase.ConcreteLocalSource;
import com.example.mealplaner.DataBase.LocalSource;
import com.example.mealplaner.Models.Meal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireBaseData {
    public static void addFavouriteToFirebase(Context context, Meal meal) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
        } else {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Registered Users Meals");
            ref.child(firebaseAuth.getUid()).child("Favorites").child(meal.getStrMeal()).setValue(meal)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

    public static void addPlanToFirebase(Context context, Meal meal) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {

        } else {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Registered Users Meals");
            ref.child(firebaseAuth.getUid()).child("Plan").child(meal.getStatus()).child(meal.getStrMeal()).setValue(meal)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


        }
    }

    public static void getFavouriteFromFirebase(Context context, FirebaseUser user) {

        DatabaseReference rootFav = FirebaseDatabase.getInstance().getReference().child("Registered Users Meals").child(user.getUid()).child("Favorites");
        rootFav.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Meal meal = dataSnapshot.getValue(Meal.class);
                    ConcreteLocalSource local =  ConcreteLocalSource.getInstance(context);
                    local.insert(meal);
                    Log.i("finaaaaaaaal", meal.getStrMeal() + "" + meal.getIdMeal());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("test", error.getMessage());
            }
        });
    }


    public static void getPlanFromFireBase(Context context, FirebaseUser user, String status) {
        DatabaseReference rootPlan = FirebaseDatabase.getInstance().getReference().child("Registered Users Meals").child(user.getUid()).child("Plan").child(status);
        rootPlan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Meal meal = dataSnapshot.getValue(Meal.class);
                    ConcreteLocalSource local = ConcreteLocalSource.getInstance(context);
                    local.insert(meal);
                    Log.i("finaaaaaaaal", meal.getStrMeal() + "" + meal.getIdMeal());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("test", error.getMessage());
            }
        });

    }

    public static void removeFavouriteFromFirebase(Context context, Meal myMeal) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {

        } else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Registered Users Meals");
            ref.child(firebaseAuth.getUid()).child("Favorites").child(myMeal.getStrMeal()).removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "removed from your favList", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "failed to remove from your favList", Toast.LENGTH_SHORT).show();

                        }
                    });

        }
    }

    public static void removePlanFromFireBase(Context context, Meal meal) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            Toast.makeText(context, "you\re not logged in", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Registered Users Meals");
            ref.child(firebaseAuth.getUid()).child("Plan").child(meal.getStatus()).child(meal.getStrMeal()).removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                        }
                    });

        }
    }
}
