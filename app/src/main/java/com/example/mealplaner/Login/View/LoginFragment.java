package com.example.mealplaner.Login.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealplaner.Network.FireBaseData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.mealplaner.HomePage.View.MainActivity;
import com.example.mealplaner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText et_email;
    EditText pass;
    TextView forgetPass;
    Button login_btn;
    final float alpha = 0;
    private FirebaseAuth mAuth;
    Button guest;
    ProgressDialog progressDialog;



    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
        inflateUi(v);
        animateUi();
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getActivity());

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail,userPassword;
                userEmail = et_email.getText().toString();
                userPassword = pass.getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                    et_email.setError("Invalid Email");
                    et_email.setFocusable(true);
                } else if(userPassword.isEmpty()){
                        pass.setError("Invalid Password");
                        pass.setFocusable(true);
                }else{
                    loginUser(userEmail,userPassword);

                }
            }
        });
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                i.putExtra("checkUserType","guest");
                startActivity(i);
            }
        });
    }

    public void inflateUi(ViewGroup v) {
        et_email = v.findViewById(R.id.et_email_signin);
        pass = v.findViewById(R.id.et_password_signin);
        login_btn = v.findViewById(R.id.btn_login);
        forgetPass = v.findViewById(R.id.tv_forget_pass);
        guest = v.findViewById(R.id.btn_guest_login);
    }

    public void animateUi() {
        et_email.setTranslationX(800);
        pass.setTranslationX(800);
        login_btn.setTranslationX(800);
        forgetPass.setTranslationX(800);
        guest.setTranslationX(800);
        et_email.setAlpha(alpha);
        pass.setAlpha(alpha);
        login_btn.setAlpha(alpha);
        forgetPass.setAlpha(alpha);
        et_email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login_btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        guest.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();


    }

    private  void loginUser(String email , String password){
        progressDialog.setMessage("Logging in");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(getActivity(),MainActivity.class);
                            i.putExtra("checkUserType","loggedUser");
                            FireBaseData.getPlanFromFireBase(getContext(),mAuth.getCurrentUser(),"saturday");
                            FireBaseData.getPlanFromFireBase(getContext(),mAuth.getCurrentUser(),"sunday");
                            FireBaseData.getPlanFromFireBase(getContext(),mAuth.getCurrentUser(),"monday");
                            FireBaseData.getPlanFromFireBase(getContext(),mAuth.getCurrentUser(),"tuesday");
                            FireBaseData.getPlanFromFireBase(getContext(),mAuth.getCurrentUser(),"wednesday");
                            FireBaseData.getPlanFromFireBase(getContext(),mAuth.getCurrentUser(),"thursday");
                            FireBaseData.getPlanFromFireBase(getContext(),mAuth.getCurrentUser(),"friday");
                            FireBaseData.getFavouriteFromFirebase(getContext(),mAuth.getCurrentUser());
                            startActivity(i);
                            getActivity().finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showRecoverPasswordDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Recover Your Password");
        LinearLayout linearLayout = new LinearLayout(getActivity());
        EditText et_email = new EditText(getActivity());
        et_email.setHint("Enter Your Email");
        et_email.setMinEms(10);
        et_email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(et_email);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = et_email.getText().toString().trim();
                beginRecover(email);

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

    private void beginRecover(String email){
        progressDialog.setMessage("Sending Email");
        progressDialog.show();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Recovery email sent", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}