package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FinPartida extends AppCompatActivity {

    private boolean isGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_partida);

        isGameOver = receiveData();
        System.out.println(isGameOver);
    }

    private boolean receiveData(){
        Bundle isGameOver = getIntent().getExtras();
        return isGameOver.getBoolean("gameOverBool");
    }
}
