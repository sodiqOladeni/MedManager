package com.africanego.med_manager.ui;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.africanego.med_manager.R;


public class UserProfileChangePassword extends AppCompatActivity {

    private EditText oldPasswordEdittext, newPasswordEdittext, confirmNewPasswordEdittext;
    private TextView warningChangingPassword;
    private Button changePasswordButton;
    private Button changeOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_change_password);

        oldPasswordEdittext = findViewById(R.id.change_password_old);
        newPasswordEdittext = findViewById(R.id.change_password_enter);
        confirmNewPasswordEdittext = findViewById(R.id.change_password_confirm);
        warningChangingPassword = findViewById(R.id.change_warning_password);
        changePasswordButton = findViewById(R.id.change_password_submit_button);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = oldPasswordEdittext.getText().toString().trim();
                String newPassword = newPasswordEdittext.getText().toString().trim();
                String confirmNewPassword = confirmNewPasswordEdittext.getText().toString().trim();


                if(!newPassword.equals(confirmNewPassword)) {
                    warningChangingPassword.setText(getString(R.string.password_mismatch));
                    warningChangingPassword.setTextColor(getResources().getColor(R.color.warning_color));

                    return;
                }
                if((newPassword.length() < 6) || (newPassword.length() < 6)) {
                    warningChangingPassword.setText(getString(R.string.warning_real_password));
                    warningChangingPassword.setTextColor(Color.RED);

                    return;
                }

                if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()){
                    warningChangingPassword.setText(getString(R.string.fiels_cant_empty));
                    warningChangingPassword.setTextColor(Color.RED);

                    return;
                }


            }
        });
    }
}
