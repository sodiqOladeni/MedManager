package com.africanego.med_manager.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.africanego.med_manager.R;

public class AppAbout extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_about);
    }

    public void developerTwitter(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "This app remind you to take your drug at specified time: https://play.google.com/store/apps/details?id=com.africanego.med_manager");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
