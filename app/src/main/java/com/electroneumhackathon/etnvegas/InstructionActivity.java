package com.electroneumhackathon.etnvegas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.electroneumhackathon.etnvegas.R;

public class InstructionActivity extends AppCompatActivity {
    private WebView instructionWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_instruction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        instructionWebView = findViewById(R.id.instructionWebView);
        WebSettings webSettings = instructionWebView.getSettings();

        webSettings.setJavaScriptEnabled(true); // Enable JavaScript if needed
        webSettings.setAllowFileAccess(true); // Allow access to local files
        webSettings.setDomStorageEnabled(true); // Enable DOM storage if your HTML uses it
        webSettings.setAllowContentAccess(true); // Allow content access

        Button walletButton = findViewById(R.id.wallet_button_instructions);

        // Your HTML formatted text
        // Read HTML content from assets

        walletButton.setOnClickListener(e -> {
            // destroy all activities and go to main
            walletButton.setVisibility(View.INVISIBLE);
            walletButton.setEnabled(false);
            Intent intent = new Intent(InstructionActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        instructionWebView.loadUrl("file:///android_asset/instructions.html");
    }

//    private class LoadHtmlTask extends AsyncTask<Void, Void, String> {
//        @Override
//        protected String doInBackground(Void... params) {
//            // Load your HTML file from local resources
//            String htmlData = loadHtmlFile();
//            return htmlData;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            instructionWebView.loadData(result, "text/html", "UTF-8");
//        }
//    }
//
//    private String loadHtmlFile() {
//        // Read HTML file from assets or local storage
//        StringBuilder stringBuilder = new StringBuilder();
//        try {
//            InputStream inputStream = getAssets().open("instructions.html");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//            inputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return stringBuilder.toString();
//    }
}