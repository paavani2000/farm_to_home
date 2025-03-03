package com.example.mad_project;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerViewAdapter extends FragmentStateAdapter {
    public PagerViewAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position)
        {
            case 1:
                return new UsersFragment();
        }
        return new ProfileFragment()        ;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

