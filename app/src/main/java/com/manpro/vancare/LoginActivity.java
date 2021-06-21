package com.manpro.vancare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    //calling activity
    private Button callRegisterActivity;
    private Button callDashActivity;
    //Button callProfileActivity;

    //declaring variables
    private TextView textLogin, textRegister;
    private ImageView picLogin;
    private TextInputLayout emailLog, passwordLog;
    private Button logButton, logToRegister;
    private String email, password;
    private static final String TAG = "LoginActivity";

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //activity
        callRegisterActivity = findViewById(R.id.buttonRegisterLog);
        callDashActivity = findViewById(R.id.buttonLoginLog);

        //hooks
        textLogin = findViewById(R.id.textLogin);
        textRegister = findViewById(R.id.textRegister);
        picLogin = findViewById(R.id.picLogin);
        emailLog = findViewById(R.id.editEmailLog);
        passwordLog = findViewById(R.id.editPasswordLog);
        logButton = findViewById(R.id.buttonLoginLog);
        logToRegister = findViewById(R.id.buttonRegisterLog);
        //nikLog = findViewById(R.id.nikReg);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        //checking if user is logged in
//        if (mAuth.getCurrentUser() != null) {
//            updateUI(mAuth.getCurrentUser());
//        }
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, ProfileActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Please login", Toast.LENGTH_LONG).show();
                }
            }
        };

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailLog.getEditText().getText().toString();
                password = passwordLog.getEditText().getText().toString();

                if (email.isEmpty()) {
                    emailLog.setError("Please provide email");
                    emailLog.requestFocus();
                } else if (password.isEmpty()) {
                    passwordLog.setError("Please provide password");
                    passwordLog.requestFocus();
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fiels are empty"
                            , Toast.LENGTH_LONG).show();
                } else if (!(email.isEmpty() && password.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intProfile = new Intent(LoginActivity.this, ProfileActivity.class);
                                        startActivity(intProfile);
                                    } else {
                                        Log.w(TAG, "SignIn Failed", task.getException());
                                        Toast.makeText(getApplicationContext(), "Authentication failed",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(LoginActivity.this, "Error Occured", Toast.LENGTH_LONG).show();
                }
            }
        });

        //go to register activity
        callRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //animation
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(textLogin, "text_main");
                pairs[1] = new Pair<View, String>(emailLog, "edit_email");
                //pairs[2] = new Pair<View, String>(logButton, "button_login");
                //pairs[3] = new Pair<View, String>(logToRegister, "button_register");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

//        //go to Dashboard page
//        callDashActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //validate and into next activity
//                if (!validationEmail() | !validationPassword()) {
//                    return;
//                } else {
//                    isUser();
//                }
//
//            }
//        });

    }

    private void updateUI(FirebaseUser currentUser) {
        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
        profileIntent.putExtra("email", currentUser.getEmail());
        Log.v("DATA", currentUser.getUid());
        startActivity(profileIntent);
    }


    //validation for username
    private Boolean validationEmail() {

        String val = emailLog.getEditText().getText().toString();
        //String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            emailLog.setError("Field cannot be empty");
            return false;
        //}
//        else if (!val.matches(noWhiteSpace)) {
//            editEmailLog.setError("White Spaces are not allowed");
//            return false;
        } else
        {
            emailLog.setError(null);
            emailLog.setErrorEnabled(false);
            return true;
        }

    }

    //validation for password
    private Boolean validationPassword() {

        String val = passwordLog.getEditText().getText().toString();

        if (val.isEmpty()) {
            passwordLog.setError("Field cannot be empty");
            return false;
        } else {
            passwordLog.setError(null);
            passwordLog.setErrorEnabled(false);
            return true;
        }

    }

    //check username and password
    private void isUser() {

        //String nikRegLog = nikLog.getEditText().getText().toString().trim();
        String userEnteredEmail = emailLog.getEditText().getText().toString().trim();
        String userEnteredPassword = passwordLog.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser = reference.orderByChild("email").equalTo(userEnteredEmail);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    emailLog.setError(null);
                    //emailLog.setErrorEnabled(false);

                    //String email
                    String passwordFromDB = dataSnapshot.child(userEnteredEmail).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)) {

                        passwordLog.setError(null);
                        //passwordLog.setErrorEnabled(false);

                        String nameFromDB = dataSnapshot.child(userEnteredEmail).child("name").getValue(String.class);
                        //String usernameFromDB = dataSnapshot.child(userEnteredEmail).child("email").getValue(String.class);
                        String phoneNumberFromDB = dataSnapshot.child(userEnteredEmail).child("phoneNumber").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredEmail).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);

                        intent.putExtra("name", nameFromDB);
                        //intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phoneNumber", phoneNumberFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                        finish();

                    } else {
                        passwordLog.setError("Wrong Password");
                        passwordLog.requestFocus();
                    }
                } else {
                    emailLog.setError("No such user exist");
                    emailLog.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}