package com.example.cijfersenlettersexamen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

public class GameScreen extends AppCompatActivity {

    TextView tv_player1Name;
    TextView tv_player2Name;
    TextView tv_player1Score;
    TextView tv_player2Score;
    TextView tv_roundsBanner;

    FrameLayout fl_gamePosition;

    GameViewModel viewModel;
    NumberViewModel numberViewModel;
    LetterViewModel letterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        tv_player1Name = findViewById(R.id.tv_player1Name);
        tv_player2Name = findViewById(R.id.tv_player2Name);
        tv_player1Score = findViewById(R.id.tv_player1Score);
        tv_player2Score = findViewById(R.id.tv_player2Score);
        tv_roundsBanner = findViewById(R.id.tv_roundsBanner);
        fl_gamePosition = findViewById(R.id.fl_gamePosition);

        //we maken deze viewModels hier aan zodat alle fragments gebruik kunnen maken van dezelfde instanties
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        numberViewModel = new ViewModelProvider(this).get(NumberViewModel.class);
        letterViewModel = new ViewModelProvider(this).get(LetterViewModel.class);

        //Get extras sent from GameSettings.java
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        String player1Name = bundle.getString("player1Name");
        String player2Name = bundle.getString("player2Name");
        int numberOfRounds = bundle.getInt("numberOfRounds");

        //event driven programming, wanneer er iets verandert aan de mutableLiveData,
        //wordt er een bepaalde functionaliteit uitgevoerd
        viewModel.player1Name.observe(this, name -> {
            tv_player1Name.setText(viewModel.player1Name.getValue());
        });

        viewModel.player2Name.observe(this, name -> {
            tv_player2Name.setText(viewModel.player2Name.getValue());
        });

        viewModel.player1Score.observe(this, score -> {
            tv_player1Score.setText(String.valueOf(viewModel.player1Score.getValue()));
        });

        viewModel.player2Score.observe(this, score -> {
            tv_player2Score.setText(String.valueOf(viewModel.player2Score.getValue()));
        });

        viewModel.currentRound.observe(this, round -> {
            tv_roundsBanner.setText(String.valueOf(viewModel.currentRound.getValue()));
        });

        //Wanneer current fragment verandert, ziet de observer dit en vervangen we de huidige fragment met de nieuwe
        viewModel.currentFragment.observe(this, fragment -> {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fl_gamePosition, viewModel.currentFragment.getValue());
            ft.commit();
        });

        viewModel.setNumberOfRounds(numberOfRounds);
        viewModel.setPlayerNames(player1Name, player2Name);
    }
}