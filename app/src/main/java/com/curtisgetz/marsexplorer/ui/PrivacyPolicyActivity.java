package com.curtisgetz.marsexplorer.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebView;

import com.curtisgetz.marsexplorer.R;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private final static String POLICY_URL_REMOTE_CONFIG_KEY = "privacy_policy_url";

    @BindView(R.id.privacy_web_view)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        String policyUrl = remoteConfig.getString(POLICY_URL_REMOTE_CONFIG_KEY);



        mWebView.loadUrl(policyUrl);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
