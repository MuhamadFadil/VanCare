package com.manpro.vancare;

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

public class LoginActivity extends AppCompatActivity {

    //calling activity
    Button callRegisterActivity;
    Button callDashActivity;

    //declaring variables
    TextView textLogin, textRegister;
    ImageView picLogin;
    TextInputLayout editEmailLog, editPasswordLog;
    Button logButton, logToRegister;

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
        editEmailLog = findViewById(R.id.editEmailLog);
        editPasswordLog = findViewById(R.id.editPasswordLog);
        logButton = findViewById(R.id.buttonLoginLog);
        logToRegister = findViewById(R.id.buttonRegisterLog);

        //go to register activity
        callRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //animation
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(textLogin, "text_main");
                pairs[1] = new Pair<View, String>(editEmailLog, "edit_email");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

    }
}