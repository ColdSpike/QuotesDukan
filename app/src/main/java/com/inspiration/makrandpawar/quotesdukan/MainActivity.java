package com.inspiration.makrandpawar.quotesdukan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.inspiration.makrandpawar.quotesdukan.adapter.MainActivityViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FloatingActionButton floatingActionButtonNextScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("NIGHTMODEACTIVE", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

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
                    floatingActionButtonNextScreen.setVisibility(View.GONE);
                    getSupportActionBar().setTitle("Quotes Dukan");
                } else if (position == 0) {
                    floatingActionButtonNextScreen.setVisibility(View.VISIBLE);
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

        floatingActionButtonNextScreen = (FloatingActionButton) findViewById(R.id.mainactivity_nextscreen);
        floatingActionButtonNextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mainactivity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mainactivity_settings) {
            startActivityForResult(new Intent(this, SettingsActivity.class), 199);
        } else if (item.getItemId() == R.id.mainactivity_onthisday) {
            startActivity(new Intent(this, OnThisDayActivity.class));
        } else if (item.getItemId() == R.id.mainactivity_about) {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                    .title("About The App")
                    .positiveText("CLOSE")
                    .content("This app uses the api provided by favqs.com to display its contents")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    });

            MaterialDialog dialog = builder.build();
            dialog.show();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 199) {
            recreate();
        }
    }
}
