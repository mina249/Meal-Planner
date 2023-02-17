package com.example.mealplaner.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.Login.View.LoginActivity;
import com.example.mealplaner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    ImageView profImg;
    TextView name;
    TextView email;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        inflateUi();
       // logout = findViewById(R.id.logout);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        getUserDataFromFireBase();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                checkUserStatus();
            }
        });


    }

    private void inflateUi(){
        profImg = findViewById(R.id.profile_img_prof);
        name = findViewById(R.id.name_prof_tv);
        email = findViewById(R.id.email_prof_tv);
        logout = findViewById(R.id.logout);
    }
    private void checkUserStatus() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {

        } else {
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void getUserDataFromFireBase(){
        Query query = reference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String uname = ""+ds.child("name").getValue();
                    String uemail = ""+ds.child("email").getValue();
                    String uimage = ""+ds.child("image").getValue();

                    name.setText(uname);
                    email.setText(uemail);
                    try {
                        Picasso.get().load(uimage).into(profImg);

                    }catch (Exception e){
                        Picasso.get().load(R.drawable.ic_profile_img).into(profImg);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}