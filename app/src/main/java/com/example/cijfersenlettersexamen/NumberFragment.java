package com.example.cijfersenlettersexamen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NumberFragment extends Fragment {

    Button btn_next;
    Button btn_big;
    Button btn_small;
    TextView tv_targetNumber;
    TextView tv_selectedNumbers;
    GameViewModel viewModel;
    NumberViewModel numberViewModel;
    ProgressBar pb_progressBar;

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
        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);

        btn_big = v.findViewById(R.id.vowel);
        btn_big.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int number = numberViewModel.getBigNumber();
                tv_selectedNumbers.append(" " + number);
            }
        });

        btn_small = v.findViewById(R.id.consonant);
        btn_small.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int number = numberViewModel.getSmallNumber();
                tv_selectedNumbers.append(" " + number);
            }
        });

        tv_selectedNumbers = v.findViewById(R.id.tv_selectedLetters);

        numberViewModel = new ViewModelProvider(this).get(NumberViewModel.class);
        numberViewModel.numbersLimitReached.observe(getViewLifecycleOwner(), name -> {
            btn_small.setVisibility(View.GONE);
            btn_big.setVisibility(View.GONE);
            numberViewModel.startTimer();
        });

        tv_targetNumber = v.findViewById(R.id.tv_targetNumber);
        tv_targetNumber.setText(String.valueOf(numberViewModel.getTargetNumber()));

        pb_progressBar = v.findViewById(R.id.pb_progressBar);
        numberViewModel.timerNumber.observe(getViewLifecycleOwner(), name -> {
            pb_progressBar.setProgress(numberViewModel.timerNumber.getValue());
        });

        numberViewModel.timerLimit.observe(getViewLifecycleOwner(), name -> {
            pb_progressBar.setMax(numberViewModel.timerLimit.getValue());
        });

        numberViewModel.timerLimitReached.observe(getViewLifecycleOwner(), name -> {
            viewModel.nextFragment();
        });
        // Inflate the layout for this fragment
        return v;
    }
}