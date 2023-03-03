package com.example.mealplaner.Profile.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mealplaner.Calendar.CalendarActivity;
import com.example.mealplaner.DataBase.ConcreteLocalSource;
import com.example.mealplaner.FavouriteMeals.View.FavouriteMealActivity;
import com.example.mealplaner.Login.View.LoginActivity;
import com.example.mealplaner.Profile.Presenter.ProfilePresenter;
import com.example.mealplaner.Profile.Presenter.ProfilePresenyterInterface;
import com.example.mealplaner.R;
import com.example.mealplaner.SplashScree.View.SplashScreen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    ImageView profImg;
    TextView name;
    TextView email;
    StorageReference storageReference;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button logout;
    Uri profImgUri;
    FloatingActionButton editProf;
    ProfilePresenyterInterface profilePresenyterInterface;
    static final int IMA_PICK_GALLERY_CODE = 200;
    Switch themeSwitch;
    public static boolean nightMode ;
   public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        inflateUi();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        profilePresenyterInterface = new ProfilePresenter(ConcreteLocalSource.getInstance(this));
        getUserDataFromFireBase();




        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                checkUserStatus();
                profilePresenyterInterface.deleteTable();
            }
        });
        editProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfDialog();
            }
        });


    }

    private void inflateUi() {
        profImg = findViewById(R.id.profile_img_prof);
        name = findViewById(R.id.name_prof_tv);
        email = findViewById(R.id.email_prof_tv);
        logout = findViewById(R.id.logout);
        editProf = findViewById(R.id.fb_edit_prof);
        themeSwitch = findViewById(R.id.switch_theme);
    }

    private void checkUserStatus() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {

        } else {
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void getUserDataFromFireBase() {
        if (user != null) {
            Query query = reference.orderByChild("email").equalTo(user.getEmail());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String uname = "" + ds.child("name").getValue();
                        String uemail = "" + ds.child("email").getValue();
                        String uimage = "" + ds.child("image").getValue();
                        name.setText(uname);
                        email.setText(uemail);
                        try {
                           Glide.with(ProfileActivity.this).load(uimage).into(profImg);
                        } catch (Exception e) {
                            profImg.setImageResource(R.drawable.ic_add_prof_img);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void showEditProfDialog() {
        String[] options = {"Edit Name", "Edit Profile image"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit profile data");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showUpdateDialog("name");

                } else if (which == 1) {
                    pickFromGallery();
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMA_PICK_GALLERY_CODE) {
            profImgUri = data.getData();
            HashMap<String, Object> img = new HashMap<>();
            img.put("image", profImgUri.toString());
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(user.getUid()).updateChildren(img).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Glide.with(ProfileActivity.this).load(profImgUri).into(profImg);
                }
            });
        }

    }

    private void showUpdateDialog(String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update " + key);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setPadding(10, 10, 10, 10);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        EditText editText = new EditText(this);
        editText.setHint("Enter Your name");
        linearLayout.addView(editText);

        builder.setView(linearLayout);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(value)) {
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);
                    reference.child(user.getUid()).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void pickFromGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMA_PICK_GALLERY_CODE);
    }


}