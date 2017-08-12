package com.inspiration.makrandpawar.quotesdukan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.inspiration.makrandpawar.quotesdukan.adapter.MainActivityViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

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
                } else if (position == 0) {
                    getSupportActionBar().setTitle("Quote Of The Day");
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
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            menu.findItem(R.id.mainactivity_daynightswitch).setIcon(R.drawable.ic_brightness_high_black_24dp);
        } else {
            menu.findItem(R.id.mainactivity_daynightswitch).setIcon(R.drawable.ic_brightness_low_black_24dp);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mainactivity_daynightswitch) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }
        }else if(item.getItemId() == R.id.mainactivity_about){
            startActivity(new Intent(this,OnThisDayActivity.class));
//            MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
//                    .title("About The App")
//                    .positiveText("CLOSE")
//                    .content("This app uses the api provided by favqs.com to display its contents")
//                    .onPositive(new MaterialDialog.SingleButtonCallback() {
//                        @Override
//                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                            dialog.dismiss();
//                        }
//                    });
//
//            MaterialDialog dialog = builder.build();
//            dialog.show();
        }
        return true;
    }
}
