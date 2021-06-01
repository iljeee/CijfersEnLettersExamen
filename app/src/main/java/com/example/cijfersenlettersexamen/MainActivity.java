package com.example.cijfersenlettersexamen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Onclick function for exit button
    public void closeApp(View view) {
        this.finishAffinity();
    }

    //Begint een spel wanneer de knop ingedrukt wordt
    public void startMatch(View view) {
        Intent i = new Intent(this, GameSettings.class);
        startActivity(i);
    }
}