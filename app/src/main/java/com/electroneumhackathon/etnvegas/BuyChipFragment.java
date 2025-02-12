package com.electroneumhackathon.etnvegas;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.electroneumhackathon.etnvegas.R;

import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import cryptooperations.KeyGenerator;
import cryptooperations.MyWeb3Client;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuyChipFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyChipFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    private TextView chipBalanceTextView;
    private KeyGenerator keyGenerator;
    private MyWeb3Client myWeb3Client;
    private CardView[] cardViews;
    private BigInteger etherBalanceAvailable = new BigInteger("0");
    private BigInteger pricePerToken =  Convert.toWei("0.002", Convert.Unit.ETHER).toBigInteger();
    private ScrollView scrollView;
    private float initialY = 0; // Used for swipe detection
    private boolean isSwipe = false;
    private LottieAnimationView lottieAnimationView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BuyChipFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuyChipFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuyChipFragment newInstance(String param1, String param2) {
        BuyChipFragment fragment = new BuyChipFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_buy_chip, container, false);
        chipBalanceTextView = view.findViewById(R.id.chip_balance);
        cardViews = new CardView[8];
        initializeCards();
        setEnableAllCards(false);
        lottieAnimationView = view.findViewById(R.id.fetch_lottie_animation_2);
        keyGenerator = new KeyGenerator(requireContext());
        myWeb3Client = new MyWeb3Client(keyGenerator);
        new Thread(() -> {
            String chipBalance = myWeb3Client.getChipBalance();
            BigInteger x1 = myWeb3Client.fetchEtherBalanceInBigInteger();
            if(x1 != null) {
                etherBalanceAvailable = x1;
            }
            BigInteger x2 = myWeb3Client.getBuyPricePerToken();
            if(x2 != null) {
                pricePerToken = x2;
            }
            if (isAdded() && getActivity() != null) {
                requireActivity().runOnUiThread(() -> {
                    lottieAnimationView.cancelAnimation();
                    lottieAnimationView.setVisibility(View.GONE);
                    chipBalanceTextView.setText("Available : " + chipBalance + " CHIP");
                    // enable cards
                    setEnableAllCards(true);

                });
            }
        }).start();

        boolean[] isValid = new boolean[1];

        // attach to all card view a toast and on click of ok make toast else dont do anything.
        for(int i = 0; i < 7; i++) {

            cardViews[i].setOnClickListener(v -> {
                // Create an AlertDialog builder
                // Inflate custom alert dialog layout
                LayoutInflater alertInflater = getLayoutInflater();
                View dialogView = alertInflater.inflate(R.layout.custom_alert_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setView(dialogView);

                // Set the rounded background for the dialog's root layout
                Drawable background = getResources().getDrawable(R.drawable.alert_background_round);
                dialogView.setBackground(background);

                // Customize title and message (optional)
                TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
                TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);

                // Initialize buttons and set click listeners
                Button btnOk = dialogView.findViewById(R.id.btnOk);
                Button btnCancel = dialogView.findViewById(R.id.btnCancel);

                AlertDialog dialog = builder.create();
                // Prevent default buttons from showing
                builder.setCancelable(false); // Disable dismiss on outside click

                // Prevent dismiss when back button is pressed
                dialog.setCanceledOnTouchOutside(false);


                // Set the title and message
                Pair<BigInteger, BigDecimal> pair = (Pair) v.getTag();
                BigDecimal weiAmount = Convert.toWei(pair.second.toString(), Convert.Unit.ETHER);

                if(etherBalanceAvailable.compareTo(weiAmount.toBigInteger()) > 0) {
                    dialogTitle.setText("Buy " + pair.first.toString() + " CHIP");
                    dialogMessage.setText("Click Buy to buy " + pair.first.toString() + " CHIP at " + pair.second.toString() + " ETH");
                    btnOk.setVisibility(View.VISIBLE);
                    btnOk.setEnabled(true);
                    isValid[0] = true;
                } else {
                    dialogTitle.setText("Buy " + pair.first.toString() + " CHIP");
                    dialogMessage.setText("Insufficient balance ! Please Topup your wallet with ether.");
                    dialogMessage.setTextColor(Color.RED);
                    isValid[0] = false;
                }


                btnOk.setOnClickListener(view -> {
                    // Handle OK button click
                    if(isValid[0]) {
                        Intent intent = new Intent(requireActivity(), ChipBuyResultActivity.class);
                        intent.putExtra("chipAmount", weiAmount.toBigInteger().toString());  // Passing a string
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(view -> {
                    // Handle Cancel button click
                    dialog.dismiss();
                });



                // Show the dialog
                dialog.show();
            });
        }

        BigInteger zero = new BigInteger("0");
        cardViews[7].setOnClickListener(v -> {
            // Create an AlertDialog builder
            // Inflate custom alert dialog layout
            LayoutInflater alertInflater = getLayoutInflater();
            View dialogView = alertInflater.inflate(R.layout.custom_alert_dialog_buy_chip, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setView(dialogView);

            // Set the rounded background for the dialog's root layout
            Drawable background = getResources().getDrawable(R.drawable.alert_background_round);
            dialogView.setBackground(background);

            TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);
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
                        BigInteger chipCorrespondingEtherAmount = chipAmountInBigInteger.multiply(pricePerToken);
                        BigDecimal inEth = Convert.fromWei(chipCorrespondingEtherAmount.toString(), Convert.Unit.ETHER);
                        inEth.setScale(5, RoundingMode.HALF_UP);
                        if(chipAmountInBigInteger.equals(zero) || etherBalanceAvailable.compareTo(chipCorrespondingEtherAmount) <= 0) {
                            // set buy button as disabled and text red
                            dialogMessage.setText("Insufficient Balance");
                            dialogMessage.setTextColor(Color.RED);
                            btnOk.setEnabled(false);
                            btnOk.setVisibility(View.INVISIBLE);
                            isValid[0] = false;
                            inWeiAmount[0] = zero;

                        } else {
                            isValid[0] = true;
                            inWeiAmount[0] = chipCorrespondingEtherAmount;
                            dialogMessage.setText("You pay : " + inEth + " ETH");
                            dialogMessage.setTextColor(Color.parseColor("#388E3C"));
                            if(!btnOk.isEnabled() || btnOk.getVisibility() == View.INVISIBLE) {
                                btnOk.setVisibility(view.VISIBLE);
                                btnOk.setEnabled(true);
                            }
                        }

                    } catch(Exception ex) {
                        // dont do anything;
                        // set buy button as disabled and text red
                        isValid[0] = false;
                        inWeiAmount[0] = zero;
                        dialogMessage.setText("Error");
                        dialogMessage.setTextColor(Color.RED);
                        btnOk.setEnabled(false);
                        btnOk.setVisibility(View.INVISIBLE);
                        inWeiAmount[0] = zero;
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });



            btnOk.setOnClickListener(view -> {
                // Handle OK button click
                System.out.println("Isvalid-9" + isValid[0] + "----"+inWeiAmount[0]);
                if(isValid[0] && !inWeiAmount[0].equals(zero)) {
                    Intent intent = new Intent(requireActivity(), ChipBuyResultActivity.class);
                    intent.putExtra("chipAmount", inWeiAmount[0].toString());  // Passing a string
                    startActivity(intent);
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


        return view;
    }

    private void setEnableAllCards(boolean enable) {
        for(CardView cardView : cardViews) {
            cardView.setEnabled(enable);
        }
    }

    private void initializeCards() {

        cardViews[0] = view.findViewById(R.id.card_1);
        BigInteger chipAmount1 = new BigInteger("5");
        BigDecimal ethAmount1 = new BigDecimal("0.01");
        Pair<BigInteger, BigDecimal> value1 = new Pair<>(chipAmount1, ethAmount1);
        cardViews[0].setTag(value1);

        cardViews[1] = view.findViewById(R.id.card_2);
        BigInteger chipAmount2 = new BigInteger("20");
        BigDecimal ethAmount2 = new BigDecimal("0.04");
        Pair<BigInteger, BigDecimal> value2 = new Pair<>(chipAmount2, ethAmount2);
        cardViews[1].setTag(value2);

        cardViews[2] = view.findViewById(R.id.card_3);
        BigInteger chipAmount3 = new BigInteger("50");
        BigDecimal ethAmount3 = new BigDecimal("0.1");
        Pair<BigInteger, BigDecimal> value3 = new Pair<>(chipAmount3, ethAmount3);
        cardViews[2].setTag(value3);

        cardViews[3] = view.findViewById(R.id.card_4);
        BigInteger chipAmount4 = new BigInteger("100");
        BigDecimal ethAmount4 = new BigDecimal("0.2");
        Pair<BigInteger, BigDecimal> value4 = new Pair<>(chipAmount4, ethAmount4);
        cardViews[3].setTag(value4);

        cardViews[4] = view.findViewById(R.id.card_5);
        BigInteger chipAmount5 = new BigInteger("250");
        BigDecimal ethAmount5 = new BigDecimal("0.5");
        Pair<BigInteger, BigDecimal> value5 = new Pair<>(chipAmount5, ethAmount5);
        cardViews[4].setTag(value5);

        cardViews[5] = view.findViewById(R.id.card_6);
        BigInteger chipAmount6 = new BigInteger("500");
        BigDecimal ethAmount6 = new BigDecimal("1");
        Pair<BigInteger, BigDecimal> value6 = new Pair<>(chipAmount6, ethAmount6);
        cardViews[5].setTag(value6);

        cardViews[6] = view.findViewById(R.id.card_7);
        BigInteger chipAmount7 = new BigInteger("1000");
        BigDecimal ethAmount7 = new BigDecimal("2");
        Pair<BigInteger, BigDecimal> value7 = new Pair<>(chipAmount7, ethAmount7);
        cardViews[6].setTag(value7);

        cardViews[7] = view.findViewById(R.id.card_8);

    }

    @Override
    public void onResume() {
        super.onResume();
        lottieAnimationView.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();
        chipBalanceTextView.setText("");

        new Thread(() -> {
            String chipBalance = myWeb3Client.getChipBalance();
            BigInteger x1 = myWeb3Client.fetchEtherBalanceInBigInteger();
            if(x1 != null) {
                etherBalanceAvailable = x1;
            }
            BigInteger x2 = myWeb3Client.getBuyPricePerToken();
            if(x2 != null) {
                pricePerToken = x2;
            }
            if (isAdded() && getActivity() != null) {
                requireActivity().runOnUiThread(() -> {
                    chipBalanceTextView.setText("Available : " + chipBalance + " CHIP");
                    lottieAnimationView.setVisibility(View.GONE);
                    lottieAnimationView.cancelAnimation();
                    setEnableAllCards(true);
                });
            }
        }).start();
    }
}