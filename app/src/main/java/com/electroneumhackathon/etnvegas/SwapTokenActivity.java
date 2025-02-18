package com.electroneumhackathon.etnvegas;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.electroneumhackathon.etnvegas.R;


import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

import cryptooperations.KeyGenerator;
import cryptooperations.MyWeb3Client;

public class SwapTokenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_swap_token);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // set animation for text view
        ImageView swapImage = findViewById(R.id.swap_image);
        EditText chipsAmount = findViewById(R.id.swap_chip_amount);
        TextView maxChipDisplay = findViewById(R.id.max_chip_balance_show);
        TextView exchangeAmount = findViewById(R.id.max_eth_value_received);
        TextView ethPricePerChip = findViewById(R.id.chip_price_per_unit);
        Button swapButton = findViewById(R.id.swap_chips_button);
        LottieAnimationView lottieAnimationView = findViewById(R.id.fetch_lottie_animation);
        chipsAmount.setEnabled(false);

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(swapImage, "rotation", 0f, 360f);
        rotationAnimator.setDuration(2000); // 2 seconds for a full rotation
        rotationAnimator.setRepeatCount(ObjectAnimator.INFINITE); // Repeat forever
        rotationAnimator.setRepeatMode(ObjectAnimator.RESTART); // Restart the animation
        // preserve this only when user types a value

        KeyGenerator keyGenerator = new KeyGenerator(this);
        MyWeb3Client myWeb3Client = new MyWeb3Client(keyGenerator);
        maxChipDisplay.setText("Max " + keyGenerator.getSavedChips() + " CHIP");
        // onload i want to load the max value of chips and eth price per unit
        BigInteger[] sellPriceData = new BigInteger[2];
        BigInteger maxChipAvailable = new BigInteger(keyGenerator.getSavedChips());
        new Thread(() -> {
            try {
                BigInteger sellPrice = myWeb3Client.getSellPricePerToken();
                BigDecimal inEther = Convert.fromWei(sellPrice.toString(), Convert.Unit.ETHER);
                BigInteger feePercentage = myWeb3Client.getFeePercentage();
                sellPriceData[0] = sellPrice;
                sellPriceData[1] = feePercentage;
                runOnUiThread(() -> {
                    ethPricePerChip.setText("1 CHIP = " + inEther.toString() + " ETN | FEE " + feePercentage + "%");
                    chipsAmount.setEnabled(true);
                    lottieAnimationView.cancelAnimation();
                    lottieAnimationView.setVisibility(View.GONE);


                });
            } catch (Exception ex) {
                ex.printStackTrace();
                runOnUiThread(() -> {
                    Intent intent = new Intent(SwapTokenActivity.this, GenericErrorActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        }).start();

        boolean[] isValid = new boolean[1];
        // on entering amount i wanna validate max chip amount
        // as he types i wanna calculate the eth amount he gets
        BigInteger zero = new BigInteger("0");
        chipsAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String chipAmount = charSequence.toString().trim();
                try {
                    BigInteger bChipAmount = new BigInteger(chipAmount);
                    if(maxChipAvailable.compareTo(bChipAmount) >= 0 && !bChipAmount.equals(zero)) {
                        if(!rotationAnimator.isRunning()) {
                            rotationAnimator.start();
                        }
                        if(!maxChipDisplay.getTextColors().equals(Color.parseColor("#7B1FA2"))) {
                            maxChipDisplay.setTextColor(Color.parseColor("#7B1FA2"));
                        }
                        BigInteger ethValueMaxReceived = bChipAmount.multiply(sellPriceData[0]);
                        BigInteger feeValue = (sellPriceData[1].multiply(ethValueMaxReceived)).divide(BigInteger.valueOf(100L));
                        BigInteger ethValueReceivedAfterFee = ethValueMaxReceived.subtract(feeValue);
                        BigDecimal inEther = Convert.fromWei(ethValueReceivedAfterFee.toString(), Convert.Unit.ETHER);

                        exchangeAmount.setText(inEther.toString() + " ETN");
                        // enable button
                        if(!swapButton.isEnabled() || swapButton.getVisibility() != View.VISIBLE) {
                            // disable
                            swapButton.setEnabled(true);
                            swapButton.setVisibility(View.VISIBLE);
                        }
                        isValid[0] = true;
                    } else {
                        if(rotationAnimator.isRunning()) {
                            rotationAnimator.cancel();
                            rotationAnimator.setCurrentFraction(0f);
                        }
                        exchangeAmount.setText("0 ETN");
                        maxChipDisplay.setTextColor(Color.RED);
                        if(swapButton.isEnabled() || swapButton.getVisibility() == View.VISIBLE) {
                            // disable
                            swapButton.setEnabled(false);
                            swapButton.setVisibility(View.INVISIBLE);
                        }

                        isValid[0] = false;

                    }
                } catch(Exception ex) {
                    // dont do anything...
                    if(rotationAnimator.isRunning()) {
                        rotationAnimator.cancel();
                        rotationAnimator.setCurrentFraction(0f);

                    }
                    exchangeAmount.setText("0 ETN");
                    maxChipDisplay.setTextColor(Color.parseColor("#7B1FA2"));
                    if(swapButton.isEnabled() || swapButton.getVisibility() == View.VISIBLE) {
                        // disable
                        swapButton.setEnabled(false);
                        swapButton.setVisibility(View.INVISIBLE);
                    }

                    isValid[0] = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // and display his exchange rate by multiplying.
        swapButton.setOnClickListener(e -> {
            if(isValid[0]) {
                // navigate to other activity to send ether
                Intent intent = new Intent(SwapTokenActivity.this, SwapResultActivity.class);
                intent.putExtra("chipAmount", chipsAmount.getText().toString().trim());  // Passing a string
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid chip amount", Toast.LENGTH_LONG).show();
            }
        });

    }
}