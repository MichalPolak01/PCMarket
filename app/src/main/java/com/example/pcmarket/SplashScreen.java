package com.example.pcmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView loadingIcon = findViewById(R.id.loadingIcon);
        loadingIcon.setAlpha(0f);
        loadingIcon.animate().setDuration(1500).alpha(1f).withEndAction(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
            }
        });

    }
}