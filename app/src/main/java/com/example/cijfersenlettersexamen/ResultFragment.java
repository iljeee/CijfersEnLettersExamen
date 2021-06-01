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
    TextView tv_player1Won;
    TextView tv_player2Won;
    TextView tv_solutions;
    GameViewModel viewModel;
    NumberViewModel numberViewModel;
    LetterViewModel letterViewModel;

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
        btn_player1Won = v.findViewById(R.id.btn_player1Won);
        btn_player2Won = v.findViewById(R.id.btn_player2Won);
        btn_draw = v.findViewById(R.id.btn_draw);
        tv_player1Won = v.findViewById(R.id.tv_player1Won);
        tv_player2Won = v.findViewById(R.id.tv_player2Won);
        tv_solutions = v.findViewById(R.id.tv_solutions);

        tv_player1Won.setText(viewModel.player1Name.getValue());
        tv_player2Won.setText(viewModel.player2Name.getValue());

        tv_solutions.setText("Loading solutions");

        //Afhankelijk van de vorige fragment kijken welke solutions opgeroepen
        //moeten worden, numbers of letters
        if(viewModel.currentGame == viewModel.numberFragment){
            numberViewModel.getSolutions();
        } else {
            letterViewModel.getSolutions();
        }

        //resultString wordt observed, allSolutions wordt ingevuld met de resultString
        //van numbers of letters om dan over elk element van de list te loopen en ze allemaal te
        //tonen aan de gebruiker door ze toe te voegen aan tv_solutions

        numberViewModel.resultString.observe(getViewLifecycleOwner(), name -> {
            LinkedList<String> allSolutions = numberViewModel.resultString.getValue();
            //"Solutions loading" weghalen
            tv_solutions.setText("");
            for (String solution : allSolutions) {
                tv_solutions.append(solution + "\n");
            }
        });

        letterViewModel.solutionString.observe(getViewLifecycleOwner(), name -> {
            LinkedList<String> allSolutions = letterViewModel.solutionString.getValue();
            tv_solutions.setText("");
            for (String solution : allSolutions) {
                tv_solutions.append(solution + "\n");
            }
        });

        //Verder gaan naar de volgende ronde of het einde van het spel
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.nextFragment();
            }
        });

        //Speler 1 een punt geven
        btn_player1Won.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.addPoint(1);
            }
        });

        //Speler 2 een punt geven
        btn_player2Won.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.addPoint(2);
            }
        });
        //Beide spelers een punt geven
        btn_draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.draw();
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    //Wanneer de fragment verdwijnt van het scherm kijken we of de vorige ronde numbers of
    //letters was en resetten we de juiste ronde
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(viewModel.currentGame == viewModel.numberFragment){
            numberViewModel.reset();
        } else {
            letterViewModel.reset();
        }
    }
}