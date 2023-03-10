package com.example.mealplaner.Login.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mealplaner.HomePage.View.FaceBook;
import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.Network.FireBaseData;
import com.example.mealplaner.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    TabLayout loginTabLayout;
    ViewPager loginViewPager;
    FloatingActionButton fb, google, twitter;
    LoginFragmentAdapter loginAdapter;
    Button login_btn;
    float alpha = 0;
    LoginFragment loginFragment;
    SignupFragment signupFragment;
    private FirebaseAuth mAuth;

    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient googleSignInClient;

    Drawable login;
   // FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginFragment = new LoginFragment();
        signupFragment = new SignupFragment();
        mAuth = FirebaseAuth.getInstance();



        inflateUi();
        settingTableLayout();
        animateUi();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
              }
        });
       // mAuth = FirebaseAuth.getInstance();
        fb.setOnClickListener(view -> {
           Intent intent =new Intent(this,FaceBook.class);
           startActivity(intent);
        });


       /* if (mAuth.getCurrentUser() != null) {
            // When user already sign in redirect to profile activity
            startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        }*/
    }

    public void animateUi() {
        fb.setTranslationY(300);
        google.setTranslationY(300);
        twitter.setTranslationY(300);
        loginTabLayout.setTranslationY(300);
        fb.setAlpha(alpha);
        google.setAlpha(alpha);
        twitter.setAlpha(alpha);
        loginTabLayout.setAlpha(alpha);
        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        loginTabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }

    public void inflateUi() {
        loginTabLayout = findViewById(R.id.tab_layout);
        loginViewPager = findViewById(R.id.view_pager);
        fb = findViewById(R.id.fab_fb);
        google = findViewById(R.id.fab_google);
        twitter = findViewById(R.id.fab_twitter);

    }

    public void settingTableLayout() {

        loginTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        loginAdapter = new LoginFragmentAdapter(getSupportFragmentManager(), this);
        loginAdapter.setData(new LoginFragment(), "Login");
        loginAdapter.setData(new SignupFragment(), "Signup");
        loginTabLayout.setupWithViewPager(loginViewPager);

        loginViewPager.setAdapter(loginAdapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            if (signInAccountTask.isSuccessful()) {
                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    if (googleSignInAccount != null) {
                        firebaseAuthWithGoogleAccount(googleSignInAccount);
                      /*  AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

                       mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Check condition
                                if (task.isSuccessful()) {
                                   FirebaseUser user = mAuth.getCurrentUser();

                                    if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                                        String email = user.getEmail();
                                        String uId = user.getUid();
                                        HashMap<Object, String> userData = new HashMap<>();
                                        userData.put("email", email);
                                        userData.put("uid", uId);
                                        userData.put("name", user.getDisplayName());
                                        userData.put("image", "");
                                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                        DatabaseReference reference = firebaseDatabase.getReference("Users");
                                        reference.child(uId).setValue(userData);
                                        FireBaseData.getPlanFromFireBase(LoginActivity.this,mAuth.getCurrentUser(),"saturday");
                                        FireBaseData.getPlanFromFireBase(LoginActivity.this,mAuth.getCurrentUser(),"sunday");
                                        FireBaseData.getPlanFromFireBase(LoginActivity.this,mAuth.getCurrentUser(),"monday");
                                        FireBaseData.getPlanFromFireBase(LoginActivity.this,mAuth.getCurrentUser(),"tuesday");
                                        FireBaseData.getPlanFromFireBase(LoginActivity.this,mAuth.getCurrentUser(),"wednesday");
                                        FireBaseData.getPlanFromFireBase(LoginActivity.this,mAuth.getCurrentUser(),"thursday");
                                        FireBaseData.getPlanFromFireBase(LoginActivity.this,mAuth.getCurrentUser(),"friday");
                                        FireBaseData.getFavouriteFromFirebase(LoginActivity.this,mAuth.getCurrentUser());
                                    }

                                } else {


                                }
                            }
                        });*/
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

       private void firebaseAuthWithGoogleAccount (GoogleSignInAccount account){
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                            String email = user.getEmail();
                            String uId = user.getUid();
                            HashMap<Object, String> userData = new HashMap<>();
                            userData.put("email", email);
                            userData.put("uid", uId);
                            userData.put("name", user.getDisplayName());
                            userData.put("image", "");
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference reference = firebaseDatabase.getReference("Users");
                            reference.child(uId).setValue(userData);
                            FireBaseData.getPlanFromFireBase(LoginActivity.this, mAuth.getCurrentUser(), "saturday");
                            FireBaseData.getPlanFromFireBase(LoginActivity.this, mAuth.getCurrentUser(), "sunday");
                            FireBaseData.getPlanFromFireBase(LoginActivity.this, mAuth.getCurrentUser(), "monday");
                            FireBaseData.getPlanFromFireBase(LoginActivity.this, mAuth.getCurrentUser(), "tuesday");
                            FireBaseData.getPlanFromFireBase(LoginActivity.this, mAuth.getCurrentUser(), "wednesday");
                            FireBaseData.getPlanFromFireBase(LoginActivity.this, mAuth.getCurrentUser(), "thursday");
                            FireBaseData.getPlanFromFireBase(LoginActivity.this, mAuth.getCurrentUser(), "friday");
                            FireBaseData.getFavouriteFromFirebase(LoginActivity.this, mAuth.getCurrentUser());
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }


}
