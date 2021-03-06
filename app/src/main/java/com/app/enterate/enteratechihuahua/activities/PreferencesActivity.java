package com.app.enterate.enteratechihuahua.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.app.enterate.enteratechihuahua.test.R;

/**
 * Created by armando on 9/19/16.
 */
public class PreferencesActivity extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MyPreferenceFragment()).commit();

    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        Intent i;
        Intent t;

        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            findPreference("privacy");
            findPreference("terms");

            Preference privacy = (Preference) findPreference("privacy");
            Preference terms = (Preference) findPreference("terms");

            i = new Intent(getActivity(), PrivacyActivity.class);
            privacy.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                     startActivity(i);
                    return false;
                }
            });


            t = new Intent(getActivity(), TermsActivity.class);
            terms.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(t);
                    return false;
                }
            });
        }
    }
}
