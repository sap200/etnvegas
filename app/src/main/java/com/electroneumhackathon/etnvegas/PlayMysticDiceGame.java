package com.electroneumhackathon.etnvegas;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

import java.math.BigInteger;
import java.util.Random;

import cryptooperations.KeyGenerator;
import cryptooperations.MyWeb3Client;

public class PlayMysticDiceGame extends AppCompatActivity {

    private ImageView diceImage;
    private AnimationDrawable rollingAnimation;
    private Random random = new Random();
    private Handler handler = new Handler();
    private boolean isRolling = false;
    private BigInteger chipBalance = new BigInteger("0");
    private KeyGenerator keyGenerator;
    private MyWeb3Client myWeb3Client;
    private TextView balanceView;
    private TextView errorMessage;
    private LottieAnimationView lottieAnimationView;
    private EditText chipAmountText;
    private EditText guessNumber;
    private TextView textView2;
    private Button goToWalletButton;
    private Button betAndRollButton;


    private final int[] diceFaces = {
            R.drawable.dice_1,
            R.drawable.dice_2,
            R.drawable.dice_3,
            R.drawable.dice_4,
            R.drawable.dice_5,
            R.drawable.dice_6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_mystic_dice_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Link resources with ID
        diceImage = findViewById(R.id.dice_animation);
        betAndRollButton = findViewById(R.id.roll_button);
        keyGenerator = new KeyGenerator(this);
        myWeb3Client = new MyWeb3Client(keyGenerator);
        errorMessage = findViewById(R.id.dice_display_error_message);
        balanceView = findViewById(R.id.dice_balance_view);
        lottieAnimationView = findViewById(R.id.fetch_balance_animation_dice_3);
        chipAmountText = findViewById(R.id.bet_chip_on_mystic_dice);
        guessNumber = findViewById(R.id.mystic_dice_choice_number);
        textView2 = findViewById(R.id.dice_warning_message);
        goToWalletButton = findViewById(R.id.go_to_wallet_from_dice);

        int mysticDiceResult = keyGenerator.getLastMysticDiceResult();
        diceImage.setImageResource(diceFaces[mysticDiceResult-1]);

        // set the wallet button on click listener
        goToWalletButton.setOnClickListener(e -> {
            Intent intent = new Intent(PlayMysticDiceGame.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // fetch and update the balance
        new Thread(() -> {
            try {
                BigInteger x = myWeb3Client.getChipBalanceInBigInteger();
                if (x != null) {
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
                        guessNumber.setEnabled(false);
                        chipAmountText.setVisibility(View.GONE);
                        guessNumber.setVisibility(View.GONE);
                    } else {
                        textView2.setText("Note: Please do not quit this screen while spinning");
                        textView2.setVisibility(View.VISIBLE);
                        chipAmountText.setVisibility(View.VISIBLE);
                        guessNumber.setVisibility(View.VISIBLE);
                        chipAmountText.setEnabled(true);
                        guessNumber.setEnabled(true);
                    }
                });
            } catch(Exception ex) {
                runOnUiThread(() -> {
                    Intent intent = new Intent(PlayMysticDiceGame.this, GenericErrorActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        }).start();

        // make a text watch editor for choice number and bet amount
        BigInteger zero = new BigInteger("0");
        boolean[] isValid = new boolean[2];
        BigInteger[] arrayNum = new BigInteger[2];

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

                        errorMessage.setText("Invalid CHIP amount or Guess Number not in range [1-6]");
                        if(errorMessage.getVisibility() != View.VISIBLE) {
                            errorMessage.setVisibility(View.VISIBLE);
                        }
                        if(betAndRollButton.isEnabled() || betAndRollButton.getVisibility() == View.VISIBLE) {
                            // make it invisible
                            betAndRollButton.setEnabled(false);
                            betAndRollButton.setVisibility(View.GONE);
                        }

                    } else {
                        isValid[0] = true;
                        // success show spin button
                        arrayNum[0] = chipBetAmountBigInt;

                        if(isValid[0] && isValid[1]) {
                            if(errorMessage.getVisibility() == View.VISIBLE) {
                                errorMessage.setVisibility(View.GONE);
                            }
                            betAndRollButton.setVisibility(View.VISIBLE);
                            betAndRollButton.setEnabled(true);
                        }
                    }

                } catch(Exception ex) {
                    // error occured
                    isValid[0] = false;

                    errorMessage.setText("Invalid CHIP amount or Guess Number not in range [1-6]");
                    if(errorMessage.getVisibility() != View.VISIBLE) {
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                    if(betAndRollButton.isEnabled() || betAndRollButton.getVisibility() == View.VISIBLE) {
                        // make it invisible
                        betAndRollButton.setEnabled(false);
                        betAndRollButton.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        guessNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    String guessNumber = charSequence.toString().trim();
                    BigInteger guessNumberBig = new BigInteger(guessNumber);

                    if(guessNumberBig.compareTo(new BigInteger("1")) >= 0 && guessNumberBig.compareTo(new BigInteger("6"))  <= 0 ) {
                        isValid[1] = true;
                        arrayNum[1] = guessNumberBig;
                        if(isValid[0] && isValid[1]) {
                            if(errorMessage.getVisibility() == View.VISIBLE) {
                                errorMessage.setVisibility(View.GONE);
                            }
                            betAndRollButton.setVisibility(View.VISIBLE);
                            betAndRollButton.setEnabled(true);
                        }
                    } else {
                        // error occured
                        isValid[1] = false;

                        errorMessage.setText("Invalid CHIP amount or Guess Number not in range [1-6]");
                        if(errorMessage.getVisibility() != View.VISIBLE) {
                            errorMessage.setVisibility(View.VISIBLE);
                        }
                        if(betAndRollButton.isEnabled() || betAndRollButton.getVisibility() == View.VISIBLE) {
                            // make it invisible
                            betAndRollButton.setEnabled(false);
                            betAndRollButton.setVisibility(View.GONE);
                        }
                    }

                } catch(Exception ex) {
                    // error occured
                    isValid[1] = false;

                    errorMessage.setText("Invalid CHIP amount or Guess Number not in range [1-6]");
                    if(errorMessage.getVisibility() != View.VISIBLE) {
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                    if(betAndRollButton.isEnabled() || betAndRollButton.getVisibility() == View.VISIBLE) {
                        // make it invisible
                        betAndRollButton.setEnabled(false);
                        betAndRollButton.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        betAndRollButton.setOnClickListener(v -> {
            if(isValid[0] && isValid[1]) {
                BigInteger myBetAmount = arrayNum[0];
                BigInteger myGuessNum = arrayNum[1];
                betAndRollButton.setEnabled(false);
                chipAmountText.setEnabled(false);
                guessNumber.setEnabled(false);
                BigInteger updatedBalance = chipBalance.subtract(myBetAmount);
                balanceView.setText("\uD83E\uDE99 Balance : " + updatedBalance.toString() + " CHIP");
                // roll the dice after this
                rollDice(myBetAmount, myGuessNum);
            }
        });
    }

    private void rollDice(BigInteger betAmount, BigInteger choice) {
        isRolling = true;

        // Start rolling animation in a loop
        handler.post(rollingRunnable);

        // Background thread to determine final face after 5 seconds
        new Thread(() -> {
            try {
                Thread.sleep(3000); // Wait 5 seconds
                // make an api call here
                String[] result = myWeb3Client.rollADice(betAmount, choice);
                // if there is an error
                if (result[2].equals(MyWeb3Client.ERROR_OCCURED)) {
                    // error has occured so we redirect to the error activity
                    handler.post(() -> {
                        Intent intent = new Intent(PlayMysticDiceGame.this, FortuneWheelGameResultActivity.class);
                        intent.putExtra("txn_hash", result[3]);
                        intent.putExtra("error", result[4]);
                        // redirect
                        startActivity(intent);                    // also redirect to an intent after waiting for 2 seconds.
                    });

                } else {
                    int diceRollResult = Integer.parseInt(result[1]);
                    keyGenerator.saveMysticDiceResult(diceRollResult);
                    System.out.println("Dice Roll result: " + diceRollResult);
                    // Gradually slow down and stop after 2-3 more rolls
                    handler.post(() -> stopRolling(result));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }

    private Runnable rollingRunnable = new Runnable() {
        private int interval = 100; // Initial speed (milliseconds)

        @Override
        public void run() {
            if (isRolling) {
                int randomFace = random.nextInt(6);
                diceImage.setImageResource(diceFaces[randomFace]);

                // Realistic rolling effect using rotation + scaling
                AnimationSet rollAnimation = new AnimationSet(true);

                RotateAnimation rotate = new RotateAnimation(0, random.nextInt(360) + 180,
                        Animation.RELATIVE_TO_SELF, 0.45f,
                        Animation.RELATIVE_TO_SELF, 0.45f);
                rotate.setDuration(interval);

                ScaleAnimation scale = new ScaleAnimation(
                        1f, 1.2f, 1f, 1.2f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                scale.setDuration(interval / 2);
                scale.setRepeatMode(Animation.REVERSE);
                scale.setRepeatCount(1);

                rollAnimation.addAnimation(rotate);
                rollAnimation.addAnimation(scale);

                diceImage.startAnimation(rollAnimation);

                // Continue rolling
                handler.postDelayed(this, interval);
            }
        }
    };

    private void stopRolling(String[] result) {
        new Thread(() -> {
            for (int i = 0; i < 5; i++) { // Gradually slow down
                try {
                    Thread.sleep(300 + (i * 200)); // Increase delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int randomFace = random.nextInt(6);
                handler.post(() -> {
                    diceImage.setImageResource(diceFaces[randomFace]);

                    // Apply a smaller rolling effect while slowing down
                    RotateAnimation slowRotate = new RotateAnimation(0, 90,
                            Animation.RELATIVE_TO_SELF, 0.5f,
                            Animation.RELATIVE_TO_SELF, 0.5f);
                    slowRotate.setDuration(300);
                    diceImage.startAnimation(slowRotate);
                });
            }

            // Stop on final face
            handler.post(() -> {
                int finalFace = Integer.parseInt(result[1])-1;
                diceImage.setImageResource(diceFaces[finalFace]);
                isRolling = false;
            });
        }).start();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(PlayMysticDiceGame.this, FortuneWheelGameResultActivity.class);
            intent.putExtra("txn_hash", result[3]);
            intent.putExtra("has_won", Integer.parseInt(result[0]) == Integer.parseInt(result[1]));
            intent.putExtra("win_number", Integer.parseInt(result[1]));
            // redirect
            startActivity(intent);                    // also redirect to an intent after waiting for 2 seconds.

        }, 6000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // refresh chip balance
        // set all disabled to enabled
        // reset variables
        // set last state
        this.isRolling = false;
        int mysticDiceResult = keyGenerator.getLastMysticDiceResult();
        diceImage.setImageResource(diceFaces[mysticDiceResult-1]);
        betAndRollButton.setVisibility(View.GONE);
        chipAmountText.setText("");
        guessNumber.setText("");
        errorMessage.setVisibility(View.INVISIBLE);

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
                    guessNumber.setEnabled(false);
                    chipAmountText.setVisibility(View.GONE);
                    guessNumber.setVisibility(View.GONE);

                } else {
                    textView2.setText("Note: Please do not quit this screen while spinning");
                    textView2.setVisibility(View.VISIBLE);
                    chipAmountText.setVisibility(View.VISIBLE);
                    guessNumber.setVisibility(View.VISIBLE);
                    chipAmountText.setEnabled(true);
                    guessNumber.setEnabled(true);
                }
            });

        }).start();
    }

}

