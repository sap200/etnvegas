package com.electroneumhackathon.etnvegas;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cryptooperations.KeyGenerator;
import cryptooperations.MyWeb3Client;

public class PlayFortuneWheelGame extends AppCompatActivity {

    private int x;
    private boolean isStopping = false;
    private ObjectAnimator animator;
    ImageView ivWheel;
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
    private Button betAndSpinButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_fortune_wheel_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        keyGenerator = new KeyGenerator(this);
        myWeb3Client = new MyWeb3Client(keyGenerator);
        errorMessage = findViewById(R.id.display_error_message);
        balanceView = findViewById(R.id.fortune_balance_view);
        lottieAnimationView = findViewById(R.id.fetch_balance_animation_2);
        chipAmountText = findViewById(R.id.bet_chip_on_fortune_wheel);
        guessNumber = findViewById(R.id.choice_number);
        textView2 = findViewById(R.id.warning_message);
        goToWalletButton = findViewById(R.id.go_to_wallet_from_wheel);
        betAndSpinButton = findViewById(R.id.bet_and_spin_button);
        ivWheel = findViewById(R.id.ivWheel);

        float myFinalAngle = getAngleForX(keyGenerator.getLastFortuneWheelResult());
        float myRotation = 720 + myFinalAngle;
        ivWheel.setRotation(myRotation);


