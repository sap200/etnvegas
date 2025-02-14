package com.electroneumhackathon.etnvegas;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GamesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GamesListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private CardView slotMachine;
    private CardView fortuneWheel;
    private CardView europeanRoulette;
    private CardView mysticDice;

    public GamesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GamesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GamesListFragment newInstance(String param1, String param2) {
        GamesListFragment fragment = new GamesListFragment();
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
        view =  inflater.inflate(R.layout.fragment_games_list, container, false);

        slotMachine = view.findViewById(R.id.casino_slot_machine);
        fortuneWheel = view.findViewById(R.id.casino_fortune_wheel);
        europeanRoulette = view.findViewById(R.id.roulette_game_casino);
        mysticDice = view.findViewById(R.id.casino_dice_game);


        if(isAdded() && getActivity() != null) {
            // slot game redirection
            slotMachine.setOnClickListener(e -> {
                Intent intent = new Intent(getActivity(), SlotGameActivity.class);
                startActivity(intent);
            });

            // spin wheel game redirection
            fortuneWheel.setOnClickListener(e -> {
                Intent intent = new Intent(getActivity(), PlayFortuneWheelGame.class);
                startActivity(intent);
            });

            // dice game
            mysticDice.setOnClickListener(e -> {
                Intent intent = new Intent(getActivity(), PlayMysticDiceGame.class);
                startActivity(intent);
            });

            // roulette game redirection
        }

        return view;

    }
}