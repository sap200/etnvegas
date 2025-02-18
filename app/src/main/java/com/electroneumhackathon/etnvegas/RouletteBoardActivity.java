package com.electroneumhackathon.etnvegas;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

import org.web3j.utils.Convert;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cryptooperations.KeyGenerator;
import cryptooperations.MyWeb3Client;

public class RouletteBoardActivity extends AppCompatActivity {

    String[] data = {
            "FIRST_SPECIAL", "0",

            "1-18", "1st12", "1", "2", "3",
            "4", "5", "6",
            "EVEN", "7", "8", "9",
            "10", "11", "12",

            "RED", "2nd12", "13", "14", "15",
            "16", "17", "18",
            "BLACK", "19", "20", "21",
             "22", "23", "24",

            "ODD", "3rd12", "25", "26", "27",
             "28", "29", "30",
            "19-36", "31", "32", "33",
            "34", "35", "36",

            "LAST_SPECIAL", "COL34", "COL35", "COL36",
    };
    Set<String> blackSet = new HashSet<>(Arrays.asList("2", "4", "6", "8", "10", "11", "13", "15", "17", "20", "22", "24", "26", "28", "29", "31", "33", "35", "BLACK"));
    Set<String> redSet = new HashSet<>(Arrays.asList("1", "3", "5", "7", "9", "12", "14", "16", "18", "19", "21", "23", "25", "27", "30", "32", "34", "36", "RED"));
    Button finalizeAndBetButton;
    private int DATA_TAG = 191;
    private int VALUE_TAG = 192;
    private List<TextView> listOfTextViews = new LinkedList<>();
    private MyWeb3Client myWeb3Client;
    private KeyGenerator keyGenerator;
    private BigInteger chipBalanceInBigInteger = new BigInteger("0");
    private BigInteger apparentChipBalanceInBigInteger = new BigInteger("0");

