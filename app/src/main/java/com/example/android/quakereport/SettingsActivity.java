package com.example.android.quakereport;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);
    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate( Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference minMagnitude = findPreference(getString(R.string.settingsMinMagnitudeKey));
            bindPreferenceSummaryToValue(minMagnitude);

            Preference orderBy = findPreference(getString(R.string.settingsOrderByKey));
            bindPreferenceSummaryToValue(orderBy);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String stringValue = o.toString();

            if(preference instanceof ListPreference) {
                ListPreference listPrefs = (ListPreference)preference;

                int prefIndex = listPrefs.findIndexOfValue(stringValue);

                if(prefIndex >= 0) {
                    CharSequence[] labels = listPrefs.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
                else {
                    preference.setSummary(stringValue);
                }
            }

            //preference.setSummary(stringValue);

            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = sharedPrefs.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}