package com.electroneumhackathon.etnvegas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.electroneumhackathon.etnvegas.R;


import java.math.BigInteger;

import cryptooperations.KeyGenerator;
import cryptooperations.MyWeb3Client;

public class SendEtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_ether);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        KeyGenerator keyGenerator = new KeyGenerator(this);
        MyWeb3Client myWeb3Client = new MyWeb3Client(keyGenerator);

        EditText address = (EditText) findViewById(R.id.eth_send_address);
        EditText amount = findViewById(R.id.send_eth_amount);
        TextView addressError = findViewById(R.id.send_eth_address_error_text_view);
        TextView amountError = findViewById(R.id.send_eth_amount_error_text_view);
        Button sendButton = findViewById(R.id.ether_send_button);
        boolean[] isValid = new boolean[2];

        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do something
                // Validate the Ethereum address as the user types
                if (!KeyGenerator.isValidEthereumAddress(charSequence.toString().trim())) {
                    addressError.setText("Invalid Ethereum Address");
                    addressError.setTextColor(Color.RED);
                    isValid[0] = false;
                    updateButton(sendButton, isValid);
                } else if (charSequence.toString().trim().equals(keyGenerator.getAddress())) {
                    addressError.setText("Sender and Receiver address same");
                    addressError.setTextColor(Color.RED);
                    isValid[0] = false;
                    updateButton(sendButton, isValid);
                } else {
                    // change the address to checksum address
                    String chkSum = KeyGenerator.getEthereumChecksumAddress(charSequence.toString().trim());
                    if(!KeyGenerator.isFullyValidEthereumAddress(chkSum)) {
                        addressError.setText("Unable to generate Ethereum checksum address");
                        addressError.setTextColor(Color.RED);
                        isValid[0] = false;
                        updateButton(sendButton, isValid);
                    } else {
                        addressError.setText("");
                        isValid[0] = true;
                        updateButton(sendButton, isValid);
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // get balance
                String balance = keyGenerator.getSavedBalance();
                String numberAmount = charSequence.toString().trim();
                BigInteger balanceWei = KeyGenerator.convertEthToWei(balance);
                BigInteger numberAmountWei = KeyGenerator.convertEthToWei(numberAmount);

                System.out.println("Balances : " + balanceWei + ": " + numberAmountWei);

                if(!KeyGenerator.isValidAmount(balanceWei, numberAmountWei)) {
                    amountError.setText("Invalid Amount");
                    amountError.setTextColor(Color.RED);
                    isValid[1] = false;
                    updateButton(sendButton, isValid);
                } else {
                    amountError.setText("");
                    isValid[1] = true;
                    updateButton(sendButton, isValid);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sendButton.setOnClickListener(e -> {
            if(isValid[0] && isValid[1]) {
                // navigate to other activity to send ether
                Intent intent = new Intent(SendEtherActivity.this, SendEthResultActivity.class);
                intent.putExtra("address", KeyGenerator.getEthereumChecksumAddress(address.getText().toString().trim()));  // Passing a string
                BigInteger numberAmountWei = KeyGenerator.convertEthToWei(amount.getText().toString().trim());
                intent.putExtra("amount", numberAmountWei.toString());          // Passing an integer
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid address or amount", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateButton(Button button, boolean[] x ) {
        if(x[0] && x[1]) {
            button.setVisibility(View.VISIBLE);
            button.setEnabled(true);
        } else {
            // disable button3
            if(button.isEnabled() || button.getVisibility() == View.VISIBLE)
                button.setVisibility(View.INVISIBLE);
                button.setEnabled(false);
        }
    }
}