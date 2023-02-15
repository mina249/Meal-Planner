package com.example.mealplaner;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class User {
    private String userName;
    private String email;
    private String password;
    static ArrayList<User> registeredUsers=new ArrayList<>();
    Button retry;

    public User() {
    }

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static User findUserByEmail(String email){
        User temp = new User();
        for(int i =0 ; i< registeredUsers.size();i++){

            if(registeredUsers.get(i).getEmail().equals(email)){
                temp = registeredUsers.get(i);
                break;
            }

        }
        return temp;
    }
}
