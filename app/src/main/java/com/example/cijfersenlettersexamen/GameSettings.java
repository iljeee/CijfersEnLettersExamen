package com.example.cijfersenlettersexamen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class GameSettings extends AppCompatActivity {

    EditText et_player1;
    EditText et_player2;
    NumberPicker np_numberOfRounds;
    Button btn_start;
    GameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        et_player1 = findViewById(R.id.et_player1);
        et_player2 = findViewById(R.id.et_player2);
        np_numberOfRounds = findViewById(R.id.np_numberOfRounds);
        btn_start = findViewById(R.id.btn_start);

        np_numberOfRounds.setMaxValue(6);
        np_numberOfRounds.setMinValue(1);
        np_numberOfRounds.setValue(3);
    }

    public void startMatch(View view){
        String player1 = et_player1.getText().toString();
        String player2 = et_player2.getText().toString();
        int rounds = np_numberOfRounds.getValue();

        Intent i = new Intent(this, GameScreen.class);
        i.putExtra("player1Name", player1);
        i.putExtra("player2Name", player2);
        i.putExtra("numberOfRounds", rounds);
        startActivity(i);

    }
}