package com.sharpsoft.twinsapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sharpsoft.twinsapp.AndroidStudioLogic.FlipObserver;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Turn;
import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Board;
import com.sharpsoft.twinsapp.AndroidStudioLogic.BoardStandard;
import com.sharpsoft.twinsapp.AndroidStudioLogic.BoardByCard;
import com.sharpsoft.twinsapp.AndroidStudioLogic.BoardBySet;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Deck;
import com.sharpsoft.twinsapp.AndroidStudioLogic.DeckFactory;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Score;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Sound;

import java.text.DecimalFormat;


public class GameActivity extends AppCompatActivity {

    private TextView chronoTV, turnTimer;
    private int turnSeconds;
    private CountDownTimer chronometer;
    private CountDownTimer turnCrono;
    private long timeLeft;
    private boolean first = true;
    private boolean gameOverBool = false;
    private int levelNumber;
    private Level level;

    private LinearLayout tableLayout;
    private Board board;
    private ImageButton imageButtonPause;
    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();
    private final DecimalFormat cronoFormatLong = new DecimalFormat("#0.0");

    private static GameActivity instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        chronoTV = findViewById(R.id.cronoTV);
        tableLayout = findViewById(R.id.tableroLayout);
        imageButtonPause = findViewById(R.id.imageButtonPause);
        turnTimer = findViewById(R.id.turnTimer);

        instance = this;

        int song = ConfigSingleton.getInstance().getSelectedMusic();
        level = (Level) getIntent().getExtras().get("level");
        levelNumber = getIntent().getExtras().getInt("levelNumber", -1);

        Deck deck = ConfigSingleton.getInstance().getSelectedDeck(level.getDimension(), level.getNumPairs(), this);
        if(level.getType() == Level.Type.standard){
            board = new BoardStandard(level.getDimension(), level.getTimePerTurn(), deck);
        }else if(level.getType() == Level.Type.byCard){
            board = new BoardByCard(level.getDimension(), level.getTimePerTurn(), deck);
        }else if(level.getType() == Level.Type.bySet){
            DeckFactory.Decks decks = deck.getName().equals("Minecraft")? DeckFactory.Decks.fruits : DeckFactory.Decks.fruits;
            Deck deck2 = DeckFactory.getDeck(decks, level.getDimension(), level.getDimension().getTotal()/2, this);
            board = new BoardBySet(level.getDimension(), level.getTimePerTurn(), deck, deck2);
        }
        int time = level.getTotalTime();

        board.setFlipTime(level.getFlipTime() == null? 500 : level.getFlipTime());

        Log.i("asd", level.getNumPairs() + "");

        setBoard();
        instanceChronometer(time);
        instanceSoundFX();
        chronometer.start();
        ToPausedActivity();
        audioFacadeInstance.setMusicGame(this, song);
        instanceTurn();

        Integer startTimeFlip = level.getFlipStartTime();
        if(startTimeFlip != null) board.flipAllCardsDuring(startTimeFlip);
    }

    public void instanceTurn(){
        turnSeconds = board.getTurn().getDuration();
        turnTimer.setText(cronoFormatLong.format(turnSeconds / 10.0));
        board.getTurn().addObserver(new Turn.TurnObserver() {
            @Override
            public void onStart() {
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        if(turnCrono != null) turnCrono.cancel();
                        turnCrono = new CountDownTimer(board.getTurn().getDuration(), 100){
                            @Override
                            public void onTick(long millisUntilFinished) {
                                turnTimer.setText("" + cronoFormatLong.format(millisUntilFinished / 1000.0));
                            }

                            @Override
                            public void onFinish() {
                                turnTimer.setText("" + cronoFormatLong.format(turnSeconds/1000));
                            }
                        }.start();
                    }
                });
            }

            @Override
            public void onEnd() {

            }

            @Override
            public void lost() {

            }
        });
    }

    public void setBoard() {
        View tableroView = board.getView(this);
        tableLayout.addView(tableroView);

        board.addObserver(new FlipObserver() {
            @Override
            public void onFlip() {

            }

            @Override
            public void onSuccess() {
                if(board.isComplete()){
                    Intent i = new Intent(GameActivity.this, GameOverActivity.class);
                    i.putExtra("gameOverBool", gameOverBool);
                    i.putExtra("timeLeft", timeLeft);
                    i.putExtra("score", board.getScore().getScore());
                    i.putExtra("totalTime", level.getTotalTime());
                    i.putExtra("level", level);
                    ConfigSingleton.getInstance().setLevelsPassed(levelNumber, GameActivity.this);
                    chronometer.cancel();
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure() {

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
                i.putExtra("level", level);
                startActivity(i);
                finish();
            }
        };
    }

    public void instanceSoundFX(){
        board.addObserver(new FlipObserver() {
            @Override
            public void onFlip() {
                audioFacadeInstance.makeSound(Sound.Sounds.flip);
            }

            @Override
            public void onSuccess() {
                audioFacadeInstance.makeSound(Sound.Sounds.correct);
            }

            @Override
            public void onFailure() {
                audioFacadeInstance.makeSound(Sound.Sounds.incorrect);
            }
        });
    }

    public void ToPausedActivity() {
        imageButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, PausedActivity.class);
                intent.putExtra("level", level);
                intent.putExtra("multiplayer", false);
                startActivity(intent);
                chronometer.cancel();
                audioFacadeInstance.pauseMusic();

            }
        });
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

    }

    @Override
    protected void onPause() {
        super.onPause();
        audioFacadeInstance.pauseMusic();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        chronometer.cancel();
    }

    @Override
    public void onBackPressed() {

    }

    public static void closedMethod(){
        instance.finish();
    }
}
