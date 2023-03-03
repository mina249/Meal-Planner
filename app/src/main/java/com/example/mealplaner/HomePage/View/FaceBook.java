package com.example.mealplaner.HomePage.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mealplaner.Login.View.LoginActivity;
import com.example.mealplaner.Network.FireBaseData;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Arrays;
import java.util.HashMap;


public class FaceBook extends LoginActivity {
    CallbackManager callbackManager;
    FirebaseAuth mAuth;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.d("TAG", "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(FaceBook.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            HashMap  <String,Object> result = new HashMap<>();
        Log.i("Name", "updateUI: "+user.getDisplayName());
        result.put("name",user.getDisplayName());
        Intent intent = new Intent(this,MainActivity.class);

            FireBaseData.getPlanFromFireBase(FaceBook.this,mAuth.getCurrentUser(),"saturday");
            FireBaseData.getPlanFromFireBase(FaceBook.this,mAuth.getCurrentUser(),"sunday");
            FireBaseData.getPlanFromFireBase(FaceBook.this,mAuth.getCurrentUser(),"monday");
            FireBaseData.getPlanFromFireBase(FaceBook.this,mAuth.getCurrentUser(),"tuesday");
            FireBaseData.getPlanFromFireBase(FaceBook.this,mAuth.getCurrentUser(),"wednesday");
            FireBaseData.getPlanFromFireBase(FaceBook.this,mAuth.getCurrentUser(),"thursday");
            FireBaseData.getPlanFromFireBase(FaceBook.this,mAuth.getCurrentUser(),"friday");
            FireBaseData.getFavouriteFromFirebase(FaceBook.this,mAuth.getCurrentUser());
            startActivity(intent);

            result.put("image", user.getPhotoUrl().toString());

            reference.child(user.getUid()).updateChildren(result);
        }
    }
}
