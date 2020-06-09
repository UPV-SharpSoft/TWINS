package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sharpsoft.twinsapp.AndroidStudioLogic.FinalScore;
import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Sound;

import java.util.Calendar;

public class GameOverActivity extends AppCompatActivity {

    private boolean isGameOver;
    private long timeLeft;
    private int totalTime;
    private TextView resultTV, timeTV, scoreTV;
    private int score;
    private Button mainMenuButton, resetButton;
    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();
    private Level level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_partida);

        resultTV = findViewById(R.id.resultTV);
        timeTV = findViewById(R.id.timeTV);
        scoreTV = findViewById(R.id.scoreTV);
        mainMenuButton = findViewById(R.id.mainMenuButton);
        resetButton = findViewById(R.id.resetButton);


        receiveData();
        createButtons();
        if(isGameOver) {
            audioFacadeInstance.makeSound(Sound.Sounds.gameover);
        }else{
            audioFacadeInstance.makeSound(Sound.Sounds.victory);
        }

        timeTV.setText("Tu tiempo ha sido de " + (totalTime-timeLeft)/1000 + "s.");

        if(isGameOver) {
            resultTV.setText("¡DERROTA!");
            timeTV.setText("Se te ha agotado el tiempo");
        }
        int finalScore = calcScore(score, timeLeft);

        scoreTV.setText("Puntuación final: " + finalScore);

        Calendar cal = Calendar.getInstance();
        int timeSpent = totalTime - (int)timeLeft;
        FinalScore results = new FinalScore(level.getType().toString(), finalScore, timeSpent , cal);

        ConfigSingleton config =  ConfigSingleton.getInstance();
        config.saveFinalScore(results, GameOverActivity.this);
    }

    static public int calcScore(int score, long timeLeft) {
        return score + (int) timeLeft / 1000;
    }

    private void receiveData(){
        Bundle data = getIntent().getExtras();
        timeLeft = data.getLong("timeLeft", 0);
        totalTime = data.getInt("totalTime");
        isGameOver = data.getBoolean("gameOverBool");
        score = data.getInt("score");
        level = (Level) data.get("level");
    }

    private void createButtons(){
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameOverActivity.this, MainMenuActivity.class);
                i.putExtra("level", level);
                startActivity(i);
                finish();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameOverActivity.this, GameActivity.class);
                i.putExtra("level", level);
                startActivity(i);
                finish();
            }
        });
    }
}