    private LottieAnimationView lottieAnimationView;
    private TextView rouletteBalanceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_roulette_board);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        GridLayout gridLayout = findViewById(R.id.my_board_grid); // Your GridLayout in XML
        gridLayout.setVerticalScrollBarEnabled(true);
        finalizeAndBetButton = findViewById(R.id.finalize_bet_button);
        keyGenerator = new KeyGenerator(this);
        myWeb3Client = new MyWeb3Client(keyGenerator);
        lottieAnimationView = findViewById(R.id.fetch_lottie_animation_2);
        rouletteBalanceView = findViewById(R.id.roulette_balance_view);

        new Thread(() -> {
            try {
                chipBalanceInBigInteger = myWeb3Client.getChipBalanceInBigInteger();
                apparentChipBalanceInBigInteger = new BigInteger(chipBalanceInBigInteger.toString());
                System.out.println("CHIP BALANCE: " + chipBalanceInBigInteger);
                runOnUiThread(() -> {
                    lottieAnimationView.cancelAnimation();
                    lottieAnimationView.setVisibility(View.GONE);
                    rouletteBalanceView.setText("\uD83E\uDE99 Balance : " + chipBalanceInBigInteger.toString().trim() + " CHIP");
                    rouletteBalanceView.setVisibility(View.VISIBLE);
                });
            } catch(Exception  ex) {
                Intent intent = new Intent(RouletteBoardActivity.this, GenericErrorActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();



        float widthPercentage = 0.18f;  // 30% of parent width


        Typeface typeface = ResourcesCompat.getFont(this, R.font.lustria);

        gridLayout.post(new Runnable() {
            @Override
            public void run() {
                int parentWidth = gridLayout.getWidth();
                int columnWidth = (int) (parentWidth * widthPercentage);  // Calculate width as 30% of parent width

                for (int i = 0; i < data.length; i++) {
                    TextView textView = new TextView(getApplicationContext());
                    textView.setText(data[i]);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(16);
                    textView.setWidth(40);
                    textView.setHeight(35);
                    textView.setTextColor(Color.WHITE);
                    textView.setBackgroundResource(R.drawable.cell_border);
                    textView.setPadding(2, 2, 2, 2);
                    textView.setTypeface(typeface);
                    Map<Integer, String> tagMap = new HashMap<>();
                    tagMap.put(DATA_TAG, data[i]);
                    textView.setTag(tagMap);
                    if(blackSet.contains(data[i])) {
                        textView.setBackgroundColor(Color.BLACK);
                    } else if (redSet.contains(data[i])) {
                        textView.setBackgroundColor(Color.RED);
                    } else {
                        textView.setBackgroundColor(Color.parseColor("#3F704D"));
                    }


                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = columnWidth;  // Set width as calculated percentage
                    params.height = dpToPx(50);

                    params.setMargins(1, 1, 1, 1);
                    if(data[i].equals("1st12")) {
                        params.rowSpec = GridLayout.spec(1, 4);
                        params.height = dpToPx(50*4 + 2);
                    } else if (data[i].equals("1-18")) {
                        params.rowSpec = GridLayout.spec(0, 2);
                        params.height = dpToPx(50*2);
                    } else if (data[i].equals("EVEN")) {
                        params.rowSpec = GridLayout.spec(2, 2);
                        params.height = dpToPx(50 * 2);
                    } else if (data[i].equals("RED")) {
                        params.rowSpec = GridLayout.spec(4, 2);
                        params.height = dpToPx(50 * 2);
                    } else if (data[i].equals("2nd12")) {
                        params.rowSpec = GridLayout.spec(5, 4);
                        params.height = dpToPx(50 * 4 + 2);
                    } else if(data[i].equals("BLACK")) {
                        params.rowSpec = GridLayout.spec(6, 2);
                        params.height = dpToPx(50*2);
                    }  else if(data[i].equals("3rd12")) {
                        params.rowSpec = GridLayout.spec(9, 4);
                        params.height = dpToPx(50*4 +  2);
                    }  else if (data[i].equals("ODD")) {
                        params.rowSpec = GridLayout.spec(8, 2);
                        params.height = dpToPx(50*2);
                    }  else if(data[i].equals("19-36")) {
                        params.rowSpec = GridLayout.spec(10, 2);
                        params.height = dpToPx(50*2);
                    }  else if (data[i].equals("LAST_SPECIAL")) {
                        params.columnSpec = GridLayout.spec(0, 2);
                        textView.setText("");
                        params.width = columnWidth*2;
                        textView.setBackground(null);
                    } else if (data[i].equals("FIRST_SPECIAL")) {
                        params.columnSpec = GridLayout.spec(0, 2);
                        textView.setText("");
                        params.width = columnWidth*2;
                        textView.setBackground(null);
                    } else if (data[i].equals("0")) {
                        params.columnSpec = GridLayout.spec(2, 3);
                        params.width = columnWidth*3;
                    }

                    // On click listener for text view
                    if(!data[i].equals("FIRST_SPECIAL") && !data[i].equals("LAST_SPECIAL")) {
                        setClickListenerForEachTextView(textView);
                        listOfTextViews.add(textView);
                    }


                    textView.setLayoutParams(params);


                    gridLayout.addView(textView);



                }

            }
        });

        finalizeAndBetButton.setOnClickListener(e -> {
            // navigate to other activity to send ether
            Intent intent = new Intent(RouletteBoardActivity.this, RouletteWheelSpinActivity.class);
            intent.putExtra("bet", (Serializable) getAllBetsFormatted());
            startActivity(intent);
            finish();
        });






    }

    public int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }



    private String transform(String x) {
           StringBuilder transformed = new StringBuilder();
            for (char c : x.toString().toCharArray()) {
                transformed.append(c).append("\n"); // Each letter on a new line
            }
            return transformed.toString();
    }

    private void setClickListenerForEachTextView(TextView textView) {

        textView.setOnClickListener(e -> {
            // Create an AlertDialog builder
            // Inflate custom alert dialog layout
            LayoutInflater alertInflater = getLayoutInflater();
            View dialogView = alertInflater.inflate(R.layout.custom_roulette_layover_for_bet, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);

            // Set the rounded background for the dialog's root layout
            Drawable background = getResources().getDrawable(R.drawable.alert_background_round);
            dialogView.setBackground(background);

            TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
            Map<Integer, String> tMap = (Map)textView.getTag();
            dialogTitle.setText("Enter bet amount on " + tMap.get(DATA_TAG));
            TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);
            if(tMap.get(VALUE_TAG) == null || tMap.get(VALUE_TAG).equals("")) {
                dialogMessage.setText("Current bet : 0 CHIP");
            } else {
                dialogMessage.setText("Current bet : " + tMap.get(VALUE_TAG) + " CHIP");
            }
            // Initialize buttons and set click listeners
            Button btnOk = dialogView.findViewById(R.id.btnOk);
            Button btnCancel = dialogView.findViewById(R.id.btnCancel);

            AlertDialog dialog = builder.create();
            // Prevent default buttons from showing
            builder.setCancelable(false); // Disable dismiss on outside click

            // Prevent dismiss when back button is pressed
            dialog.setCanceledOnTouchOutside(false);


            EditText chipAmountEditText = dialogView.findViewById(R.id.dialogChipAmount);
            BigInteger[] inWeiAmount = new BigInteger[1];
            chipAmountEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // get chip amount entered
                    String chipAmount = charSequence.toString().trim();
                    try {
                        BigInteger chipAmountInBigInteger = new BigInteger(chipAmount);
                        System.out.println("Bet Amount entered : " + chipAmountInBigInteger);
                        // get setting of current text view
                        BigInteger currentValue = new BigInteger("0");
                        if(tMap.get(VALUE_TAG) == null || tMap.get(VALUE_TAG).equals("")) {
                             currentValue = new BigInteger("0");
                        } else {
                            currentValue = new BigInteger(tMap.get(VALUE_TAG).toString().trim());
                        }
                        BigInteger uppervalue = apparentChipBalanceInBigInteger.add(currentValue);
                        System.out.println("UPPER_VALUE: " + uppervalue);
                        if(chipAmountInBigInteger.compareTo(new BigInteger("0")) >= 0 && chipAmountInBigInteger.compareTo(uppervalue) <= 0) {
                            // validation here and enable ok button
                            btnOk.setEnabled(true);
                            btnOk.setVisibility(View.VISIBLE);
                            if(tMap.get(VALUE_TAG) == null || tMap.get(VALUE_TAG).equals("")) {
                                dialogMessage.setText("Current bet : 0 CHIP");
                                dialogMessage.setTextColor(Color.parseColor("#388E3C"));
                            } else {
                                dialogMessage.setText("Current bet : " + tMap.get(VALUE_TAG) + " CHIP");
                                dialogMessage.setTextColor(Color.parseColor("#388E3C"));
                            }
                        } else {
                            dialogMessage.setVisibility(View.VISIBLE);
                            dialogMessage.setText("Invalid chip amount");
                            dialogMessage.setTextColor(Color.RED);
                            btnOk.setEnabled(false);
                            btnOk.setVisibility(View.INVISIBLE);
                        }

                    } catch (Exception ex) {
                        // dont do anything;
                        // set buy button as disabled and text red
                        btnOk.setEnabled(false);
                        btnOk.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            btnOk.setOnClickListener(view -> {

                // Handle OK button click
                System.out.println("Clicked OK !");
                Map<Integer, String> tagMap = (Map) textView.getTag();
                String dataTag = tagMap.get(DATA_TAG);
                String x = chipAmountEditText.getText().toString().trim();
                BigInteger value = new BigInteger(x);
                tagMap.put(VALUE_TAG, value.toString().trim());
                textView.setTag(tagMap);
                String newText = dataTag + "\n₵:" + value;
                SpannableString spannable = new SpannableString(newText);
                spannable.setSpan(new ForegroundColorSpan(Color.YELLOW), dataTag.length(), spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                BigInteger totalBetAmountNow = calculateTotalBetAmount();
                System.out.println("Total bet Amount: " + totalBetAmountNow);
                apparentChipBalanceInBigInteger = chipBalanceInBigInteger.subtract(totalBetAmountNow);
                rouletteBalanceView.setText("\uD83E\uDE99 Balance : " + apparentChipBalanceInBigInteger.toString().trim() + " CHIP");
                if(totalBetAmountNow.compareTo(new BigInteger("0")) > 0 && totalBetAmountNow.compareTo(chipBalanceInBigInteger) <= 0) {
                    finalizeAndBetButton.setVisibility(View.VISIBLE);
                    finalizeAndBetButton.setEnabled(true);
                } else {
                    finalizeAndBetButton.setVisibility(View.INVISIBLE);
                    finalizeAndBetButton.setEnabled(false);
                }

                if(value.equals(new BigInteger("0"))) {
                    textView.setText(dataTag);
                } else {
                    textView.setText(spannable);
                }

                dialog.dismiss();
            });

            btnCancel.setOnClickListener(view -> {
                // Handle Cancel button click
                dialog.dismiss();
            });


            // Show the dialog
            dialog.show();
        });
    }

    private BigInteger calculateTotalBetAmount() {
        // for all text views get the value tag and return
        BigInteger sum = new BigInteger("0");
        for (TextView t : listOfTextViews) {
            Map<Integer, String> tMap = (Map) t.getTag();
            String value= tMap.get(VALUE_TAG);
            if (value != null && !value.equals("")) {
                BigInteger x = new BigInteger(value);
                sum = sum.add(x);
            }
        }

        return sum;
    }

    private Map<String, BigInteger> getAllBetsFormatted() {
        Map<String, BigInteger> mapd = new HashMap<>();
        for(TextView t : listOfTextViews) {
            Map<Integer, String> tMap = (Map) t.getTag();
            String value= tMap.get(VALUE_TAG);
            if (value != null && !value.equals("")) {
                BigInteger x = new BigInteger(value);
                mapd.put(tMap.get(DATA_TAG), x);
            } else {
                mapd.put(tMap.get(DATA_TAG), new BigInteger("0"));
            }
        }

        return mapd;
    }

}