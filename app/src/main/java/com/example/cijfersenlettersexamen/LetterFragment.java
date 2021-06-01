package com.example.cijfersenlettersexamen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class LetterFragment extends Fragment {

    Button btn_vowel;
    Button btn_consonant;
    TextView tv_selectedLetters;
    ProgressBar pb_progressBar;
    GameViewModel viewModel;
    LetterViewModel letterViewModel;

    public LetterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_letters, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        letterViewModel = new ViewModelProvider(requireActivity()).get(LetterViewModel.class);

        btn_vowel = v.findViewById(R.id.btn_vowel);
        btn_consonant = v.findViewById(R.id.btn_consonant);
        tv_selectedLetters = v.findViewById(R.id.tv_selectedLetters);
        pb_progressBar = v.findViewById(R.id.pb_progressBar);

        //Wanneer lettersLimitReached true is, worden de buttons verborgen en de timer gestart
        letterViewModel.lettersLimitReached.observe(getViewLifecycleOwner(), name -> {
            if(letterViewModel.lettersLimitReached.getValue()){
                btn_consonant.setVisibility(View.GONE);
                btn_vowel.setVisibility(View.GONE);
                letterViewModel.startTimer();
            }
        });

        //De vooruitgang van de progress bar
        letterViewModel.timerNumber.observe(getViewLifecycleOwner(), name -> {
            pb_progressBar.setProgress(letterViewModel.timerNumber.getValue());
        });

        //De maximale speeltijd
        letterViewModel.timerLimit.observe(getViewLifecycleOwner(), name -> {
            pb_progressBar.setMax(letterViewModel.timerLimit.getValue());
        });

        //Wanneer de progressbar het einde bereikt, wordt de result fragment geladen
        letterViewModel.timerLimitReached.observe(getViewLifecycleOwner(), name -> {
            if(letterViewModel.timerLimitReached.getValue()){
                viewModel.nextFragment();
            }
        });

        //Genereert een klinker en voegt deze toe aan de textView
        btn_vowel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Character letter = letterViewModel.getVowel();
                tv_selectedLetters.append(" " + letter);
            }
        });

        //Genereert een medeklinker en voegt deze toe aan de textView
        btn_consonant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Character letter = letterViewModel.getConsonant();
                tv_selectedLetters.append(" " + letter);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }
}