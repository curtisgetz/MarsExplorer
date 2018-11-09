package com.curtisgetz.marsexplorer.ui.settings;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;


import com.curtisgetz.marsexplorer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class  SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener{


    /*Tweets preference change is not functioning at this time. This preference requires FCM topics
       which , as far as I know, cannot be sent from the FB console and will have to be implemented when
       I set up a backend to handle the Tweets automatically.
       Currently the Tweets need to be sent manually via the FB console (a notification message) but the
       app is set up to handle this and show a notification whether it's open or not.

       So the Tweet features are functioning but require a manual message via the console.
       Will work on a backend later but it is out of the scope of his project and I don't have time.
    */

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        //unregister SettingsFragment as a SPchanged Listener
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //register SettingsFragment as a SPchanged Listener
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
}
