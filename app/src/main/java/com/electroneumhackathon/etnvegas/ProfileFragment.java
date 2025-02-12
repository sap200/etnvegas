package com.electroneumhackathon.etnvegas;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.electroneumhackathon.etnvegas.R;


import cryptooperations.KeyGenerator;
import cryptooperations.QRCodeGenerator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private TextView addressTextView;
    private  KeyGenerator keyGenerator;
    private ImageView profileQR;
    private Button instructionButton;
    private Button privateKeyButton;
    private Button logoutButton;
    private LinearLayout addressLinearLayout;
    private Button arbitrumBridgeButton;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        addressTextView = view.findViewById(R.id.address_text_view);
        keyGenerator = new KeyGenerator(requireActivity());
        addressTextView.setText(keyGenerator.getAddress());

        profileQR = view.findViewById(R.id.profile_qr_image);
        new Thread(() -> {
            Bitmap bm = QRCodeGenerator.generateQRcode(keyGenerator.getAddress());
            if (isAdded() && getActivity() != null) {
                requireActivity().runOnUiThread(() -> {
                    profileQR.setImageBitmap(bm);
                });
            }
        }).start();

        instructionButton = view.findViewById(R.id.btn_instructions);
        privateKeyButton = view.findViewById(R.id.btn_private_key);
        logoutButton = view.findViewById(R.id.btn_logout);
        addressLinearLayout = view.findViewById(R.id.address_layout_linear);
        arbitrumBridgeButton = view.findViewById(R.id.btn_arbitrum_bridge);

        // 1st lets do logout implementation
        logoutButton.setOnClickListener(e -> {
            // show alert box
            // Create an AlertDialog builder
            // Inflate custom alert dialog layout
            if (isAdded() && getActivity() != null && getContext() != null) {

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
                btnOk.setText("Logout");
                Button btnCancel = dialogView.findViewById(R.id.btnCancel);

                AlertDialog dialog = builder.create();
                // Prevent default buttons from showing
                builder.setCancelable(false); // Disable dismiss on outside click

                // Prevent dismiss when back button is pressed
                dialog.setCanceledOnTouchOutside(false);


                dialogTitle.setText("Logout");
                dialogMessage.setText("Backup your private key before logging out.");
                btnOk.setVisibility(View.VISIBLE);
                btnOk.setEnabled(true);


                btnOk.setOnClickListener(view -> {
                    // Handle OK button click
                    // delete wallet...
                    keyGenerator.deleteWallet();
                    if (isAdded() && getActivity() != null) {
                        // now redirect to main activity the initial activity ok loginsignupactivity.
                        Intent intent = new Intent(requireActivity(), LoginSignupButtonActivity.class);
                        // del all activities in btwn
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        requireActivity().finish(); // Optional: Closes the current activity
                    }
                    dialog.dismiss();
                });

                btnCancel.setOnClickListener(view -> {
                    // Handle Cancel button click
                    dialog.dismiss();
                });

                // Show the dialog
                dialog.show();
            }
        });

        privateKeyButton.setOnClickListener(e -> {
            // show alert box
            // Create an AlertDialog builder
            // Inflate custom alert dialog layout
            if (isAdded() && getActivity() != null && getContext() != null) {

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


                Drawable editTextBackground = getResources().getDrawable(R.drawable.edit_text_box);
                dialogTitle.setText("Your Private key");
                dialogMessage.setText(keyGenerator.getPrivateKey());
                dialogMessage.setBackground(editTextBackground);
                dialogMessage.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                dialogMessage.setPadding(15, 15, 15, 15);
                dialogMessage.setTextColor(Color.RED);
                btnOk.setVisibility(View.VISIBLE);
                btnOk.setEnabled(true);
                btnOk.setText("Copy");


                btnOk.setOnClickListener(view -> {
                    // Handle OK button click
                    // Get the ClipboardManager service
                    if (isAdded() && getActivity() != null) {

                        ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                        // Create a ClipData with the private key text
                        ClipData clip = ClipData.newPlainText("private_key_copied_from_profile", keyGenerator.getPrivateKey());

                        // Copy the text to the clipboard
                        clipboard.setPrimaryClip(clip);

                        // Show a Toast confirmation
                        Toast.makeText(requireActivity(), "Private key copied to clipboard!", Toast.LENGTH_LONG).show();
                    }
                });

                btnCancel.setOnClickListener(view -> {
                    // Handle Cancel button click
                    dialog.dismiss();
                });

                // Show the dialog
                dialog.show();
            }

        });

        instructionButton.setOnClickListener(e -> {
            // now redirect to main activity the initial activity ok loginsignupactivity.
            if (isAdded() && getActivity() != null) {
                Intent intent = new Intent(requireActivity(), InstructionActivity.class);
                startActivity(intent);
            }
        });

        addressLinearLayout.setOnClickListener(e -> {
            // Get the ClipboardManager service
            if (isAdded() && getActivity() != null) {

                ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                // Create a ClipData with the private key text
                ClipData clip = ClipData.newPlainText("address_copied_from_profile", keyGenerator.getAddress());

                // Copy the text to the clipboard
                clipboard.setPrimaryClip(clip);

                // Show a Toast confirmation
                Toast.makeText(requireActivity(), "Address copied to clipboard!", Toast.LENGTH_LONG).show();
            }
        });

        arbitrumBridgeButton.setOnClickListener(e -> {
            // show alert box
            // Create an AlertDialog builder
            // Inflate custom alert dialog layout
            if (isAdded() && getActivity() != null && getContext() != null) {

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


                Drawable editTextBackground = getResources().getDrawable(R.drawable.edit_text_box);
                dialogTitle.setText("Arbitrum Bridge Link");
                dialogMessage.setText("https://bridge.arbitrum.io/");
                dialogMessage.setBackground(editTextBackground);
                dialogMessage.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                dialogMessage.setPadding(15, 15, 15, 15);
                btnOk.setVisibility(View.VISIBLE);
                btnOk.setEnabled(true);
                btnOk.setText("Copy");


                btnOk.setOnClickListener(view -> {
                    // Handle OK button click
                    // Get the ClipboardManager service
                    if (isAdded() && getActivity() != null) {

                        ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                        // Create a ClipData with the private key text
                        ClipData clip = ClipData.newPlainText("arbitrum_bridge_link_profile", "https://bridge.arbitrum.io/");

                        // Copy the text to the clipboard
                        clipboard.setPrimaryClip(clip);

                        // Show a Toast confirmation
                        Toast.makeText(requireActivity(), "Arbitrum Bridge Link copied to clipboard!", Toast.LENGTH_LONG).show();
                    }
                });

                btnCancel.setOnClickListener(view -> {
                    // Handle Cancel button click
                    dialog.dismiss();
                });

                // Show the dialog
                dialog.show();
            }
        });

        return view;
    }
}