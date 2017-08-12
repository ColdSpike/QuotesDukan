package com.example.makrandpawar.quotesdukan;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makrandpawar.quotesdukan.adapter.MainActivityViewPagerAdapter;
import com.example.makrandpawar.quotesdukan.model.QotdResponse;
import com.example.makrandpawar.quotesdukan.model.QuotesListResponse;
import com.example.makrandpawar.quotesdukan.rest.QuoteService;
import com.example.makrandpawar.quotesdukan.rest.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SwitchCompat switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainactivity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quote Of The Day");

        viewPager = (ViewPager) findViewById(R.id.mainactivity_viewpager);
        viewPager.setAdapter(new MainActivityViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 1) {
                    getSupportActionBar().setTitle("Quotes Dukan");
                    if (switchCompat != null)
                        switchCompat.setChecked(false);
                } else if (position == 0) {
                    getSupportActionBar().setTitle("Quote Of The Day");
                    if (switchCompat != null)
                        switchCompat.setChecked(true);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mainactivity_menu, menu);

        MenuItem switchCompatItem = menu.findItem(R.id.mainactivity_switch_layout);
        switchCompat = (SwitchCompat) switchCompatItem.getActionView();
        switchCompat.setChecked(true);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getSupportActionBar().setTitle("Quote Of The Day");
                    viewPager.setCurrentItem(0, true);
                } else {
                    getSupportActionBar().setTitle("Quotes Dukan");
                    viewPager.setCurrentItem(1, true);
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
