package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    private boolean isGameOver;
    private long timeLeft;
    private TextView resultTV, timeTV;
    private Button exitButton, resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_partida);

        resultTV = findViewById(R.id.resultTV);
        timeTV = findViewById(R.id.timeTV);
        exitButton = findViewById(R.id.exitButton);
        resetButton = findViewById(R.id.resetButton);

        receiveData();
        createButtons();

        timeTV.setText("Tu tiempo ha sido de " + (60000-timeLeft)/1000 + "s.");

        if(isGameOver) {
            resultTV.setText("¡DERROTA!");
            timeTV.setText("Se te ha agotado el tiempo");
        }
    }

    private void receiveData(){
        Bundle data = getIntent().getExtras();
        timeLeft = data.getLong("timeLeft", 0);
        isGameOver = data.getBoolean("gameOverBool");
    }

    private void createButtons(){
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameOver.this, Game.class);
                startActivity(i);
                finish();
            }
        });
    }
}
