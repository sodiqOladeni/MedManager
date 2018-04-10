package com.africanego.med_manager.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.africanego.med_manager.R;
import com.africanego.med_manager.db.UserDetails;

public class UserProfileEdit extends AppCompatActivity {

    private String username, userMobile, userGender, userMStatus, userDob;
    private int id;
    private TextView usernameEdittext, userMobileEdittext, userGenderEdittext, userMaritalStatusEdittext, userDobEdittext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_signup);


        usernameEdittext = findViewById(R.id.new_edit_username);
        userMobileEdittext = findViewById(R.id.new_edit_mobile);
        userGenderEdittext = findViewById(R.id.new_edit_gender);
        userMaritalStatusEdittext = findViewById(R.id.new_edit_marital_status);
        userDobEdittext = findViewById(R.id.new_edit_dob);

        Bundle userInformation = getIntent().getExtras();
        if (userInformation != null){
            username = userInformation.getString("username");
            userMobile = userInformation.getString("mobile");
            userGender = userInformation.getString("gender");
            userMStatus = userInformation.getString("marital_status");
            userDob = userInformation.getString("dob");
            id = userInformation.getInt("id");

            usernameEdittext.setText(username);
            userMobileEdittext.setText(userMobile);
            userGenderEdittext.setText(userGender);
            userMaritalStatusEdittext.setText(userMStatus);
            userDobEdittext.setText(userDob);
        }

//        newUsername = usernameEdittext.getText().toString();
//        newUserMobile = userMobileEdittext.getText().toString();
//        newUserGender = userGenderEdittext.getText().toString();
//        newUserMStatus = userMaritalStatusEdittext.getText().toString();
//        newUserDob = userDobEdittext.getText().toString();
    }


    private void updateUserProfile(){
        String newUsername = usernameEdittext.getText().toString();
        String newUserMobile = userMobileEdittext.getText().toString();
        String newUserGender = userGenderEdittext.getText().toString();
        String newUserMStatus = userMaritalStatusEdittext.getText().toString();
        String newUserDob = userDobEdittext.getText().toString();

        UserDetails userDetails = new UserDetails();
        userDetails.setId(id);
        userDetails.setUsername(newUsername);
        userDetails.setMobile(newUserMobile);
        userDetails.setGender(newUserGender);
        userDetails.setMarital_status(newUserMStatus);
        userDetails.setDob(newUserDob);

        HomeFragment.mUserDatabase.userDao().updateUserProfile(userDetails);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_update_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "upBack" menu option
            case R.id.home:
                onBackPressed();
                return true;

            // Respond to a click on the "save" menu option
            case R.id.update_profile:
                updateUserProfile();
                Toast.makeText(this, "Profile successfully updated", Toast.LENGTH_SHORT).show();
                Intent profileUpdatedIntent = new Intent(this, UserProfile.class);
                startActivity(profileUpdatedIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
