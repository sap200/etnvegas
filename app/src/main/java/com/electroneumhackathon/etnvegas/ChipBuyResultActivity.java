package com.electroneumhackathon.etnvegas;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.electroneumhackathon.etnvegas.R;

import java.math.BigInteger;

import cryptooperations.KeyGenerator;
import cryptooperations.MyWeb3Client;

public class ChipBuyResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chip_buy_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();

        String amount = intent.getStringExtra("chipAmount");    // Retrieve the integer
        BigInteger amountInBigInt = new BigInteger(amount);

        System.out.println("CHIP BUY AMOUNT: " + amountInBigInt);

        KeyGenerator keyGenerator = new KeyGenerator(this);
        MyWeb3Client myWeb3Client = new MyWeb3Client(keyGenerator);


        LottieAnimationView lottieAnimationView = findViewById(R.id.buy_status_lottie_animation);
        TextView txnStatus = findViewById(R.id.buy_status_text);
        TextView txnDetails = findViewById(R.id.buy_details_text);
        TextView txnHash = findViewById(R.id.buy_txn_hash_text);
        Button shopButton = findViewById(R.id.buy_activity_wallet_btn);
        txnDetails.setMovementMethod(new ScrollingMovementMethod());




        shopButton.setOnClickListener(e -> {
            finish();
        });

        txnHash.setOnClickListener(e -> {
            if(txnHash.isEnabled()) {
                // Get the ClipboardManager service
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                // Create a ClipData with the private key text
                ClipData clip = ClipData.newPlainText("buy_txn_hash", txnHash.getText().toString().trim());

                // Copy the text to the clipboard
                clipboard.setPrimaryClip(clip);

                // Show a Toast confirmation
                Toast.makeText(ChipBuyResultActivity.this, "Transaction Hash copied to clipboard!", Toast.LENGTH_LONG).show();
            }
        });

        new Thread(() -> {
            // send eth txn
            String[] result =  myWeb3Client.buyChips(amountInBigInt);
            System.out.println("Here inside error occured");

            if(result[0].equals(MyWeb3Client.ERROR_OCCURED)) {
                System.out.println("Here inside error occured");
                // error occured
                runOnUiThread(() -> {
                    lottieAnimationView.setAnimation(R.raw.failure);
                    lottieAnimationView.setRepeatCount(0);
                    lottieAnimationView.playAnimation();
                    txnStatus.setText("Buy Failed");
                    txnStatus.setTextColor(Color.RED);
                    if(result[2] == null) {
                        txnDetails.setText("Please tap on the transaction hash to copy it to the clipboard and check details in https://blockexplorer.thesecurityteam.rocks/");
                    } else {
                        txnDetails.setText(result[2]);
                    }
                    if(result[1] != null) {
                        txnHash.setEnabled(true);
                        txnHash.setVisibility(View.VISIBLE);
                        txnHash.setText(result[1]);
                    }
                    shopButton.setEnabled(true);
                    shopButton.setVisibility(View.VISIBLE);
                });

            } else {
                // no error
                // Perform your second action here
                runOnUiThread(() -> {
                    lottieAnimationView.setAnimation(R.raw.success);
                    lottieAnimationView.setRepeatCount(0);
                    lottieAnimationView.playAnimation();
                    txnStatus.setText("Buy Success");
                    txnStatus.setTextColor(Color.parseColor("#3aa832"));
                    txnDetails.setText("Please tap on the transaction hash to copy it to the clipboard and check validity in https://blockexplorer.electroneum.com/");
                    txnHash.setEnabled(true);
                    txnHash.setVisibility(View.VISIBLE);
                    txnHash.setText(result[1]);
                    shopButton.setEnabled(true);
                    shopButton.setVisibility(View.VISIBLE);
                });
            }

        }).start();
    }
}