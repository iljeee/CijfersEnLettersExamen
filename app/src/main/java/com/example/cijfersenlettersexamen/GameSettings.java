package com.example.cijfersenlettersexamen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class GameSettings extends AppCompatActivity {

    final private int maxNumberOfRounds = 6;
    final private int minNumberOfRounds = 1;
    final private int defaultNumberOfRounds = 3;

    EditText et_player1;
    EditText et_player2;
    Button btn_start;
    NumberPicker np_numberOfRounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        et_player1 = findViewById(R.id.et_player1);
        et_player2 = findViewById(R.id.et_player2);
        btn_start = findViewById(R.id.btn_start);
        np_numberOfRounds = findViewById(R.id.np_numberOfRounds);

        np_numberOfRounds.setMaxValue(maxNumberOfRounds);
        np_numberOfRounds.setMinValue(minNumberOfRounds);
        np_numberOfRounds.setValue(defaultNumberOfRounds);
    }

    public void startMatch(View view){

        String player1 = et_player1.getText().toString();
        if(TextUtils.isEmpty(player1)) {
            et_player1.setError("Speler 1 naam mag niet leeg zijn");
            return;
        }

        String player2 = et_player2.getText().toString();
        if(TextUtils.isEmpty(player2)) {
            et_player2.setError("Speler 2 naam mag niet leeg zijn");
            return;
        }

        int rounds = np_numberOfRounds.getValue();

        Intent i = new Intent(this, GameScreen.class);
        i.putExtra("player1Name", player1);
        i.putExtra("player2Name", player2);
        i.putExtra("numberOfRounds", rounds);
        startActivity(i);
    }
}