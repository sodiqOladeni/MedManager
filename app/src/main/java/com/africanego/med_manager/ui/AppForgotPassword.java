package com.africanego.med_manager.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.africanego.med_manager.R;

public class AppForgotPassword extends AppCompatActivity {

    private EditText forgotPasswordEmail, newPassword, newPasswordConfirm;
    private Button changepasswordBtn;
    private TextView warningTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_forgot_password);

        forgotPasswordEmail = findViewById(R.id.forget_email);
        newPassword = findViewById(R.id.forget_new_password);
        newPasswordConfirm = findViewById(R.id.forget_new_password_confirm);
        changepasswordBtn = findViewById(R.id.confirm_forget_password_btn);
        warningTextView =findViewById(R.id.forget_warning_email);
    }

    public void forgotPasswordCorrected(View view) {
        String userEmail = forgotPasswordEmail.getText().toString();
        String userNewPassword = newPassword.getText().toString();
        String userNewPasswordConfirm = newPasswordConfirm.getText().toString();

        if (userEmail.isEmpty()){
            warningTextView.setTextColor(getResources().getColor(R.color.warning_color));
            warningTextView.setText(getString(R.string.email_cant_empty));

        }else if (!userNewPassword.equals(userNewPasswordConfirm)){
            warningTextView.setTextColor(getResources().getColor(R.color.warning_color));
            warningTextView.setText(getString(R.string.password_mismatch));

        }else {
            Toast.makeText(this, "Password changed", Toast.LENGTH_SHORT).show();

            Intent confirmForgotPasswordIntent = new Intent(this, AppAuthPage.class);
            confirmForgotPasswordIntent.putExtra("user_email", userEmail);
            startActivity(confirmForgotPasswordIntent);
        }
    }
}
