package com.electroneumhackathon.etnvegas;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.electroneumhackathon.etnvegas.R;


import org.web3j.utils.Convert;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;

import cryptooperations.KeyGenerator;
import cryptooperations.MyWeb3Client;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    KeyGenerator keyGenerator;
    MyWeb3Client myWeb3Client;
    TextView ethBalanceTextView;
    TextView chipBalanceTextView;
    LottieAnimationView lottieAnimationView;
    Button sendEthButton;
    Button receiveEthButton;
    Button swapChipButton;
    LinearLayout linearLayout;
    private BigInteger myEtherBalanceBigInt;
    private BigInteger myChipBalance;

    public WalletFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
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
        view =  inflater.inflate(R.layout.fragment_wallet, container, false);

         keyGenerator = new KeyGenerator(requireContext());
         myWeb3Client = new MyWeb3Client(keyGenerator);
         ethBalanceTextView = (TextView)view.findViewById(R.id.wallet_display_eth_balance);
         chipBalanceTextView = (TextView)view.findViewById(R.id.wallet_display_chip_balance);
         lottieAnimationView = view.findViewById(R.id.txn_status_lottie_animation);
         sendEthButton = view.findViewById(R.id.wallet_send_eth_button);
         receiveEthButton = view.findViewById(R.id.wallet_receive_button);
         swapChipButton = view.findViewById(R.id.wallet_swap_button);
         linearLayout = view.findViewById(R.id.wallet_buttons_layout);

        new Thread(() -> {
            myEtherBalanceBigInt = myWeb3Client.fetchEtherBalanceInBigInteger();
            myChipBalance = myWeb3Client.getChipBalanceInBigInteger();
            if(myEtherBalanceBigInt == null) {
                myEtherBalanceBigInt = new BigInteger("0");
            }

            if(myChipBalance == null) {
                myChipBalance = new BigInteger("0");
            }

            String ethBalance = Convert.fromWei(myEtherBalanceBigInt.toString(), Convert.Unit.ETHER).setScale(5, RoundingMode.HALF_UP).toString();
            String chipBalance = myChipBalance.toString();

            if (isAdded() && getActivity() != null) {
                requireActivity().runOnUiThread(() -> {
                    if (myEtherBalanceBigInt != null && myChipBalance != null) {
                        ethBalanceTextView.setText( ethBalance + " ETN");
                        chipBalanceTextView.setText(chipBalance + " CHIP");
                        lottieAnimationView.cancelAnimation();
                        lottieAnimationView.setVisibility(View.GONE);

                        // enable all buttons
                        linearLayout.setVisibility(View.VISIBLE);
                        sendEthButton.setEnabled(true);
                        receiveEthButton.setEnabled(true);
                        swapChipButton.setEnabled(true);


                    } else {
                        Intent intent = new Intent(getActivity(), GenericErrorActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
            }

        }).start();

        sendEthButton.setOnClickListener(v -> {
            String ethBalance = Convert.fromWei(myEtherBalanceBigInt.toString(), Convert.Unit.ETHER).toString();
            keyGenerator.saveBalance(ethBalance);
            Intent intent = new Intent(getActivity(), SendEtherActivity.class);
            startActivity(intent);
        });

        receiveEthButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ReceiveEthActivity.class);
            startActivity(intent);
        });

        swapChipButton.setOnClickListener(e -> {
            String editorText = chipBalanceTextView.getText().toString().trim();
            String[] res = editorText.split(" ");
            keyGenerator.saveChips(res[0].trim());
            Intent intent = new Intent(getActivity(), SwapTokenActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Your resume-related logic here
        // Example: Refresh UI or reload data

        lottieAnimationView.setVisibility(View.VISIBLE);

        new Thread(() -> {
            String etherBalance = myWeb3Client.fetchEtherBalance();
            String chipBalance = myWeb3Client.getChipBalance();
            if (isAdded() && getActivity() != null) {
                requireActivity().runOnUiThread(() -> {
                    if (etherBalance != null && chipBalance != null) {
                        ethBalanceTextView.setText(etherBalance + " ETN");
                        chipBalanceTextView.setText(chipBalance + " CHIP");
                        lottieAnimationView.cancelAnimation();
                        lottieAnimationView.setVisibility(View.GONE);
                        // enable all buttons and layout
                        linearLayout.setVisibility(View.VISIBLE);
                        sendEthButton.setEnabled(true);
                        receiveEthButton.setEnabled(true);
                        swapChipButton.setEnabled(true);

                    } else {
                        Intent intent = new Intent(getActivity(), GenericErrorActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
            }

        }).start();
    }



}