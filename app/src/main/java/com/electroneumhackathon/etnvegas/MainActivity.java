package com.electroneumhackathon.etnvegas;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.electroneumhackathon.etnvegas.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);

        // Set default fragment
        loadFragment(new WalletFragment());
        bottomNavigationView.setSelectedItemId(R.id.nav_wallet);

        // Handle navigation item clicks
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            if(item.getItemId() == R.id.nav_slot) {

                // new logic -
                // now I want to display list of games
                fragment = new GamesListFragment();
            } else if (item.getItemId() == R.id.nav_wallet) {
                fragment = new WalletFragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                    fragment = new ProfileFragment();
            } else if (item.getItemId() == R.id.nav_buy_chips) {
                    fragment = new BuyChipFragment();
            }
            return loadFragment(fragment);
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}