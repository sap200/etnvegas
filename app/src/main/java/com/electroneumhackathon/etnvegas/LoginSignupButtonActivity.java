package com.electroneumhackathon.etnvegas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.electroneumhackathon.etnvegas.R;


import cryptooperations.KeyGenerator;

public class LoginSignupButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        KeyGenerator keyGenerator = new KeyGenerator(this);
        setContentView(R.layout.activity_login_signup_button);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Delay for 1 seconds (5000 ms)
        new Handler().postDelayed(() -> {
            // Redirect to SecondActivity
            if(keyGenerator.getPrivateKey() != null && keyGenerator.getAddress() != null) {
                Intent intent = new Intent(LoginSignupButtonActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: Closes the current activity
            } else {
                Intent intent = new Intent(LoginSignupButtonActivity.this, RealLoginSignupButtonActivity.class);
                startActivity(intent);
                finish(); // Optional: Closes the current activity
            }
        }, 1000);
    }
}