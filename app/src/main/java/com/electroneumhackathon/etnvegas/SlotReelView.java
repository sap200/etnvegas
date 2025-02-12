package com.electroneumhackathon.etnvegas;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;

import java.math.BigInteger;
import java.util.Random;

import cryptooperations.MyWeb3Client;

public class SlotReelView extends View {

    private Paint paint;
    public int currentNumber;
    private int nextNumber;
    private float offsetY;
    private final int[] slotNumbers = {1, 2, 3, 4, 5}; // Slot numbers
    private int remainingSpins;
    private final Random random = new Random();
    private final Handler handler = new Handler();
    private static int[] finalNumber = new int[3];
    private int slotId;
    public boolean isSpinning = false;
    private OnSpinningCompleteListener spinningCompleteListener; // Listener
    public static boolean apiReturnedError = false;
    public static String txnHash;



    public SlotReelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(80f);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);

        // Set initial numbers
        currentNumber = slotNumbers[random.nextInt(slotNumbers.length)];
        nextNumber = slotNumbers[random.nextInt(slotNumbers.length)];
    }

    public void initializeSlotIdAndFinalNumber(int slotId, int currentNumber) {
        this.slotId = slotId;
        this.currentNumber = currentNumber;
        finalNumber[slotId] = -1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        // Draw current number
        canvas.drawText(String.valueOf(currentNumber), width / 2f, height / 2f + offsetY, paint);

        // Draw next number
        canvas.drawText(String.valueOf(nextNumber), width / 2f, height / 2f + offsetY + height, paint);
    }

    public static void setFinalNumber(MyWeb3Client myWeb3Client, BigInteger betAmount, Context context) {
        String[] result = new String[7];
        new Thread(() -> {
            try {
                // Simulate some background work
                String[] dummyResult = myWeb3Client.playSlots(betAmount);
                result[0] = dummyResult[0];
                result[1] = dummyResult[1];
                result[2] = dummyResult[2];
                result[3] = dummyResult[3];
                result[4] = dummyResult[4];
                result[5] = dummyResult[5];
                result[6] = dummyResult[6];

                if(result[4] != null && result[4].equals(MyWeb3Client.ERROR_OCCURED)) {
                    // success
                    apiReturnedError = true;

                } else {

                    finalNumber[0] = Integer.parseInt(result[0]);
                    finalNumber[1] = Integer.parseInt(result[1]);
                    finalNumber[2] = Integer.parseInt(result[2]);
                    txnHash = result[5];
                    apiReturnedError = false;
                }


            } catch (Exception e) {
                e.printStackTrace();
                apiReturnedError = true;
            }

            System.out.println("API RETURNED ERROR: " + apiReturnedError);

            // Post the action back to the main thread using a Handler
            new Handler(Looper.getMainLooper()).post(() -> {
                if (apiReturnedError) {
                    apiReturnedError = false;
                    // Launch ErrorActivity
                    Intent intent = new Intent(context, SlotGameResultActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("error", result[6]);
                    intent.putExtra("txn_hash", result[5]);
                    context.startActivity(intent);
                    ((SlotGameActivity)context).finish();
                }
            });

        }).start();
    }

    public void startSpinning(int spins) {
        remainingSpins = spins;
        isSpinning = true;
        spinOnce();
    }

    private void spinOnce() {

        nextNumber = (finalNumber[slotId] != -1 && remainingSpins == 1) ? finalNumber[slotId] : slotNumbers[random.nextInt(slotNumbers.length)];
        ValueAnimator animator = ValueAnimator.ofFloat(0, getHeight());
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(20); // Animation duration for one spin

        animator.addUpdateListener(animation -> {
            offsetY = (float) animation.getAnimatedValue()/4;
            invalidate();
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                // Update current number and reset offset
                currentNumber = nextNumber;
                offsetY = 0;

                if (finalNumber[slotId] != -1) {
                    remainingSpins--;
                }
                if (remainingSpins > 0) {
                    handler.postDelayed(SlotReelView.this::spinOnce, 100); // Delay between spins
                } else {
                    finalNumber[slotId] = -1;
                    isSpinning = false;
                    if (spinningCompleteListener != null) {
                        spinningCompleteListener.onSpinningComplete();
                    }
                }
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animator) {

            }
        });

        animator.start();
    }
    public void setOnSpinningCompleteListener(OnSpinningCompleteListener listener) {
        this.spinningCompleteListener = listener;
    }
    // Listener interface
    public interface OnSpinningCompleteListener {
        void onSpinningComplete();
    }
}
