package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharpsoft.twins_clases.logic.Board;
import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twins_clases.logic.FlipObserver;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Audio;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Deck;
import com.sharpsoft.twinsapp.AndroidStudioLogic.DeckFactory;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Score;

import java.text.DecimalFormat;


import static com.sharpsoft.twinsapp.AndroidStudioLogic.Audio.Sounds.*;


public class Game extends AppCompatActivity {

    private TextView chronoTV;
    private CountDownTimer chronometer;
    private long timeLeft;
    private boolean first = true;
    private boolean gameOverBool = false;

    private LinearLayout tableLayout;
    private Board board;
    private ImageButton imageButtonPause;
    private Audio audioInstance = Audio.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        chronoTV = findViewById(R.id.cronoTV);
        tableLayout = findViewById(R.id.tableroLayout);
        imageButtonPause = findViewById(R.id.imageButtonPause);

        addBoard();

        instanceChronometer(60000);
        chronometer.start();

        ToPausedActivity();

        //float volume = (float) (1 - (Math.log(Audio.MAX_VOLUME -
          //      audioInstance.getMusicSeekbarProgress()) / Math.log(Audio.MAX_VOLUME)));
        audioInstance.stopMusic();
        float volume = Audio.getMusicVolume();
        audioInstance.startMusic(this, R.raw.partida_default);
        audioInstance.setMusicVolume(volume, volume);

    }

    @Override
    protected void onResume() {
            super.onResume();
            if(!first) {
                instanceChronometer(timeLeft);
                chronometer.start();
                audioInstance.resumeMusic();
            }
            first = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        audioInstance.pauseMusic();
    }

    public void addBoard() {
        Dimension dimension = new Dimension(4, 6);
        Deck deck = DeckFactory.getDeck(DeckFactory.Decks.fruits, dimension, this);
        board = new com.sharpsoft.twinsapp.AndroidStudioLogic.Board(dimension, deck);

        View tableroView = ((com.sharpsoft.twinsapp.AndroidStudioLogic.Board) board).getView(this);
        tableLayout.addView(tableroView);

        board.addObserver(new FlipObserver() {
            @Override
            public void onFlip() {
                audioInstance.makeSound(flip);

                Log.i("tablero", "tablero " + board.isComplete());
            }

            @Override
            public void onSuccess() {
                audioInstance.makeSound(correct);
                if(board.isComplete()){ //Si se ha terminado el tablero
                    Intent i = new Intent(Game.this, GameOver.class);
                    i.putExtra("gameOverBool", gameOverBool);
                    i.putExtra("timeLeft", timeLeft);
                    i.putExtra("score", board.getScore().getScore());
                    chronometer.cancel();
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure() {
                audioInstance.makeSound(incorrect);
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
                audioInstance.stopMusic();
                chronoTV.setText(cronoFormatLong.format(0));

                Intent i = new Intent(Game.this, GameOver.class);
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
                Intent intent = new Intent(Game.this, PausedActivity.class);
                startActivity(intent);
                chronometer.cancel();
                audioInstance.pauseMusic();
            }
        });
    }

}
