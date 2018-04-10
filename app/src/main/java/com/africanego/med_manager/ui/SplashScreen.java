package com.africanego.med_manager.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.africanego.med_manager.R;

/**
 * Created by ricHVision on 1/3/2018.
 */

public class SplashScreen extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {


            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
            String sessionTokenString = sharedPreferences.getString("session_token", "session_token");


            @Override
            public void run() {
                if (sharedPreferences.contains("session_token")) {

                // This method will be executed once the timer is over
                // Start your app main activity
                Intent homePageIntent = new Intent(SplashScreen.this, MedicineList.class);
                startActivity(homePageIntent);
            } else {
                    Intent appWelcomeIntent = new Intent(SplashScreen.this, AppWelcome.class);
                    startActivity(appWelcomeIntent);
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
