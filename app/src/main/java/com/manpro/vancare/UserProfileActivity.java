package com.manpro.vancare;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import android.app.AppComponentFactory;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfileActivity extends AppCompatActivity {
    //declaring variable
    TextInputLayout editName, editEmailAddress, editPhoneNumber, editPassword;
    TextView proName, proUsername;
    Button proUpdate, proLogout;
    FloatingActionButton proDrawer;

    //global variable
    String user_username, user_name, user_email, user_phoneNumber, user_password;

    //firebase
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        reference = FirebaseDatabase.getInstance().getReference("Users");

        //hooks
        editName = findViewById(R.id.editProfileName);
        editEmailAddress = findViewById(R.id.editProfileEmail);
        editPhoneNumber = findViewById(R.id.editProfilePhone);
        //editPassword = findViewById(R.id.editProfilePassword);
        proName = findViewById(R.id.profileName);
        proUsername = findViewById(R.id.profileName);
        proUpdate = findViewById(R.id.updateProfile);
        proLogout = findViewById(R.id.logoutProfile);
        proDrawer = findViewById(R.id.floatingMenu);


        //show all data
        showAllUserData();

        //change and check data profile
        proUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNameChanged() || isPasswordChanged() || isEmailChanged() || isPhoneChanged()) {
                    Toast.makeText(UserProfileActivity.this, "Data has been updated", Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(UserProfileActivity.this, "Data is the same and can not be updated", Toast.LENGTH_LONG).show();
            }
        });

        //logout
        proLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        //side menu
        proDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserProfileActivity.this, UserProfileActivity.class);
                startActivity(intent);

            }
        });

    }

    //show user profile
    private void showAllUserData() {

        Intent intent = getIntent();
        user_username = intent.getStringExtra("name");
        user_name = intent.getStringExtra("name");
        user_email = intent.getStringExtra("email");
        user_phoneNumber = intent.getStringExtra("phoneNumber");
        //user_password = intent.getStringExtra("password");

        proName.setText(user_name);
        proUsername.setText(user_username);
        editName.getEditText().setText(user_name);
        editEmailAddress.getEditText().setText(user_email);
        editPhoneNumber.getEditText().setText(user_phoneNumber);
        //editPassword.getEditText().setText(user_password);

    }

    //change name checker
    private boolean isNameChanged() {

        if (!user_name.equals(editName.getEditText().getText().toString())) {
            reference.child(user_username).child("name").setValue(editName.getEditText().getText().toString());
            user_name = editName.getEditText().getText().toString();
            proName.setText(user_name);
            return true;
        } else {
            return false;
        }
    }

    //change password checker
    private boolean isPasswordChanged() {

        if (!user_password.equals(editPassword.getEditText().getText().toString())) {
            reference.child(user_username).child("password").setValue(editPassword.getEditText().getText().toString());
            user_password = editPassword.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

    //change email checker
    private boolean isEmailChanged() {
        if (!user_email.equals(editEmailAddress.getEditText().getText().toString())) {
            reference.child(user_username).child("email").setValue(editEmailAddress.getEditText().getText().toString());
            user_email = editEmailAddress.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

    //change phone number checker
    private boolean isPhoneChanged() {
        if (!user_phoneNumber.equals(editPhoneNumber.getEditText().getText().toString())) {
            reference.child(user_username).child("phoneNumber").setValue(editPhoneNumber.getEditText().getText().toString());
            user_phoneNumber = editPhoneNumber.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

}
