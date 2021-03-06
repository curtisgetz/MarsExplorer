
/*
 * Created by Curtis Getz on 11/6/18 9:48 AM
 * Last modified 11/6/18 9:48 AM
 */

package com.curtisgetz.marsexplorer.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.ui.info.InfoDialogFragment;
import com.curtisgetz.marsexplorer.ui.settings.SettingsActivity;
import com.curtisgetz.marsexplorer.utils.InformationUtils;

/**
 * Base Activity for all Activities to extend to inflate options menu
 * Some Fragments will also inflate menus in addition to this menu.
 */

@SuppressLint("Registered")
public class MarsBaseActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_credits:
                InfoDialogFragment infoDialogFragment = InfoDialogFragment.newInstance(this, InformationUtils.CREDIT_INFO);
                infoDialogFragment.show(getSupportFragmentManager(), InformationUtils.class.getSimpleName());
                return true;
            case R.id.action_privacy:
                Intent privacyIntent = new Intent(this, PrivacyPolicyActivity.class);
                startActivity(privacyIntent);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

