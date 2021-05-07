package com.manpro.vancare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    Thread timer;
    SharedPreferences walkThroughScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        timer = new Thread(){
            @Override
            public void run() {
                try {
                    synchronized (this){
                        wait(5000);
                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    walkThroughScreen = getSharedPreferences("walkThroughScreen", MODE_PRIVATE);
                    boolean isFirstTime = walkThroughScreen.getBoolean("firstTime", true);

                    if(isFirstTime){

                        SharedPreferences.Editor editor = walkThroughScreen.edit();
                        editor.putBoolean("firstTime",false);
                        editor.commit();

                        Intent intent = new Intent(SplashActivity.this, Walk_through.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        Intent intent = new Intent(SplashActivity.this, Walk_through.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };
        timer.start();

    }
}