        goToWalletButton.setOnClickListener(e -> {
            Intent intent = new Intent(PlayFortuneWheelGame.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

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
                    Intent intent = new Intent(PlayFortuneWheelGame.this, GenericErrorActivity.class);
                    startActivity(intent);
                    finish();
                });

            }

        }).start();

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

                        errorMessage.setText("Invalid CHIP amount or Guess Number not in range [1-10]");
                        if(errorMessage.getVisibility() != View.VISIBLE) {
                            errorMessage.setVisibility(View.VISIBLE);
                        }
                        if(betAndSpinButton.isEnabled() || betAndSpinButton.getVisibility() == View.VISIBLE) {
                            // make it invisible
                            betAndSpinButton.setEnabled(false);
                            betAndSpinButton.setVisibility(View.GONE);
                        }

                    } else {
                        isValid[0] = true;
                        // success show spin button
                        arrayNum[0] = chipBetAmountBigInt;

                        if(isValid[0] && isValid[1]) {
                            if(errorMessage.getVisibility() == View.VISIBLE) {
                                errorMessage.setVisibility(View.GONE);
                            }
                            betAndSpinButton.setVisibility(View.VISIBLE);
                            betAndSpinButton.setEnabled(true);
                        }
                    }

                } catch(Exception ex) {
                    // error occured
                    isValid[0] = false;

                    errorMessage.setText("Invalid CHIP amount or Guess Number not in range [1-10]");
                    if(errorMessage.getVisibility() != View.VISIBLE) {
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                    if(betAndSpinButton.isEnabled() || betAndSpinButton.getVisibility() == View.VISIBLE) {
                        // make it invisible
                        betAndSpinButton.setEnabled(false);
                        betAndSpinButton.setVisibility(View.GONE);
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

                    if(guessNumberBig.compareTo(new BigInteger("1")) >= 0 && guessNumberBig.compareTo(new BigInteger("10"))  <= 0 ) {
                        isValid[1] = true;
                        arrayNum[1] = guessNumberBig;
                        if(isValid[0] && isValid[1]) {
                            if(errorMessage.getVisibility() == View.VISIBLE) {
                                errorMessage.setVisibility(View.GONE);
                            }
                            betAndSpinButton.setVisibility(View.VISIBLE);
                            betAndSpinButton.setEnabled(true);
                        }
                    } else {
                        // error occured
                        isValid[1] = false;

                        errorMessage.setText("Invalid CHIP amount or Guess Number not in range [1-10]");
                        if(errorMessage.getVisibility() != View.VISIBLE) {
                            errorMessage.setVisibility(View.VISIBLE);
                        }
                        if(betAndSpinButton.isEnabled() || betAndSpinButton.getVisibility() == View.VISIBLE) {
                            // make it invisible
                            betAndSpinButton.setEnabled(false);
                            betAndSpinButton.setVisibility(View.GONE);
                        }
                    }

                } catch(Exception ex) {
                    // error occured
                    isValid[1] = false;

                    errorMessage.setText("Invalid CHIP amount or Guess Number not in range [1-10]");
                    if(errorMessage.getVisibility() != View.VISIBLE) {
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                    if(betAndSpinButton.isEnabled() || betAndSpinButton.getVisibility() == View.VISIBLE) {
                        // make it invisible
                        betAndSpinButton.setEnabled(false);
                        betAndSpinButton.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        betAndSpinButton.setOnClickListener(e -> {
            if(isValid[0] && isValid[1]) {
                BigInteger myBetAmount = arrayNum[0];
                BigInteger myGuessNum = arrayNum[1];
                betAndSpinButton.setEnabled(false);
                chipAmountText.setEnabled(false);
                guessNumber.setEnabled(false);
                BigInteger updatedBalance = chipBalance.subtract(myBetAmount);
                balanceView.setText("\uD83E\uDE99 Balance : " + updatedBalance.toString() + " CHIP");
                animator = ObjectAnimator.ofFloat(ivWheel, "rotation", 0f, 360f);
                animator.setDuration(1000); // 1 second per full rotation
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setInterpolator(new LinearInterpolator()); // Constant speed
                animator.start();
                spinTheContractWheel(myBetAmount, myGuessNum);
            }
        });
    }

    private void stopAtX(String[] result) {
        if (isStopping) return; // Prevent multiple stops if already stopping

        isStopping = true;
        if(result[2].equals(MyWeb3Client.ERROR_OCCURED)) {
            // directly redirect to another activity showing error
            Intent intent = new Intent(PlayFortuneWheelGame.this, FortuneWheelGameResultActivity.class);
            intent.putExtra("error", result[4]);
            intent.putExtra("txn_hash", result[3]);
            // redirect
            startActivity(intent);
        } else {
            // add the last result to the shared preferences
            // and load it.

            // also on back resume, and do necessary changes.
            int wheelNumber = Integer.parseInt(result[1]);
            System.out.println("Wheel Number In Game is : " + wheelNumber);
            // save this number.
            keyGenerator.saveFortuneWheelResult(wheelNumber);

            animator.cancel(); // Stop infinite rotation

            // Calculate target angle based on `x`
            float finalAngle = getAngleForX(wheelNumber);
            System.out.println("Final Angle: " + finalAngle);
            // Apply a smooth stop with a decelerating effect
            ObjectAnimator stopAnimator = ObjectAnimator.ofFloat(ivWheel, "rotation", ivWheel.getRotation(), 720 + finalAngle);
            stopAnimator.setDuration(3000); // 3 seconds to slow down
            stopAnimator.setInterpolator(new DecelerateInterpolator()); // Smooth slowdown

            stopAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(@NonNull Animator animator) {

                }

                @Override
                public void onAnimationEnd(@NonNull Animator animator) {
                    isStopping = false;
                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(PlayFortuneWheelGame.this, FortuneWheelGameResultActivity.class);
                        intent.putExtra("txn_hash", result[3]);
                        intent.putExtra("has_won", Integer.parseInt(result[0]) == Integer.parseInt(result[1]));
                        intent.putExtra("win_number", Integer.parseInt(result[1]));
                        // redirect
                        startActivity(intent);                    // also redirect to an intent after waiting for 2 seconds.
                    }, 3000);
                }

                @Override
                public void onAnimationCancel(@NonNull Animator animator) {

                }

                @Override
                public void onAnimationRepeat(@NonNull Animator animator) {

                }
            });
            stopAnimator.start();
        }
    }

    // Function to map number 1-10 to angle
    private float getAngleForX(int num) {
        return (num - 1) * (36f) + 18f; // Each number is 36° apart
    }


    // Make smart contract transaction here
    private void spinTheContractWheel(BigInteger betAmount, BigInteger choice) {
        new Thread(() -> {
            try {
                // Sleep for 5 seconds
                String[] result = myWeb3Client.spinTheWheel(betAmount, choice);
                runOnUiThread(() -> {
                    stopAtX(result);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // refresh chip balance
        // set all disabled to enabled

        this.isStopping = false;
        float myFinalAngle = getAngleForX(keyGenerator.getLastFortuneWheelResult());
        float myRotation = 720 + myFinalAngle;
        ivWheel.setRotation(myRotation);
        betAndSpinButton.setVisibility(View.GONE);
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