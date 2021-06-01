package com.example.cijfersenlettersexamen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class EndscreenFragment extends Fragment {

    Button btn_share;
    Button btn_main;
    TextView tv_winnerName;
    TextView tv_winnerScore;
    GameViewModel viewModel;

    public EndscreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_endscreen, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        btn_main = v.findViewById(R.id.btn_main);
        btn_main.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireActivity(), MainActivity.class);
                startActivity(i);
            }
        });

        btn_share = v.findViewById(R.id.btn_share);
        btn_share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("TAG", "Sharing is caring");
            }
        });

        tv_winnerName = v.findViewById(R.id.tv_winnerName);
        tv_winnerName.setText(viewModel.getWinnerName());

        tv_winnerScore = v.findViewById(R.id.tv_winnerScore);
        tv_winnerScore.setText(String.valueOf(viewModel.getWinnerScore()));
        return v;

    }
}