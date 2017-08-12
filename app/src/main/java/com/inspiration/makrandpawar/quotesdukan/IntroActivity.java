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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntro2Fragment.newInstance("New Quote When You Want", "just swipe down to refresh the quote(s)", R.drawable.ic_arrow_downward_black_48dp, getResources().getColor(R.color.introcolor1)));
        addSlide(AppIntro2Fragment.newInstance("Day And Night Mode", "Day mode for brighter backgrounds and night mode for darker backgrounds", R.drawable.ic_brightness_medium_black_48dp, getResources().getColor(R.color.introcolor2)));
        addSlide(AppIntro2Fragment.newInstance("Multiple View Modes", "different types of view modes for better experience", R.drawable.ic_different_views_48dp_black, getResources().getColor(R.color.introcolor3)));
        showSkipButton(false);
        setProgressButtonEnabled(true);
        SharedPreferences getPrefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

        boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

        if (!isFirstStart) {

            final Intent i = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        SharedPreferences.Editor e = getPrefs.edit();
        e.putBoolean("firstStart", false);
        e.apply();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        final Intent i = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
