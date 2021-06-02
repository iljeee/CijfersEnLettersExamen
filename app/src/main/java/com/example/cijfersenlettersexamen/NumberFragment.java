package com.example.cijfersenlettersexamen;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NumberFragment extends Fragment {

    ImageView btn_big;
    ImageView btn_small;
    TextView tv_targetNumber;
    TextView tv_selectedNumbers;
    ProgressBar pb_progressBar;
    GameViewModel viewModel;
    NumberViewModel numberViewModel;
    MediaPlayer player;

    public NumberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_numbers, container, false);

        player = MediaPlayer.create(requireActivity(), R.raw.jeopardy_theme_song);
        player.setLooping(true);

        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        numberViewModel = new ViewModelProvider(requireActivity()).get(NumberViewModel.class);

        btn_big = v.findViewById(R.id.vowel);
        btn_small = v.findViewById(R.id.consonant);
        tv_targetNumber = v.findViewById(R.id.tv_targetNumber);
        tv_selectedNumbers = v.findViewById(R.id.tv_selectedLetters);
        pb_progressBar = v.findViewById(R.id.pb_progressBar);

        tv_targetNumber.setText(String.valueOf(numberViewModel.getTargetNumber()));

        numberViewModel.numbersLimitReached.observe(getViewLifecycleOwner(), name -> {
            if (numberViewModel.numbersLimitReached.getValue()){
                btn_small.setVisibility(View.GONE);
                btn_big.setVisibility(View.GONE);
                numberViewModel.startTimer();
                player.start();
            }
        });

        numberViewModel.timerNumber.observe(getViewLifecycleOwner(), name -> {
            pb_progressBar.setProgress(numberViewModel.timerNumber.getValue());
        });

        numberViewModel.timerLimit.observe(getViewLifecycleOwner(), name -> {
            pb_progressBar.setMax(numberViewModel.timerLimit.getValue());
        });

        numberViewModel.timerLimitReached.observe(getViewLifecycleOwner(), name -> {
            if(numberViewModel.timerLimitReached.getValue()){
                viewModel.nextFragment();
            }
        });

        //Genereert een groot getal en voegt deze toe aan de textView
        btn_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = numberViewModel.getBigNumber();
                tv_selectedNumbers.append(" " + number);
            }
        });

        //Genereert een klein getal en voegt deze toe aan de textView
        btn_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = numberViewModel.getSmallNumber();
                tv_selectedNumbers.append(" " + number);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}