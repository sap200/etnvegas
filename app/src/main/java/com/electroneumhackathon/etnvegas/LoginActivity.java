package com.electroneumhackathon.etnvegas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.electroneumhackathon.etnvegas.R;

import cryptooperations.KeyGenerator;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        KeyGenerator keyGenerator = new KeyGenerator(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Redirect to SecondActivity
        if(keyGenerator.getPrivateKey() != null && keyGenerator.getAddress() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: Closes the current activity
        }

        Button proceedButton = findViewById(R.id.login_proceed);
        EditText editText = findViewById(R.id.inp_private_key);

        proceedButton.setOnClickListener(v -> {
            if(editText.getText() != null && !editText.getText().equals("") ) {
                boolean isValid = keyGenerator.validateAndStoreKey(editText.getText().toString().trim());
                if(isValid) {
                    // proceed to the main activity
                    // Redirect to SecondActivity
                    Intent intent = new Intent(LoginActivity.this, InstructionActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Closes the current activity
                } else {
                    // make a toast saying invalid private key
                    Toast.makeText(LoginActivity.this, "Invalid private key !", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}