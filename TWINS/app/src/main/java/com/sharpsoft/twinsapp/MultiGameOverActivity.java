package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharpsoft.twins_clases.logic.FinalScore;
import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Sound;

import java.util.Calendar;

public class MultiGameOverActivity extends AppCompatActivity {
    private TextView winnerTV, winnerScoreTV, loserScoreTV;
    private ImageView avatarWinner, avatarLoser;
    private int score1, score2;
    private Button mainMenuButton, restartGameButton;
    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();
    private Level level;

    private int colorPlayer1;
    private int colorPlayer2;
    private String nickname1;
    private String nickname2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameovermultiplayer);


        avatarLoser = findViewById(R.id.avatarLoser);
        avatarWinner = findViewById(R.id.avatarWinner);
        winnerTV = findViewById(R.id.winnerTextView);
        winnerScoreTV = findViewById(R.id.scoreWinner);
        loserScoreTV = findViewById(R.id.scoreLoser);
        mainMenuButton = findViewById(R.id.mainMenuButton);
        restartGameButton = findViewById(R.id.restartGameButton);


        receiveData();
        createButtons();
        audioFacadeInstance.makeSound(Sound.Sounds.victory);

        if(score1 > score2) {
            winnerScoreTV.setText("Puntuación:  " + score1);
            avatarWinner.setColorFilter(colorPlayer1);
            loserScoreTV.setText("Puntuación:  " + score2);
            avatarLoser.setColorFilter(colorPlayer2);
            winnerTV.setText("Ha ganado " + nickname1);
        } else{
            winnerScoreTV.setText("Puntuación: " + score2);
            avatarWinner.setColorFilter(colorPlayer1);
            loserScoreTV.setText("Puntuación:  " + score1);
            avatarLoser.setColorFilter(colorPlayer1);
            winnerTV.setText("Ha ganado " + nickname2);
        }

    }

    private void receiveData(){
        Bundle data = getIntent().getExtras();
        score1 = data.getInt("score1");
        score2 = data.getInt("score2");
        level = (Level) data.get("level");
        colorPlayer1 = getIntent().getExtras().getInt("color1");
        colorPlayer2 = getIntent().getExtras().getInt("color2");
        nickname1 = getIntent().getExtras().getString("nickname1");
        nickname2 = getIntent().getExtras().getString("nickname2");



       /* Para Tabla Puntuaciones
        Calendar cal = Calendar.getInstance();
        int timeSpent = totalTime - (int)timeLeft;
        FinalScore results = new FinalScore(level.getType().toString(), score, timeSpent , cal);
        ConfigSingleton config =  ConfigSingleton.getInstance();
        config.saveFinalScore(results, MultiGameOverActivity.this);
        */
    }

    private void createButtons(){
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MultiGameOverActivity.this, MainMenuActivity.class);
                i.putExtra("level", level);
                startActivity(i);
                finish();
            }
        });

        restartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MultiGameOverActivity.this, MultiGameActivity.class);
                i.putExtra("level", level);
                startActivity(i);
                finish();
            }
        });
    }
}
