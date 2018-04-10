package com.africanego.med_manager.ui;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.africanego.med_manager.R;
import com.africanego.med_manager.db.UserDetails;
import com.bumptech.glide.Glide;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class UserProfile extends Fragment implements View.OnClickListener {

    private TextView userFirstnameTextView, userLastnameTextView, userEmailTextView, userNameTextView, editUserDetails,
            userMobileTextView, userDobTextView, userGenderTextView, userMStatusTextView, userChangePassword, userId;
    private static final int OPEN_GALLERY = 100;
    private long id;
    private ImageView userProfileImage, editUserImage;
    private  UserProfile userProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_user_profile, container, false);
        setHasOptionsMenu(true);

        userFirstnameTextView = rootView.findViewById(R.id.profile_firstname);
        userLastnameTextView = rootView.findViewById(R.id.profile_lastname);
        userEmailTextView = rootView.findViewById(R.id.profile_email);
        userNameTextView = rootView.findViewById(R.id.profile_username);
        userMobileTextView = rootView.findViewById(R.id.profile_mobile);
        userDobTextView = rootView.findViewById(R.id.profile_dob);
        userGenderTextView = rootView.findViewById(R.id.profile_gender);
        userMStatusTextView = rootView.findViewById(R.id.profile_marital_status);
        userProfileImage = rootView.findViewById(R.id.profile_image);

        userChangePassword = rootView.findViewById(R.id.password);
        userChangePassword.setOnClickListener(this);

        editUserDetails = rootView.findViewById(R.id.edit_details_textView);
        editUserDetails.setOnClickListener(this);

        editUserImage = rootView.findViewById(R.id.edit_user_image);
        editUserImage.setOnClickListener(this);

        List<UserDetails> userDetailsStored = AppWelcome.mUserDatabase.userDao().loadUserProfile();

        if (userDetailsStored != null || !userDetailsStored.isEmpty()) {

            for (UserDetails userDetails : userDetailsStored) {
                String firstname = userDetails.getFirstname();
                String lastname = userDetails.getLastname();
                String email = userDetails.getEmail();
                String username = userDetails.getUsername();
                String mobile = userDetails.getMobile();
                String dob = userDetails.getDob();
                String gender = userDetails.getGender();
                String maritalStatus = userDetails.getMarital_status();
                id = userDetails.getId();
                String profileImage = userDetails.getProfile_picture();

                userFirstnameTextView.setText(firstname);
                userLastnameTextView.setText(lastname);
                userEmailTextView.setText(email);
                userNameTextView.setText(username);
                userMobileTextView.setText(mobile);
                userDobTextView.setText(dob);
                userGenderTextView.setText(gender);
                userMStatusTextView.setText(maritalStatus);
                Toast.makeText(getContext(), "user Tag: " + id, Toast.LENGTH_SHORT).show();
//            userProfileImage.setImageURI(Uri.parse(profileImage));
            }
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_profile, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            // Respond to a click on the "save" menu option
            case R.id.save_profile:
                Intent saveProfileIntent = new Intent(getContext(), MedicineList.class);
                startActivity(saveProfileIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // When an Image is picked
        if (requestCode == OPEN_GALLERY && resultCode == RESULT_OK && null != data) {

            Glide.with(UserProfile.this).load(data.getData()).into(userProfileImage);
            // Get the Image from data
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Glide.with(UserProfile.this).load(selectedImage).into(userProfileImage);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit_user_image:
                changeProfilePixsIntent();
                break;

            case R.id.password:
                changeProfilePasswordIntent();
                break;

            case R.id.edit_details_textView:
                editUserProfileIntent();
                break;
        }
    }

    public void editUserProfileIntent() {
        String username = userNameTextView.getText().toString();
        String userMobile = userMobileTextView.getText().toString();
        String userDob = userDobTextView.getText().toString();
        String userGender = userGenderTextView.getText().toString();
        String userMStatus = userMStatusTextView.getText().toString();

        Intent editProfileIntent = new Intent(getContext(), UserProfileEdit.class);
        editProfileIntent.putExtra("username", username);
        editProfileIntent.putExtra("mobile", userMobile);
        editProfileIntent.putExtra("dob", userDob);
        editProfileIntent.putExtra("gender", userGender);
        editProfileIntent.putExtra("marital_status", userMStatus);
        editProfileIntent.putExtra("id", id);
        startActivity(editProfileIntent);
    }

    public void changeProfilePixsIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, OPEN_GALLERY);
    }

    public void changeProfilePasswordIntent() {
        Intent changePasswordIntent = new Intent(getContext(), UserProfileChangePassword.class);
        startActivity(changePasswordIntent);
    }
}
