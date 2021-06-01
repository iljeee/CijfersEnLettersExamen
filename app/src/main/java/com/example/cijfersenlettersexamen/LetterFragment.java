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

import org.w3c.dom.Text;

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
        btn_vowel = v.findViewById(R.id.btn_vowel);
        btn_consonant = v.findViewById(R.id.btn_consonant);
        pb_progressBar = v.findViewById(R.id.pb_progressBar);
        tv_selectedLetters = v.findViewById(R.id.tv_selectedLetters);

        letterViewModel = new ViewModelProvider(this).get(LetterViewModel.class);
        letterViewModel.lettersLimitReached.observe(getViewLifecycleOwner(), name -> {
            btn_consonant.setVisibility(View.GONE);
            btn_vowel.setVisibility(View.GONE);
            letterViewModel.startTimer();
        });

        btn_vowel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String letter = letterViewModel.getVowel();
                tv_selectedLetters.append(" " + letter);
            }
        });
        btn_consonant.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String letter = letterViewModel.getConsonant();
                tv_selectedLetters.append(" " + letter);
            }
        });

        pb_progressBar = v.findViewById(R.id.pb_progressBar);
        letterViewModel.timerNumber.observe(getViewLifecycleOwner(), name -> {
            pb_progressBar.setProgress(letterViewModel.timerNumber.getValue());
        });

        letterViewModel.timerLimit.observe(getViewLifecycleOwner(), name -> {
            pb_progressBar.setMax(letterViewModel.timerLimit.getValue());
        });

        letterViewModel.timerLimitReached.observe(getViewLifecycleOwner(), name -> {
            viewModel.nextFragment();
        });

        // Inflate the layout for this fragment
        return v;
    }
}