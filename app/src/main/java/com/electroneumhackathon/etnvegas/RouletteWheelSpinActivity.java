package com.electroneumhackathon.etnvegas;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cryptooperations.KeyGenerator;
import cryptooperations.MyWeb3Client;
import cryptooperations.RouletteMapper;

public class RouletteWheelSpinActivity extends AppCompatActivity {
    private ImageView wheel;
    private ImageView ball;
    private int numSections = 37; // Assuming 12 sections on the wheel
    private float wheelRotationAngle = 360f; // Full rotation angle
    private FrameLayout layout;
    // Declare wheelRotation and ballJumping as global variables
    private ObjectAnimator wheelRotation;
    private ValueAnimator ballJumping;

    private Map<Integer, Float> inToOutMap = new HashMap<>();
    private Map<Integer, Float> finalAngleMap = new HashMap<>();
    private KeyGenerator keyGenerator;
    private MyWeb3Client myWeb3Client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_roulette_wheel_spin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        wheel = findViewById(R.id.rouletteWheel);
        ball = findViewById(R.id.ball);
        wheel.setRotation(0);
        layout = findViewById(R.id.my_frame);
        ball.setX(ball.getX() + 50); // To center the ball on its position
        ball.setY(ball.getY() - 50); // To center the ball on its position
        keyGenerator = new KeyGenerator(this);
        myWeb3Client = new MyWeb3Client(keyGenerator);

        Map<String, BigInteger> mp = (Map) getIntent().getSerializableExtra("bet");
        System.out.println("My map: " + mp);
        RouletteMapper rouletteMapper = new RouletteMapper(mp);
        BigInteger[] arr = rouletteMapper.mapToSolidityFormat();
        System.out.println("BigInteger array: " + Arrays.asList(arr));
       rouletteMapper.printIdx();

        makeMap();


