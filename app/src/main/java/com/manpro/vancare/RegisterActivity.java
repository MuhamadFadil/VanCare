package com.manpro.vancare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.lang.reflect.Array;
import java.security.cert.PKIXRevocationChecker;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
    }
}