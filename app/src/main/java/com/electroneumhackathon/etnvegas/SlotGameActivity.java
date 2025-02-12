package com.electroneumhackathon.etnvegas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class SlotGameActivity extends AppCompatActivity {
    private Button spinButton;
    private SlotReelView slot1, slot2, slot3;
    private EditText chipAmountText;
    private BigInteger chipBalance = new BigInteger("0");
    private KeyGenerator keyGenerator;
    private MyWeb3Client myWeb3Client;
    private TextView balanceView;
    private TextView errorMessage;
    private int completedSpins = 0;
    private Button goToWalletButton;
    private LottieAnimationView lottieAnimationView;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_slot_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        keyGenerator = new KeyGenerator(this);
        myWeb3Client = new MyWeb3Client(keyGenerator);
        int[] lastResult = keyGenerator.getLastSpinResult();
        System.out.println("Result---------------------------------" + lastResult[0] + lastResult[1] + lastResult[2]);
        // Initialize views
        slot1 = findViewById(R.id.slot1);
        slot1.initializeSlotIdAndFinalNumber(0, lastResult[0]);
        slot2 = findViewById(R.id.slot2);
        slot2.initializeSlotIdAndFinalNumber(1, lastResult[1]);
        slot3 = findViewById(R.id.slot3);
        slot3.initializeSlotIdAndFinalNumber(2, lastResult[2]);
        chipAmountText = findViewById(R.id.bet_chip_amount_text);
        spinButton = findViewById(R.id.spinButton);
        errorMessage = findViewById(R.id.error_message);
        goToWalletButton = findViewById(R.id.go_to_wallet);
        lottieAnimationView = findViewById(R.id.fetch_lottie_animation_1);
        textView2 = findViewById(R.id.textView2);
        textView2.setVisibility(View.INVISIBLE);
        chipAmountText.setEnabled(false);
        goToWalletButton.setOnClickListener(e -> {
            Intent intent = new Intent(SlotGameActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
        // 1st fetch balance

        balanceView = findViewById(R.id.balance_view);

        new Thread(() -> {
            BigInteger x = myWeb3Client.getChipBalanceInBigInteger();
            if(x != null) {
                chipBalance = x;
            }
           runOnUiThread(() -> {
                balanceView.setText("\uD83E\uDE99 Balance : " + chipBalance + " CHIP");
                lottieAnimationView.cancelAnimation();
                lottieAnimationView.setVisibility(View.GONE);
                if (chipBalance.equals(new BigInteger("0"))) {
                    textView2.setText("Note: Please buy some chip !");
                    textView2.setVisibility(View.VISIBLE);
                    chipAmountText.setEnabled(false);
                } else {
                    textView2.setText("Note: Please do not quit this screen while spinning");
                    textView2.setVisibility(View.VISIBLE);
                    chipAmountText.setEnabled(true);
                }

           });

        }).start();

        BigInteger zero = new BigInteger("0");
        boolean[] isValid = new boolean[1];

        chipAmountText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    String chipBetAmount = charSequence.toString().trim();
                    BigInteger  chipBetAmountBigInt = new BigInteger(chipBetAmount);
                    if(chipBalance.equals(zero) || chipBetAmountBigInt.equals(zero) || chipBalance.compareTo(chipBetAmountBigInt) < 0) {
                        // error occured
                        isValid[0] = false;

                        errorMessage.setText("Invalid CHIP amount");
                        if(errorMessage.getVisibility() == View.INVISIBLE) {
                            errorMessage.setVisibility(View.VISIBLE);
                        }
                        if(spinButton.isEnabled() || spinButton.getVisibility() == View.VISIBLE) {
                            // make it invisible
                            spinButton.setEnabled(false);
                            spinButton.setVisibility(View.INVISIBLE);
                        }

                    } else {
                        isValid[0] = true;
                        // success show spin button
                        if(errorMessage.getVisibility() == View.VISIBLE) {
                            errorMessage.setVisibility(View.INVISIBLE);
                        }
                        if(!spinButton.isEnabled() || spinButton.getVisibility() != View.VISIBLE) {
                            spinButton.setVisibility(View.VISIBLE);
                            spinButton.setEnabled(true);
                        }
                    }

                } catch(Exception ex) {
                    // error occured
                    isValid[0] = false;

                    errorMessage.setText("Invalid CHIP amount");
                    if(errorMessage.getVisibility() == View.INVISIBLE) {
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                    if(spinButton.isEnabled() || spinButton.getVisibility() == View.VISIBLE) {
                        // make it invisible
                        spinButton.setEnabled(false);
                        spinButton.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Start spinning all reels


        // Stop spinning after 5 seconds
        spinButton.setOnClickListener(e -> {
            try {
                String chipBetAmount = chipAmountText.getText().toString().trim();
                BigInteger chipBetAmountInBigInt = new BigInteger(chipBetAmount);
                if (isValid[0]) {
                    chipAmountText.setText("");
                    errorMessage.setVisibility(View.INVISIBLE);
                    // make bet and spin button disable
                    spinButton.setEnabled(false);
                    spinButton.setVisibility(View.INVISIBLE);
                    // disable edit text
                    chipAmountText.setEnabled(false);
                    chipAmountText.setVisibility(View.INVISIBLE);

                    // update the chip balance
                    chipBalance = chipBalance.subtract(chipBetAmountInBigInt);
                    balanceView.setText("\uD83E\uDE99 Balance : " + chipBalance + " CHIP");

                    // disable wallet button
                    goToWalletButton.setVisibility(View.INVISIBLE);
                    goToWalletButton.setEnabled(false);


                    // start the spinning
                    startSlotReel(slot1, 5);
                    startSlotReel(slot2, 6);
                    startSlotReel(slot3, 7);

                    // call the playslot api
                    SlotReelView.setFinalNumber(myWeb3Client, chipBetAmountInBigInt, this);

                    // on completion set chipAmount text to be enabled again.
                }
            } catch (Exception ex) {
                ex.printStackTrace();

            }

        });


    }


    private void startSlotReel(SlotReelView slotReelView, int spins) {
        slotReelView.startSpinning(spins); // Customize the spin count and final number
        slotReelView.setOnSpinningCompleteListener(() -> {
            onSingleReelComplete();
        });
    }

    private synchronized void onSingleReelComplete() {
        completedSpins++;
        if (completedSpins == 3) { // Check if all three reels are done
            onAllReelsComplete();
        }
    }

    private void onAllReelsComplete() {
        // Perform the desired action when all reels finish spinning
        // set slot last result
        keyGenerator.saveSpinResult(slot1.currentNumber, slot2.currentNumber, slot3.currentNumber);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SlotGameActivity.this, SlotGameResultActivity.class);
            intent.putExtra("similar", countSimilar(slot1.currentNumber, slot2.currentNumber, slot3.currentNumber));
            intent.putExtra("txn_hash", SlotReelView.txnHash);
            startActivity(intent);
        }, 2000); // 3000 milliseconds = 3 seconds

    }

    private int countSimilar(int a, int b, int c) {
        if(a == b && b == c) {
            return 3;
        } else if (a == b || a == c || b == c) {
            return 2;
        } else {
            return 0;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // refresh chip balance
        // set all disabled to enabled

        this.completedSpins = 0;
        chipAmountText.setVisibility(View.VISIBLE);
        goToWalletButton.setVisibility(View.VISIBLE);
        goToWalletButton.setEnabled(true);
        lottieAnimationView.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();

        int[] lastResult = keyGenerator.getLastSpinResult();
        slot1.initializeSlotIdAndFinalNumber(0, lastResult[0]);
        slot2.initializeSlotIdAndFinalNumber(1, lastResult[1]);
        slot3.initializeSlotIdAndFinalNumber(2, lastResult[2]);

        // update chip balance
        // show animation as well
        new Thread(() -> {
            BigInteger x = myWeb3Client.getChipBalanceInBigInteger();
            if(x != null) {
                chipBalance = x;
            }
            runOnUiThread(() -> {
                balanceView.setText("\uD83E\uDE99 Balance : " + chipBalance + " CHIP");
                lottieAnimationView.cancelAnimation();
                lottieAnimationView.setVisibility(View.GONE);
                if (chipBalance.equals(new BigInteger("0"))) {
                    textView2.setText("Note: Please buy some chip !");
                    textView2.setVisibility(View.VISIBLE);
                    chipAmountText.setEnabled(false);
                } else {
                    textView2.setText("Note: Please do not quit this screen while spinning");
                    textView2.setVisibility(View.VISIBLE);
                    chipAmountText.setEnabled(true);
                }
            });
        }).start();

    }

}