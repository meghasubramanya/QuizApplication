package com.example.quizapp.settings;

import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.quizapp.R;
import com.example.quizapp.noti.NotificationWorker;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class SettingsFragment extends PreferenceFragmentCompat {

    private String NOTIFICATION_WORK = "work";

    public SettingsFragment() {
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);

        SwitchPreference notifications=findPreference("switch_preference_1");
        notifications.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                long current = System.currentTimeMillis();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,14);
                calendar.set(Calendar.MINUTE,30);
                calendar.set(Calendar.SECOND,0);

                if(calendar.getTimeInMillis() < current) {
                    calendar.add(Calendar.DAY_OF_MONTH,1);
                }

                final WorkManager manager = WorkManager.getInstance(requireActivity());
                final PeriodicWorkRequest.Builder workRequestBuilder = new PeriodicWorkRequest.Builder(
                        NotificationWorker.class,
                        1,
                        TimeUnit.DAYS);

                workRequestBuilder.setInitialDelay(calendar.getTimeInMillis() - current , TimeUnit.MILLISECONDS);

                Boolean noti = (Boolean) newValue;

                if (noti){
                    manager.enqueueUniquePeriodicWork(NOTIFICATION_WORK, ExistingPeriodicWorkPolicy.REPLACE,workRequestBuilder.build());
                }else {
                    manager.cancelUniqueWork(NOTIFICATION_WORK);
                }
                return true;
            }
        });

        ListPreference darkMode =findPreference("list_preference_2");
        darkMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                return false;
            }
        });
    }
}
