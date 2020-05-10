package com.sharpsoft.twinsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sharpsoft.twins_clases.logic.FlipObserver;
import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Board;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Deck;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Score;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Sound;

import java.text.DecimalFormat;


public class GameActivity extends AppCompatActivity {

    private TextView chronoTV;
    private CountDownTimer chronometer;
    private long timeLeft;
    private boolean first = true;
    private boolean gameOverBool = false;
    private static boolean closed = false;
    private int levelNumber;
    private Context thisContext;

    private LinearLayout tableLayout;
    private com.sharpsoft.twinsapp.AndroidStudioLogic.Board board;
    private ImageButton imageButtonPause;
    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        chronoTV = findViewById(R.id.cronoTV);
        tableLayout = findViewById(R.id.tableroLayout);
        imageButtonPause = findViewById(R.id.imageButtonPause);

        int music = ConfigSingleton.getInstance().getSelectedMusic();
        Level level = (Level) getIntent().getExtras().get("level");
        levelNumber = (int) getIntent().getExtras().getInt("levelNumber", -1);

        thisContext = this;

        Deck deck = ConfigSingleton.getInstance().getSelectedDeck(level.getDimension(), level.getNumPairs(), this);
        board = new Board(level.getDimension(), level.getTimePerTurn(), deck);
        int time = level.getTotalTime();

        setBoard();
        instanceChronometer(time);

        chronometer.start();
        ToPausedActivity();

        audioFacadeInstance.stopMusic();
        float volume = audioFacadeInstance.getMusicVolume();
        audioFacadeInstance.startMusic(this, music);
        audioFacadeInstance.setMusicVolume(volume);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!first) {
            instanceChronometer(timeLeft);
            chronometer.start();
            audioFacadeInstance.resumeMusic();
        }
        first = false;

        if(closed){
            finish();
            closed = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        audioFacadeInstance.pauseMusic();
    }

    public void setBoard() {
        //Dimension dimension = new Dimension(4, 6);
        //board = new com.sharpsoft.twinsapp.AndroidStudioLogic.Board(dimension, deck);

        View tableroView = ((com.sharpsoft.twinsapp.AndroidStudioLogic.Board) board).getView(this);
        tableLayout.addView(tableroView);

        board.addObserver(new FlipObserver() {
            @Override
            public void onFlip() {
                audioFacadeInstance.makeSound(Sound.Sounds.flip);
            }

            @Override
            public void onSuccess() {
                audioFacadeInstance.makeSound(Sound.Sounds.correct);
                if(board.isComplete()){
                    Intent i = new Intent(GameActivity.this, GameOverActivity.class);
                    i.putExtra("gameOverBool", gameOverBool);
                    i.putExtra("timeLeft", timeLeft);
                    i.putExtra("score", board.getScore().getScore());
                    ConfigSingleton.getInstance().setLevelsPassed(levelNumber, thisContext);
                    chronometer.cancel();
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure() {
                audioFacadeInstance.makeSound(Sound.Sounds.incorrect);
            }
        });

        TextView puntuacionTextView = findViewById(R.id.puntuacionTextView);
        board.setScore(new Score(puntuacionTextView));
    }
    
    private void instanceChronometer(long time){
        chronometer = new CountDownTimer(time, 100) {

            private final DecimalFormat cronoFormatLong = new DecimalFormat("#0.0");

            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished>10000) chronoTV.setText("" + millisUntilFinished / 1000);
                else chronoTV.setText("" + cronoFormatLong.format(millisUntilFinished / 1000.0));
                timeLeft = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                gameOverBool = true;
                audioFacadeInstance.stopMusic();
                chronoTV.setText(cronoFormatLong.format(0));

                Intent i = new Intent(GameActivity.this, GameOverActivity.class);
                i.putExtra("gameOverBool", gameOverBool);
                i.putExtra("score", board.getScore().getScore());
                startActivity(i);
                finish();
            }
        };
    }


    public void ToPausedActivity() {
        imageButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, PausedActivity.class);
                startActivity(intent);
                chronometer.cancel();
                audioFacadeInstance.pauseMusic();
            }
        });
    }

    public static void closedMethod(){
        closed = true;
    }
}
