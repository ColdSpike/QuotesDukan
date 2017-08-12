package com.inspiration.makrandpawar.quotesdukan;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.inspiration.makrandpawar.quotesdukan.adapter.OnThisDayActivityViewPagerAdapter;

public class OnThisDayActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_this_day);

        toolbar = (Toolbar) findViewById(R.id.onthisdayactivity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("On This Day");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.onthisdayactivity_viewpager);
        OnThisDayActivityViewPagerAdapter onThisDayActivityViewPagerAdapter = new OnThisDayActivityViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(onThisDayActivityViewPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.onthisdayactivity_tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
