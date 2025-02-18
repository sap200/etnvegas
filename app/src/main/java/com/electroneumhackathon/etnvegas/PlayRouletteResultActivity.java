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

import java.math.BigInteger;

public class PlayRouletteResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_roulette_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Intent intent = getIntent();
        String[] result = (String[])intent.getSerializableExtra("my_result");
        String error = intent.getStringExtra("error");
        String txnHash = intent.getStringExtra("txn_hash");


        LottieAnimationView lottieAnimationView = findViewById(R.id.r_game_status_lottie_animation);
        TextView textView2X = findViewById(R.id.r_game_status_text);
        TextView textViewDetails = findViewById(R.id.r_game_details_text);
        TextView txnHashTextView = findViewById(R.id.r_txn_hash_text);
        Button goToPlayButton = findViewById(R.id.r_slot_btn);

        textViewDetails.setMovementMethod(new ScrollingMovementMethod());

        goToPlayButton.setEnabled(true);

        txnHashTextView.setOnClickListener(e -> {
            if(txnHashTextView.isEnabled()) {
                // Get the ClipboardManager service
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                // Create a ClipData with the private key text
                ClipData clip = ClipData.newPlainText("txn_hash_from_roulette_spin_game", txnHashTextView.getText().toString().trim());

                // Copy the text to the clipboard
                clipboard.setPrimaryClip(clip);

                // Show a Toast confirmation
                Toast.makeText(PlayRouletteResultActivity.this, "Transaction Hash copied to clipboard!", Toast.LENGTH_LONG).show();
            }
        });

        if(error != null) {
            goToPlayButton.setText("Go to Wallet");
            goToPlayButton.setOnClickListener(e -> {
                Intent intent1 = new Intent(PlayRouletteResultActivity.this, MainActivity.class);
                // finish all activities
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                finish();
            });
        } else {
            goToPlayButton.setOnClickListener(e -> {
                Intent intent1 = new Intent(PlayRouletteResultActivity.this, RouletteBoardActivity.class);
                // finish all activities
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                finish();
            });
        }



        if(error == null) {
            boolean hasWon = false;
            BigInteger sum = new BigInteger(result[3].trim());
            BigInteger afterDeduction = new BigInteger(result[4].trim());
            BigInteger finalBalance = new BigInteger(result[5].trim());
            BigInteger initBalance = afterDeduction.add(sum);
            if(finalBalance.compareTo(initBalance) >= 0) {
                hasWon =  true;
            } else {
                hasWon = false;
            }

            if(hasWon) {
                if(finalBalance.compareTo(initBalance) == 0) {
                    // got saved no losses
                    lottieAnimationView.setAnimation(R.raw.roulette_wheel_lottie);
                    lottieAnimationView.playAnimation();
                    textView2X.setText("No Win, No Loses ! Try Again");
                    textView2X.setVisibility(View.VISIBLE);
                    textViewDetails.setVisibility(View.VISIBLE);
                    String text1 = "Rewards: " + result[1] + " CHIP\n";
                    String text2 = "Losses: " + result[2] + " CHIP\n";
                    String text3 = "Total Bet: " + result[3] + " CHIP\n";
                    String text4 = "Post-bet Balance: " + result[4] + " CHIP\n";
                    String text5 = "Final Balance: " + result[5] + " CHIP\n";
                    String finalText = text1 + text2 + text3 + text4 + text5;
                    textViewDetails.setText(finalText);

                    goToPlayButton.setEnabled(true);
                    goToPlayButton.setVisibility(View.VISIBLE);
                    if(txnHash != null) {
                        txnHashTextView.setText(txnHash);
                        txnHashTextView.setVisibility(View.VISIBLE);
                        txnHashTextView.setEnabled(true);
                    }

                } else {
                    // won
                    lottieAnimationView.setAnimation(R.raw.slot_won);
                    lottieAnimationView.playAnimation();
                    textView2X.setText("You won " + result[1] + " CHIP");
                    textView2X.setVisibility(View.VISIBLE);
                    textViewDetails.setVisibility(View.VISIBLE);
                    String text1 = "Rewards: " + result[1] + " CHIP\n";
                    String text2 = "Losses: " + result[2] + " CHIP\n";
                    String text3 = "Total Bet: " + result[3] + " CHIP\n";
                    String text4 = "Post-bet Balance: " + result[4] + " CHIP\n";
                    String text5 = "Final Balance: " + result[5] + " CHIP\n";
                    String finalText = text1 + text2 + text3 + text4 + text5;
                    textViewDetails.setText(finalText);

                    goToPlayButton.setEnabled(true);
                    goToPlayButton.setVisibility(View.VISIBLE);
                    if(txnHash != null) {
                        txnHashTextView.setText(txnHash);
                        txnHashTextView.setVisibility(View.VISIBLE);
                        txnHashTextView.setEnabled(true);
                    }
                }

            } else {
                // lost
                lottieAnimationView.setAnimation(R.raw.slot_lost);
                lottieAnimationView.playAnimation();
                textView2X.setText("You Lost");
                String text1 = "Rewards: " + result[1] + " CHIP\n";
                String text2 = "Losses: " + result[2] + " CHIP\n";
                String text3 = "Total Bet: " + result[3] + " CHIP\n";
                String text4 = "Post-bet Balance: " + result[4] + " CHIP\n";
                String text5 = "Final Balance: " + result[5] + " CHIP\n";
                String finalText = "Don't worry try your luck again !\n" + text1 + text2 + text3 + text4 + text5;
                textViewDetails.setText(finalText);

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
            lottieAnimationView.setAnimation(R.raw.failure);
            lottieAnimationView.playAnimation();
            textView2X.setText("Error Occured");
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