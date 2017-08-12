package com.inspiration.makrandpawar.quotesdukan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro2 {
    private SharedPreferences getPrefs;
    private boolean isFirstStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntro2Fragment.newInstance("New Quote When You Want", "just swipe down to refresh the quote(s)", R.drawable.ic_arrow_downward_black_48dp, getResources().getColor(R.color.introcolor1)));
        addSlide(AppIntro2Fragment.newInstance("Day And Night Mode", "Day mode for brighter backgrounds and night mode for darker backgrounds", R.drawable.ic_brightness_medium_black_48dp, getResources().getColor(R.color.introcolor2)));
        addSlide(AppIntro2Fragment.newInstance("Multiple View Modes", "different types of view modes for better experience", R.drawable.ic_different_views_48dp_black, getResources().getColor(R.color.introcolor3)));
        addSlide(AppIntro2Fragment.newInstance("On This Day", "see what events, births and deaths happened in the past", R.drawable.ic_history_black_48dp, getResources().getColor(R.color.introcolor4)));
        addSlide(AppIntro2Fragment.newInstance("Easy Copy", "just long click what you want to copy and it will be copied to your clipboard", R.drawable.ic_content_copy_black_48dp, getResources().getColor(R.color.introcolor5)));
        addSlide(AppIntro2Fragment.newInstance("AND THE BEST PART!!", "NO IRRITATING ADS!", R.drawable.ic_tag_faces_black_48dp, getResources().getColor(R.color.introcolor6)));
        showSkipButton(false);
        setProgressButtonEnabled(true);

        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        isFirstStart = getPrefs.getBoolean("firstStart", true);

        if (!isFirstStart) {

            final Intent i = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        SharedPreferences.Editor e = getPrefs.edit();
        e.putBoolean("firstStart", false);
        e.apply();

        final Intent i = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}
