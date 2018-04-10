package com.africanego.med_manager.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.africanego.med_manager.R;

public class AppAuthPage extends AppCompatActivity {

    private AppWelcome mAppWelcome;
    private EditText userEmailEdittext, userPasswordEdittext;
    private TextView warningTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_auth_page);

//        String intent = getIntent().getAction();
//        if (intent != null || !intent.isEmpty()){
//            intent.ge
//        }

        mAppWelcome = new AppWelcome();
        userEmailEdittext = findViewById(R.id.auth_email);
        userPasswordEdittext = findViewById(R.id.auth_password);
        warningTextView = findViewById(R.id.auth_login_warning_text);
    }

    public void signupIntent(View view) {
//
        Intent newSignUpIntent = new Intent(this, AppWelcome.class);
        startActivity(newSignUpIntent);
    }

    public void forgotPasswordIntent(View view) {
        Intent forgotPasswordIntent = new Intent(this, AppForgotPassword.class);
        startActivity(forgotPasswordIntent);
    }

    public void loginToHomePage(View view) {
        String userEmailProvided = userEmailEdittext.getText().toString();
        String userPasswordProvided = userPasswordEdittext.getText().toString();

//        if (userEmailProvided.isEmpty() || userPasswordProvided.isEmpty()){
//            warningTextView.setTextColor(getResources().getColor(R.color.warning_color));
//            warningTextView.setText(getString(R.string.fiels_cant_empty));
//        }
        Intent loginToHomePageIntent = new Intent(this, MedicineList.class);
        startActivity(loginToHomePageIntent);
    }
}
