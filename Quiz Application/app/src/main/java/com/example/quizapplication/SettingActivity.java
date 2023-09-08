package com.example.quizapplication;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class QuizPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);
            Preference listPreferenceDifficulty = findPreference(getString(R.string.difficulty_key));
            Preference listPreferenceCategory = findPreference(getString(R.string.category_key));
            listPreferenceDifficulty.setOnPreferenceChangeListener(this);
            listPreferenceCategory.setOnPreferenceChangeListener(this);
            bindPreferenceSummaryToValue(listPreferenceDifficulty);
            bindPreferenceSummaryToValue(listPreferenceCategory);
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String value = sharedPreferences.getString(preference.getKey(), " ");
            onPreferenceChange(preference, value);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                CharSequence[] labels = listPreference.getEntries();
                preference.setSummary(labels[prefIndex]);
            }
            return true;
        }
    }
}
