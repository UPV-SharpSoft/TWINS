package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Player;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Sound;

public class MultiGameOverActivity extends AppCompatActivity {
    private TextView winnerTV, winnerScoreTV;
    private ImageView avatarWinner;
    private int score1, score2;
    private Button mainMenuButton, restartGameButton;
    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();
    private Level level;

    private Player player1;
    private Player player2;
    private int colorPlayer1;
    private int colorPlayer2;
    private String nickname1;
    private String nickname2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameovermultiplayer);



        avatarWinner = findViewById(R.id.avatarWinner);
        winnerTV = findViewById(R.id.winnerTextView);
        winnerScoreTV = findViewById(R.id.scoreWinner);

        mainMenuButton = findViewById(R.id.mainMenuButton);
        restartGameButton = findViewById(R.id.restartGameButton);


        receiveData();
        createButtons();
        audioFacadeInstance.makeSound(Sound.Sounds.victory);

        if(score1 > score2) {
            winnerScoreTV.setText("Puntuación: " + score1);
            avatarWinner.setColorFilter(colorPlayer1);
            winnerTV.setText("Ha ganado " + nickname1);
        } else{
           winnerScoreTV.setText("Puntuación: " + score2);
            avatarWinner.setColorFilter(colorPlayer1);
            winnerTV.setText("Ha ganado " + nickname2);
        }

    }

    private void receiveData(){
        Bundle data = getIntent().getExtras();
        level = (Level) data.get("level");
        player1 = (Player) getIntent().getExtras().get("player1");
        player2 = (Player) getIntent().getExtras().get("player2");
        score1 = player1.getScore().getScore();
        score2 = player2.getScore().getScore();
        colorPlayer1 = player1.getColor();
        colorPlayer2 = player2.getColor();
        nickname1 = player1.getNickname();
        nickname2 = player2.getNickname();



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
                i.putExtra("player1", player1);
                i.putExtra("player2", player2);
                startActivity(i);
                finish();
            }
        });
    }
}
