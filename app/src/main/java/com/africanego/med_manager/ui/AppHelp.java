package com.africanego.med_manager.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.africanego.med_manager.R;

public class AppHelp extends AppCompatActivity {

    private EditText helpTopic;
    private EditText helpBody;
    private Button helpSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_help);

        helpTopic = findViewById(R.id.help_topic);
        helpBody = findViewById(R.id.help_body);
        helpSubmit = findViewById(R.id.help_submit);

        helpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = helpTopic.getText().toString();
                String body = helpBody.getText().toString();

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","sodiqoladeni@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
    }
}
