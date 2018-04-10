package com.africanego.med_manager.ui;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.africanego.med_manager.R;
import com.africanego.med_manager.db.UserDatabase;
import com.africanego.med_manager.db.UserDetails;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class AppWelcome extends AppCompatActivity {

    private SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private EditText newUserFirstname, newUserLastname, newUserEmail, newUserPassword, newUserPasswordConfirm;
    private ImageView cancelDialogImageView;
    private TextView signupTextView, notReadyForSignupTextView;
   // public static UserDatabase mUserDatabase;
    private static final int RC_SIGN_IN = 100;
    private static final String TAG = "app_welcome";
    private CarouselView carouselView;
    public static UserDatabase mUserDatabase;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_welcome);

        mUserDatabase = Room.databaseBuilder(this,
                UserDatabase.class, "user_profile_db").allowMainThreadQueries().build();

        final int[] sampleImages = {R.color.colorAccent, R.color.signup_text, R.color.colorPrimaryDark};

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        };

        carouselView.setImageListener(imageListener);

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void loginAuth(View view) {
        Intent loginpageIntent = new Intent(this, AppAuthPage.class);
        startActivity(loginpageIntent);
    }

    public void signup(View view) {
       showNewSignupDialog();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, do something.
            String userName = account.getDisplayName();
            String userEmail = account.getEmail();

            UserDetails newUserDetails = new UserDetails();
            newUserDetails.setFirstname(userName);
            newUserDetails.setEmail(userEmail);

            long rowAdded = mUserDatabase.userDao().insertUserProfile(newUserDetails);
            if (rowAdded > 0){
                Toast.makeText(this, "Logging successful", Toast.LENGTH_SHORT).show();
                Intent medLIstIntent = new Intent(AppWelcome.this, MedicineList.class);
                startActivity(medLIstIntent);
            }else {
                Toast.makeText(this, "Error logging, try to use sign up form", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(this, "You are "+ userName + " " + userEmail, Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void showNewSignupDialog() {
        final Dialog customDialog = new Dialog(this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_signup);
        customDialog.setCancelable(true);
        customDialog.show();
        Window window = customDialog.getWindow();
        window.setLayout(android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT);

        newUserFirstname = customDialog.findViewById(R.id.signup_firstname);
        newUserLastname = customDialog.findViewById(R.id.signup_lastname);
        newUserEmail = customDialog.findViewById(R.id.signup_email);
        newUserPassword = customDialog.findViewById(R.id.signup_password);
        newUserPasswordConfirm = customDialog.findViewById(R.id.signup_confirm_password);
        signupTextView = customDialog.findViewById(R.id.btn_signup);
        notReadyForSignupTextView = customDialog.findViewById(R.id.not_ready_for_signup);
        cancelDialogImageView = customDialog.findViewById(R.id.cancel_signup_page);

        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userFirstname = newUserFirstname.getText().toString();
                String userLastname = newUserLastname.getText().toString();
                String userEmail = newUserEmail.getText().toString();
                String userPassword = newUserPassword.getText().toString();
                String userPasswordConfirm = newUserPasswordConfirm.getText().toString();

                UserDetails userDetails = new UserDetails();
                userDetails.setFirstname(userFirstname);
                userDetails.setLastname(userLastname);
                userDetails.setEmail(userEmail);
                userDetails.setPassword(userPassword);

                HomeFragment.mUserDatabase.userDao().insertUserProfile(userDetails);

            }
        });
        notReadyForSignupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

        cancelDialogImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
    }
}
