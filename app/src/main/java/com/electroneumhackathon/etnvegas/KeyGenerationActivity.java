package com.electroneumhackathon.etnvegas;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.electroneumhackathon.etnvegas.R;


import java.util.HashMap;
import java.util.Map;

import cryptooperations.KeyGenerator;


public class KeyGenerationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_key_generation);
        KeyGenerator keyGenerator = new KeyGenerator(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Redirect to SecondActivity
        if(keyGenerator.getPrivateKey() != null && keyGenerator.getAddress() != null) {
            Intent intent = new Intent(KeyGenerationActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: Closes the current activity
        }

        TextView keyView = findViewById(R.id.private_key_text);
        Button proceedButton = findViewById(R.id.proceed);
        Map<String, String> nw = new HashMap<>();

        new Thread(() -> {
            try {
                // Generate Ethereum key pair
                Map<String, String> wallet = keyGenerator.getTemporaryWallet();
                nw.put("privateKey", wallet.get("privateKey"));
                nw.put("address", wallet.get("address"));
                nw.put("publicKey", wallet.get("publicKey"));
                // Update UI with the key pair
                runOnUiThread(() -> {
                    keyView.setText(wallet.get("privateKey"));
                });
            } catch (Exception e) {
                e.printStackTrace();
                // here we want to render new activity (Error activity)
                runOnUiThread(() ->{
                    Intent intent = new Intent(KeyGenerationActivity.this, GenericErrorActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        }).start();

        keyView.setOnClickListener(v -> {
                String privateKey = (String)keyView.getText();
                if(privateKey != null && !privateKey.equals("") && !privateKey.contains("Error")) {
                    // Get the ClipboardManager service
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                    // Create a ClipData with the private key text
                    ClipData clip = ClipData.newPlainText("Private Key", privateKey);

                    // Copy the text to the clipboard
                    clipboard.setPrimaryClip(clip);

                    // Show a Toast confirmation
                    Toast.makeText(KeyGenerationActivity.this, "Private key copied to clipboard!", Toast.LENGTH_LONG).show();
                }
        });

        proceedButton.setOnClickListener(v -> {
            // Redirect to SecondActivity
            keyGenerator.store(nw);
            Intent intent = new Intent(KeyGenerationActivity.this, InstructionActivity.class);
            startActivity(intent);
            finish(); // Optional: Closes the current activity
        });
    }
}