        startWheelAndBallAnimation(arr);

    }

    private void startWheelAndBallAnimation(BigInteger[] arr) {
        startContinuousWheelRotation();
        startContinuousBallBouncing();
        startRandomNumberGeneration(arr);
    }

    // Start Wheel Rotation Continuously
    private void startContinuousWheelRotation() {
        wheelRotation = ObjectAnimator.ofFloat(wheel, "rotation", 0f, 360f);
        wheelRotation.setDuration(1000);
        wheelRotation.setRepeatCount(ValueAnimator.INFINITE);
        wheelRotation.setInterpolator(new LinearInterpolator());
        wheelRotation.start();
    }

    // Start Ball Bouncing Continuously
    private void startContinuousBallBouncing() {
        ballJumping = new ValueAnimator();
        ballJumping.setFloatValues(0, 1);
        ballJumping.setDuration(500);
        ballJumping.setRepeatCount(ValueAnimator.INFINITE);
        ballJumping.setInterpolator(new LinearInterpolator());

        ballJumping.addUpdateListener(animation -> {
            float progress = (float) animation.getAnimatedValue();
            float ballX = calculateBallX(progress);
            float ballY = calculateBallY(progress);
            ball.setX(ballX);
            ball.setY(ballY);
        });

        ballJumping.start();
    }

    // Random Number Generation in a Background Thread
    private void startRandomNumberGeneration(BigInteger[] arr) {
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Wait 5 seconds before generating
                String[] result = myWeb3Client.playRoulette(arr);
                System.out.println("RESULT_RECEIVED: " + Arrays.asList(result));
                if(result[6] != null && result[6].equals(MyWeb3Client.ERROR_OCCURED)) {
                    Intent intent = new Intent(RouletteWheelSpinActivity.this, PlayRouletteResultActivity.class);
                    intent.putExtra("txn_hash", result[7]);
                    intent.putExtra("error", result[6]);
                    startActivity(intent);
                    finish();

                } else {
                    int randomNumber = Integer.parseInt(result[0].trim());
                    System.out.println("Random Number: " + randomNumber);
                    runOnUiThread(() -> {
                        stopBallAndAlignWheel(randomNumber, result);
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }).start();
    }

    // Stop Ball First, Then Slow Down the Wheel
    private void stopBallAndAlignWheel(int randomNumber, String[] result) {
        ballJumping.cancel();

        ValueAnimator ballSlowing = ValueAnimator.ofFloat(1f, 0f);
        ballSlowing.setDuration(1500);
        ballSlowing.setInterpolator(new DecelerateInterpolator());

        ballSlowing.addUpdateListener(animation -> {
            float progress = (float) animation.getAnimatedValue();
            float ballX = calculateBallX(progress);
            float ballY = calculateBallY(progress);
            ball.setX(ballX);
            ball.setY(ballY);
        });

        ballSlowing.start();

        ballSlowing.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                stopWheelAndAlign(randomNumber, result);
            }
        });
    }

    // Slow Down the Wheel and Align to Ball
    private void stopWheelAndAlign(int randomNumber, String[] result) {
        wheelRotation.cancel();

        float sectionAngle = 360f / 37;
        float targetAngle = inToOutMap.get(randomNumber) * sectionAngle;
        float currentAngle = wheel.getRotation() % 360;
        float offset = (targetAngle - (180 % sectionAngle));
        if (offset < 0) offset += 360; // Ensure no negative angle
        float finalAngle = currentAngle + offset - (currentAngle - 180);
        System.out.println("Random Number: " + randomNumber + ", targetAngle: " + targetAngle + ", currentAngle: " + currentAngle + ", offset: " + offset + ", finalAngle: " + finalAngle);

        ObjectAnimator wheelFinalRotation = ObjectAnimator.ofFloat(wheel, "rotation", currentAngle, finalAngle);
        wheelFinalRotation.setDuration(4000);
        wheelFinalRotation.setInterpolator(new DecelerateInterpolator());
        wheelFinalRotation.start();

        // TODO:
        // do handler .postdelayed after done here to redirect to different intent using the result.
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(RouletteWheelSpinActivity.this, PlayRouletteResultActivity.class);
            intent.putExtra("my_result", (Serializable)(result));
            intent.putExtra("txn_hash", result[7]);
            startActivity(intent);
            finish();
        }, 7000);
    }

    // Utility Functions for Ball Movement Calculation
    private float calculateBallX(float progress) {
        return wheel.getX() + wheel.getWidth() / 2 + 50 + (float) Math.cos(progress * 2 * Math.PI) * 200;
    }

    private float calculateBallY(float progress) {
        return wheel.getY() + wheel.getHeight() / 2 + (float) Math.sin(progress * 2 * Math.PI) * 200;
    }

    private void makeMap() {
        inToOutMap.put(22, 0.2f);
        inToOutMap.put(9, 1.3f);
        inToOutMap.put(31, 2.3f);
        inToOutMap.put(14, 3.3f);
        inToOutMap.put(20, 4.3f);
        inToOutMap.put(1, 5.3f);
        inToOutMap.put(33, 6.25f);
        inToOutMap.put(16, 7.25f);
        inToOutMap.put(24, 8.2f);
        inToOutMap.put(5, 9.2f);
        inToOutMap.put(10, 10.2f);
        inToOutMap.put(23, 11.2f);
        inToOutMap.put(8, 12.2f);
        inToOutMap.put(30, 13.2f);
        inToOutMap.put(11, 14.15f);
        inToOutMap.put(36, 15.15f);
        inToOutMap.put(13, 16.15f);
        inToOutMap.put(27, 17.1f);
        inToOutMap.put(6, 18.1f);
        inToOutMap.put(34, 19.1f);
        inToOutMap.put(17, 20.15f);
        inToOutMap.put(25, 21.2f);
        inToOutMap.put(2, 22.2f);
        inToOutMap.put(21, 23.2f);
        inToOutMap.put(4, 24.2f);
        inToOutMap.put(19, 25.2f);
        inToOutMap.put(15, 26.2f);
        inToOutMap.put(32, 27.23f);
        inToOutMap.put(0, 28.3f);
        inToOutMap.put(26, 29.3f);
        inToOutMap.put(3, 30.3f);
        inToOutMap.put(35, 31.3f);
        inToOutMap.put(12, 32.3f);
        inToOutMap.put(28, 33.3f);
        inToOutMap.put(7, 34.3f);
        inToOutMap.put(29, 35.3f);
        inToOutMap.put(18, 36.3f);

        finalAngleMap.put(0, 450.48648f);
        finalAngleMap.put(1, 226.7027f);
        finalAngleMap.put(2, 391.13513f);




    }
}