package com.inspiration.makrandpawar.quotesdukan.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.inspiration.makrandpawar.quotesdukan.R;

public class PreferenceScreenFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    CheckBoxPreference checkBoxPreference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        checkBoxPreference = (CheckBoxPreference) getPreferenceScreen().findPreference("preference_daynight");
        checkBoxPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (checkBoxPreference.isChecked()) {
                    checkBoxPreference.setSummary("Night Mode Active");
                    sharedPreferences.edit().putBoolean("NIGHTMODEACTIVE", true).commit();
                    return true;
                } else {
                    checkBoxPreference.setSummary("Day Mode Active");
                    sharedPreferences.edit().putBoolean("NIGHTMODEACTIVE", false).commit();
                    return true;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }
}
