package com.example.welcome.cryptle;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 2000 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
                // Toast.makeText(getApplicationContext(), "You aren't connected to the internet.", Toast.LENGTH_SHORT).show();
                // finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
