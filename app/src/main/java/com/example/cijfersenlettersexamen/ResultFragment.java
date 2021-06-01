package com.example.cijfersenlettersexamen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ResultFragment extends Fragment {

    Button btn_next;
    Button btn_player1Won;
    Button btn_player2Won;
    Button btn_draw;
    TextView tv_player1Won;
    TextView tv_player2Won;
    GameViewModel viewModel;

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

        // Inflate the layout for this fragment
        return v;
    }
}