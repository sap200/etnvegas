package com.electroneumhackathon.etnvegas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.electroneumhackathon.etnvegas.R;


import java.util.Map;

import cryptooperations.KeyGenerator;

public class RealLoginSignupButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_real_login_signup_button);
        KeyGenerator keyGenerator = new KeyGenerator(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Redirect to SecondActivity
        if(keyGenerator.getPrivateKey() != null && keyGenerator.getAddress() != null) {
            Intent intent = new Intent(RealLoginSignupButtonActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: Closes the current activity
        }

        Button signupButton = findViewById(R.id.signup);
        Button loginButton = findViewById(R.id.login);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(() -> {
                    try {
                        // Generate Ethereum key pair
                        Map<String, String> wallet = keyGenerator.GenerateWallet();
                        // Update UI with the key pair
                        keyGenerator.saveKeysTemporarily(wallet);
                        runOnUiThread(() -> {
                            Intent intent = new Intent(RealLoginSignupButtonActivity.this, KeyGenerationActivity.class);
                            startActivity(intent);
                            finish(); // Optional: Closes the current activity
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        // here we want to render new activity (Error activity)
                        runOnUiThread(() ->{
                            Intent intent = new Intent(RealLoginSignupButtonActivity.this, GenericErrorActivity.class);
                            startActivity(intent);
                            finish();
                        });
                    }
                }).start();

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // take to the Key generation activity
                // Redirect to SecondActivity
                Intent intent = new Intent(RealLoginSignupButtonActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                // don't finish prev activity because user can forget and come back here to sign up
            }
        });
    }
}