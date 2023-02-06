package com.example.mealplaner;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class LoginFragmentAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments;
    ArrayList<String> names;
    private Context context;
    private int numberOfPages;

    public LoginFragmentAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.numberOfPages = numberOfPages;
        fragments = new ArrayList<>();
        names = new ArrayList<>();
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void setData(Fragment fragment, String name) {
        fragments.add(fragment);
        names.add(name);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        SpannableString spannableString = new SpannableString("" + names.get(position));
        return spannableString;
    }
}
