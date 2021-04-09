package com.manpro.vancare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity{
    //calling activity
    Button callRegisterActivity;
    Button callProfileActivity;

    //declaring variables
    ImageView loginLogo;
    TextView textStart;
    TextInputLayout logUsername, logPassword;
    Button logButton, logToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //activity
        callRegisterActivity = findViewById(R.id.buttonRegisterLog);
        callProfileActivity = findViewById(R.id.buttonLoginLog);

        //hooks
        loginLogo = findViewById(R.id.picLogoLogin);
        textStart = findViewById(R.id.welcomeTextLogin);
        logUsername = findViewById(R.id.editUsernameLog);
        logPassword = findViewById(R.id.editPasswordLog);
        logButton = findViewById(R.id.buttonLoginLog);
        logToRegister = findViewById(R.id.buttonRegisterLog);

        //go to register page
        callRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //animation
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                Pair[] pairs = new Pair[6];
                pairs[0] = new Pair<View, String>(loginLogo, "logo_image");
                pairs[1] = new Pair<View, String>(textStart, "text_image");
                pairs[2] = new Pair<View, String>(logUsername, "edit_username");
                pairs[3] = new Pair<View, String>(logPassword, "edit_password");
                pairs[4] = new Pair<View, String>(logButton, "button_login");
                pairs[5] = new Pair<View, String>(logToRegister, "button_register");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        //go to profile page
        callProfileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validate and into next activity
                if (!validationUsername() | !validationPassword()) {
                    return;
                } else {
                    isUser();
                }

            }
        });


    }

    //validation for username
    private Boolean validationUsername() {

        String val = logUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            logUsername.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            logUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            logUsername.setError(null);
            logUsername.setErrorEnabled(false);
            return true;
        }

    }

    //validation for password
    private Boolean validationPassword() {

        String val = logPassword.getEditText().getText().toString();

        if (val.isEmpty()) {
            logPassword.setError("Field cannot be empty");
            return false;
        } else {
            logPassword.setError(null);
            logPassword.setErrorEnabled(false);
            return true;
        }

    }

    //check username and password
    private void isUser() {

        String userEnteredUsername = logUsername.getEditText().getText().toString().trim();
        String userEnteredPassword = logPassword.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    logUsername.setError(null);
                    logUsername.setErrorEnabled(false);

                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)) {

                        logPassword.setError(null);
                        logPassword.setErrorEnabled(false);

                        String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNumberFromDB = dataSnapshot.child(userEnteredUsername).child("phoneNumber").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);

                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phoneNumber", phoneNumberFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                        finish();

                    } else {
                        logPassword.setError("Wrong Password");
                        logPassword.requestFocus();
                    }
                } else {
                    logUsername.setError("No such user exist");
                    logUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
