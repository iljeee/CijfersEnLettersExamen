package com.example.cijfersenlettersexamen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Variables
    Button btn_start;
    Button btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = findViewById(R.id.btn_start);

    }
    //Onclick function for exit button
    public void closeApp(View view) {
        this.finishAffinity();
    }

    //Starts a match when button is pressed
    public void startMatch(View view) {
        Intent i = new Intent(this, GameSettings.class);
        startActivity(i);
    }
}