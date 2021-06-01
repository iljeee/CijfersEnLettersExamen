package com.example.cijfersenlettersexamen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;

public class ResultFragment extends Fragment {

    Button btn_next;
    Button btn_player1Won;
    Button btn_player2Won;
    Button btn_draw;
    Button btn_getSolutions;
    TextView tv_player1Won;
    TextView tv_player2Won;
    TextView tv_solutions;
    GameViewModel viewModel;
    NumberViewModel numberViewModel;
    LetterViewModel letterViewModel;

    final NumberFragment numberFragment = new NumberFragment();
    final LetterFragment letterFragment = new LetterFragment();

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        numberViewModel = new ViewModelProvider(requireActivity()).get(NumberViewModel.class);
        letterViewModel = new ViewModelProvider(requireActivity()).get(LetterViewModel.class);

        btn_next = v.findViewById(R.id.btn_nextRound);
        btn_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                viewModel.nextFragment();
            }
        });
        btn_player1Won = v.findViewById(R.id.btn_player1Won);
        btn_player1Won.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                viewModel.addPoint(1);
            }
        });

        btn_player2Won = v.findViewById(R.id.btn_player2Won);
        btn_player2Won.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                viewModel.addPoint(2);
            }
        });

        tv_player1Won = v.findViewById(R.id.tv_player1Won);
        tv_player1Won.setText(viewModel.player1Name.getValue());
        tv_player2Won = v.findViewById(R.id.tv_player2Won);
        tv_player2Won.setText(viewModel.player2Name.getValue());

        btn_draw = v.findViewById(R.id.btn_draw);
        btn_draw.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                viewModel.draw();
            }
        });

        tv_solutions = v.findViewById(R.id.tv_solutions);
        numberViewModel.resultString.observe(getViewLifecycleOwner(), name -> {
            LinkedList<String> allSolutions = numberViewModel.resultString.getValue();
            tv_solutions.setText("");
            Log.d("TAG", "Observer result string number");
            for (String solution : allSolutions) {
                tv_solutions.append(solution + "\n");
            }
        });

        letterViewModel.resultString.observe(getViewLifecycleOwner(), name -> {
            LinkedList<String> allSolutions = letterViewModel.resultString.getValue();
            tv_solutions.setText("");
            Log.d("TAG", "Observer result string letter");
            for (String solution : allSolutions) {
                tv_solutions.append(solution + "\n");
            }
        });

        if(viewModel.currentGame == viewModel.numberFragment){
            Log.d("TAG", "if" + viewModel.currentGame);
            numberViewModel.getSolutions();
        } else {
            Log.d("TAG", "else" + viewModel.currentGame);
            letterViewModel.getSolutions();
        }


        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(viewModel.currentGame == viewModel.numberFragment){
            numberViewModel.reset();
        } else {
            letterViewModel.reset();
        }
        tv_solutions.setText("");
    }
}