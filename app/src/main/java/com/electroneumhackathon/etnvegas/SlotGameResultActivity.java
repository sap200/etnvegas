package com.electroneumhackathon.etnvegas;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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


public class SlotGameResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_slot_game_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        int similar = intent.getIntExtra("similar", -1);
        String error = intent.getStringExtra("error");
        String txnHash = intent.getStringExtra("txn_hash");

        LottieAnimationView lottieAnimationView = findViewById(R.id.game_status_lottie_animation);
        TextView textView2X = findViewById(R.id.game_status_text);
        TextView textViewDetails = findViewById(R.id.game_details_text);
        TextView txnHashTextView = findViewById(R.id.txn_hash_text);
        Button goToPlayButton = findViewById(R.id.slot_btn);
        textViewDetails.setMovementMethod(new ScrollingMovementMethod());

        goToPlayButton.setEnabled(true);

        txnHashTextView.setOnClickListener(e -> {
            if(txnHashTextView.isEnabled()) {
                // Get the ClipboardManager service
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                // Create a ClipData with the private key text
                ClipData clip = ClipData.newPlainText("txn_hash_from_game", txnHashTextView.getText().toString().trim());

                // Copy the text to the clipboard
                clipboard.setPrimaryClip(clip);

                // Show a Toast confirmation
                Toast.makeText(SlotGameResultActivity.this, "Transaction Hash copied to clipboard!", Toast.LENGTH_LONG).show();
            }
        });

        if(error != null) {

            goToPlayButton.setText("Go to Wallet");
            goToPlayButton.setOnClickListener(e -> {
                Intent intent1 = new Intent(SlotGameResultActivity.this, MainActivity.class);
                // finish all activities
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                finish();
            });
        } else {
            goToPlayButton.setOnClickListener(e -> {
                finish();
            });
        }

        if(error == null) {


            if (similar == 3) {
                lottieAnimationView.setAnimation(R.raw.slot_won);
                lottieAnimationView.playAnimation();
                textView2X.setText("You won 3X");
                textView2X.setVisibility(View.VISIBLE);
                textViewDetails.setVisibility(View.INVISIBLE);
                goToPlayButton.setEnabled(true);
                goToPlayButton.setVisibility(View.VISIBLE);
                if(txnHash != null) {
                    txnHashTextView.setText(txnHash);
                    txnHashTextView.setVisibility(View.VISIBLE);
                    txnHashTextView.setEnabled(true);
                }
            } else if (similar == 2) {
                lottieAnimationView.setAnimation(R.raw.slot_won);
                lottieAnimationView.playAnimation();
                textView2X.setText("You won 2X");
                textViewDetails.setVisibility(View.INVISIBLE);
                goToPlayButton.setEnabled(true);
                goToPlayButton.setVisibility(View.VISIBLE);
                if(txnHash != null) {
                    txnHashTextView.setText(txnHash);
                    txnHashTextView.setVisibility(View.VISIBLE);
                    txnHashTextView.setEnabled(true);
                }
            } else {
                lottieAnimationView.setAnimation(R.raw.slot_lost);
                lottieAnimationView.playAnimation();
                textView2X.setText("You Lost");
                textViewDetails.setText("Don't worry try your luck again !");
                textViewDetails.setVisibility(View.VISIBLE);
                goToPlayButton.setEnabled(true);
                goToPlayButton.setVisibility(View.VISIBLE);
                if(txnHash != null) {
                    txnHashTextView.setText(txnHash);
                    txnHashTextView.setVisibility(View.VISIBLE);
                    txnHashTextView.setEnabled(true);
                }
            }
        } else {
            // error occured
            lottieAnimationView.setAnimation(R.raw.failure);
            lottieAnimationView.playAnimation();
            textView2X.setText("Error occured");
            textView2X.setVisibility(View.VISIBLE);
            textViewDetails.setText(error + ":: Possibly you don't have enough ether for gas. Please topup your wallet and check again");
            textViewDetails.setVisibility(View.VISIBLE);
            if(txnHash != null) {
                txnHashTextView.setText(txnHash);
                txnHashTextView.setVisibility(View.VISIBLE);
                txnHashTextView.setEnabled(true);
            }
            goToPlayButton.setEnabled(true);
            goToPlayButton.setVisibility(View.VISIBLE);

        }

    }
}