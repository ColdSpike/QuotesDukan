package com.inspiration.makrandpawar.quotesdukan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.inspiration.makrandpawar.quotesdukan.fragments.QotdFragment;
import com.inspiration.makrandpawar.quotesdukan.fragments.QuotesListFragment;

public class MainActivityViewPagerAdapter extends FragmentPagerAdapter {
    public MainActivityViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                QotdFragment qotdFragment = new QotdFragment();
                return qotdFragment;
            case 1:
                QuotesListFragment quotesListFragment = new QuotesListFragment();
                return quotesListFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
