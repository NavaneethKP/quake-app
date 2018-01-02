package com.example.android.quakereport;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class settingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment
    implements Preference.OnPreferenceChangeListener{

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
            bindPreferenceSummaryToValue(minMagnitude);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {

            preference.setSummary(newValue.toString());
            return true;
        }


        private void bindPreferenceSummaryToValue(Preference preference){

            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = preference.getSharedPreferences();
            String preferenceString = preferences.getString(getString(R.string.settings_min_magnitude_key),"");
            onPreferenceChange(preference, preferenceString);

        }
    }
}
