package com.manpro.vancare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationAttributes;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import android.os.Handler;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.AuthResult;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import java.lang.reflect.Array;
import java.security.cert.PKIXRevocationChecker;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    //declare
    private String name,email,nik,kabupaten,kecamatan,faskes, jenis, umur, birth, phoneNumber, password;
    private TextInputLayout regName, regEmail, regNik, regKabupaten, regKecamatan, regFaskes, regJenis, regUmur, regBirth, regPhoneNumber, regPassword;
    private Button regBtn, regToLogin;
    private UserHelperClass user;
    private String TAG = "RegisterActivity";


    //connect to firebase
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //hooks
        regName = findViewById(R.id.nameReg);
        regEmail = findViewById(R.id.emailReg);
        regNik = findViewById(R.id.nikReg);
        regKabupaten = findViewById(R.id.kabupatenReg);
        regKecamatan = findViewById(R.id.kecamatanReg);
        regFaskes = findViewById(R.id.faskesReg);
        regUmur = findViewById(R.id.umurReg);
        regBirth = findViewById(R.id.birthReg);
        regJenis = findViewById(R.id.jenisReg);
        regPassword = findViewById(R.id.passwordReg);
        regPhoneNumber = findViewById(R.id.phoneReg);

        //hook button
        regBtn = findViewById(R.id.regButton);
        regToLogin = findViewById(R.id.backToLogin);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        AutoCompleteTextView myKabupatenOption = (AutoCompleteTextView) findViewById(R.id.kabupatenOption);
        AutoCompleteTextView myKecamatanOption = (AutoCompleteTextView) findViewById(R.id.kecamatanOption);
        AutoCompleteTextView myFasKesOption = (AutoCompleteTextView) findViewById(R.id.faskesOption);
        AutoCompleteTextView myJenisOption = (AutoCompleteTextView) findViewById(R.id.jenisOption);

        ArrayAdapter<String> myKabupatenAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.list_kabupaten_jakarta));
        myKabupatenOption.setAdapter(myKabupatenAdapter);

        ArrayAdapter<String> myKecamatanAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.list_kecamatan_jakarta));
        myKecamatanOption.setAdapter(myKecamatanAdapter);

        ArrayAdapter<String> myFaskesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.faskes));
        myFasKesOption.setAdapter(myFaskesAdapter);

        ArrayAdapter<String> myJenisAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.jenisKelamin));
        myJenisOption.setAdapter(myJenisAdapter);


        //save data in firebase on button onclick
        regBtn.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //validation
                if (!validationEmail() | !validationPassword()) {
                    return;
                }

                //get values in string
                //if (regEmail.getEditText().getText().toString() != null && regPassword.getEditText().getText().toString() != null) {
                    name = regName.getEditText().getText().toString();
                    email = regEmail.getEditText().getText().toString();
                    nik = regNik.getEditText().getText().toString();
                    kabupaten = regKabupaten.getEditText().getText().toString();
                    kecamatan = regKecamatan.getEditText().getText().toString();
                    faskes = regFaskes.getEditText().getText().toString();
                    jenis = regJenis.getEditText().getText().toString();
                    umur = regUmur.getEditText().getText().toString();
                    birth = regBirth.getEditText().getText().toString();
                    phoneNumber = regPhoneNumber.getEditText().getText().toString();
                    password = regPassword.getEditText().getText().toString();

                    //store data in firebase
                    //rootNode = FirebaseDatabase.getInstance();
                    //reference = rootNode.getReference("Users");
                    user = new UserHelperClass(name, email, nik, kabupaten, kecamatan, faskes, jenis, umur, birth, phoneNumber, password);
                    //reference.child(nik).setValue(user);
                    //reference.setValue(helperClass);
                    registerUser();
//                    //account has been made sign
//                    Toast.makeText(RegisterActivity.this, "Account has been made", Toast.LENGTH_LONG).show();
                //}

            }
        });

    }

    public void registerUser(){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG,"Create New Account Success");
                            FirebaseUser user = mAuth.getCurrentUser(); 
                            updateUI(user);
                        }else{
                            Log.w(TAG, "Create New Account Failed", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //delay to login page after account is made
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //into login activity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                //animation
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View, String>(regName, "edit_username");
                pairs[1] = new Pair<View, String>(regPassword, "edit_password");
                pairs[2] = new Pair<View, String>(regBtn, "button_login");
                pairs[3] = new Pair<View, String>(regToLogin, "button_register");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        }, 2000);

        //Back into login activity
        regToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                //animation
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View, String>(regName, "edit_username");
                pairs[1] = new Pair<View, String>(regPassword, "edit_password");
                pairs[2] = new Pair<View, String>(regBtn, "button_login");
                pairs[3] = new Pair<View, String>(regToLogin, "button_register");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
                startActivity(intent, options.toBundle());

            }
        });
    }

    private void updateUI(FirebaseUser currentUser) {
        String keyId = mDatabase.push().getKey();
        mDatabase.child(keyId).setValue(user); //insert to database
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    private Boolean validationEmail() {

        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        Query emailCheck = FirebaseDatabase.getInstance().getReference("Users").orderByChild("email").equalTo(val);
        emailCheck.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 0) {
                    regEmail.setError("Email address already exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        }
        else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validationPassword() {

        String val = regPassword.getEditText().getText().toString();
        final Pattern passwordVal = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        }
        else if (!passwordVal.matcher(val).matches()) {
            regPassword.setError("Password to weak, improve it");
            return false;
        }
        else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }

    }
}