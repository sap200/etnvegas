package com.electroneumhackathon.etnvegas;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.electroneumhackathon.etnvegas.R;


import cryptooperations.KeyGenerator;
import cryptooperations.QRCodeGenerator;

public class ReceiveEthActivity extends AppCompatActivity {

    Bitmap qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_receive_eth);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView qrcode = findViewById(R.id.address_qr);
        TextView address = findViewById(R.id.address_display);
        TextView arbBridge = findViewById(R.id.arb_bridge_text_view);
        KeyGenerator keyGenerator = new KeyGenerator(this);
        String adr = keyGenerator.getAddress();

        new Thread(() -> {
            qr = QRCodeGenerator.generateQRcode(adr);

            runOnUiThread(() -> {
                qrcode.setImageBitmap(qr);
                address.setText(adr);
            });
        }).start();

        qrcode.setOnClickListener(e -> {
            // Get the ClipboardManager service
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            // Create a ClipData with the private key text
            ClipData clip = ClipData.newPlainText("address_copied_from_qr", adr);

            // Copy the text to the clipboard
            clipboard.setPrimaryClip(clip);

            // Show a Toast confirmation
            Toast.makeText(ReceiveEthActivity.this, "Address copied to clipboard!", Toast.LENGTH_LONG).show();
        });

        arbBridge.setOnClickListener(e -> {
            // Get the ClipboardManager service
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            // Create a ClipData with the private key text
            ClipData clip = ClipData.newPlainText("arbitrum_bridge_link", "https://bridge.arbitrum.io/");

            // Copy the text to the clipboard
            clipboard.setPrimaryClip(clip);

            // Show a Toast confirmation
            Toast.makeText(ReceiveEthActivity.this, "Arbitrum Bridge Link copied to clipboard!", Toast.LENGTH_LONG).show();
        });

        address.setOnClickListener(e -> {
            // Get the ClipboardManager service
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            // Create a ClipData with the private key text
            ClipData clip = ClipData.newPlainText("address_copied", adr);

            // Copy the text to the clipboard
            clipboard.setPrimaryClip(clip);

            // Show a Toast confirmation
            Toast.makeText(ReceiveEthActivity.this, "Address copied to clipboard!", Toast.LENGTH_LONG).show();
        });
    }
}