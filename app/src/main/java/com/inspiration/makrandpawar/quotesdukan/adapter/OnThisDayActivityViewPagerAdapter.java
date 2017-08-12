package com.inspiration.makrandpawar.quotesdukan.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.inspiration.makrandpawar.quotesdukan.fragments.OnThisDayBirthsFragment;
import com.inspiration.makrandpawar.quotesdukan.fragments.OnThisDayDeathsFragment;
import com.inspiration.makrandpawar.quotesdukan.fragments.OnThisDayEventsFraagment;

public class OnThisDayActivityViewPagerAdapter extends FragmentPagerAdapter{
    public OnThisDayActivityViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            OnThisDayEventsFraagment onThisDayEventsFraagment = new OnThisDayEventsFraagment();
            return onThisDayEventsFraagment;
        }
        else if (position == 1){
            OnThisDayBirthsFragment onThisDayBirthsFragment = new OnThisDayBirthsFragment();
            return onThisDayBirthsFragment;
        }else if (position == 2){
            OnThisDayDeathsFragment onThisDayDeathsFragment = new OnThisDayDeathsFragment();
            return onThisDayDeathsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Events";
            case 1:
                return "Births";
            case 2:
                return "Deaths";
            default:
                return null;
        }
    }
}
