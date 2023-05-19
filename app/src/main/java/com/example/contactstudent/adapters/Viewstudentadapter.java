package com.example.contactstudent.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.contactstudent.fragments.Contact_fragment;
import com.example.contactstudent.fragments.Student_fragment;

public class Viewstudentadapter extends FragmentPagerAdapter {

    public Viewstudentadapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Contact_fragment();
        } else {
            return new Student_fragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "Contact";
        } else {
            return "Student";
        }
    }
}
