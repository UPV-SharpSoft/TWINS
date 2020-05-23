package com.sharpsoft.twinsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sharpsoft.twins_clases.logic.FlipObserver;
import com.sharpsoft.twins_clases.logic.Turn;
import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Board;
import com.sharpsoft.twinsapp.AndroidStudioLogic.BoardBySet;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Deck;
import com.sharpsoft.twinsapp.AndroidStudioLogic.DeckFactory;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Player;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Score;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Sound;

import java.text.DecimalFormat;

public class MultiGameActivity extends AppCompatActivity {

    private TextView turnTimer, puntuacionTextView, puntuacion2TextView;
    private CountDownTimer chronometer, turnCrono;
    private long timeLeft;
    private int turnSeconds;
    private LinearLayout tableLayout;
    private com.sharpsoft.twins_clases.logic.Board board;
    private ImageButton imageButtonPause;
    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();
    private Context thisContext;
    private boolean first = true;
    private boolean gameOverBool = false;
    private static boolean closed = false;
    private Level level;
    private int levelNumber;
    private final DecimalFormat cronoFormatLong = new DecimalFormat("#0.0");
    private Player player1;
    private Player player2;
    private int turnCounter;
    private boolean player1Turn = true;

    //UI
    private int colorPlayer1;
    private int colorPlayer2;
    private String nickname1;
    private String nickname2;
    private TextView player1TV, player2TV;
    private ImageView avatar1, avatar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        tableLayout = findViewById(R.id.tableroLayout);
        imageButtonPause = findViewById(R.id.imageButtonPause);
        turnTimer = findViewById(R.id.turnTimer);
        puntuacionTextView = findViewById(R.id.puntuacionTextView);
        puntuacion2TextView = findViewById(R.id.puntuacion2TextView);
        player1TV = findViewById(R.id.player1TV);
        player2TV = findViewById(R.id.player2TV);
        avatar1 = findViewById(R.id.avatar1);
        avatar2 = findViewById(R.id.avatar2);


        receiveData();

        int song = ConfigSingleton.getInstance().getSelectedMusic();
        thisContext = this;


        thisContext = this;

        Deck deck = ConfigSingleton.getInstance().getSelectedDeck(level.getDimension(), level.getNumPairs(), this);
        if(level.getType() == Level.Type.standard){
            board = new Board(level.getDimension(), level.getTimePerTurn(), deck);
        }else if(level.getType() == Level.Type.byCard){
            board = new Board(level.getDimension(), level.getTimePerTurn(), deck);
        }else if(level.getType() == Level.Type.bySet){
            DeckFactory.Decks decks = deck.getName().equals("Minecraft")? DeckFactory.Decks.fruits : DeckFactory.Decks.fruits;
            Deck deck2 = DeckFactory.getDeck(decks, level.getDimension(), level.getDimension().getTotal()/2, this);
            board = new BoardBySet(level.getDimension(), level.getTimePerTurn(), deck, deck2);
        }
        int time = level.getTotalTime();

        board.setTiempoVolteo(level.getFlipTime() == null? 500 : level.getFlipTime());

        setBoard();
        instanceChronometer(time);

        chronometer.start();
        ToPausedActivity();

        audioFacadeInstance.setMusicGame(this, song);

        Integer startTimeFlip = level.getFlipStartTime();
        if(startTimeFlip != null) board.flipAllCardsDuring(startTimeFlip);

        turnSeconds = board.getTurn().getDuration();
        board.getTurn().addObserver(new Turn.TurnObserver() {
            @Override
            public void onStart() {
                turnCrono = new CountDownTimer(board.getTurn().getDuration(), 100){
                    @Override
                    public void onTick(long millisUntilFinished) {
                        turnTimer.setText("" + cronoFormatLong.format(millisUntilFinished / 1000.0));
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }

            @Override
            public void onEnd() {
            }

            @Override
            public void lost() {
                if(player1Turn) {
                    player1.getScore().missedTurn();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            puntuacionTextView.setText(String.valueOf(player1.getScore().getScore()));

                        }
                    });
                    player1Turn=false;
                }else{
                    player2.getScore().missedTurn();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            puntuacion2TextView.setText(String.valueOf(player2.getScore().getScore()));
                        }
                    });
                    player1Turn=true;
                }

            }
        });
    }

    private void receiveData() {
        level = (Level) getIntent().getExtras().get("level");
        levelNumber = getIntent().getExtras().getInt("levelNumber", -1);
        player1 = (Player) getIntent().getExtras().get("player1");
        player2 = (Player) getIntent().getExtras().get("player2");
        colorPlayer1 = player1.getColor();
        colorPlayer2 = player2.getColor();
        nickname1 = player1.getNickname();
        nickname2 = player2.getNickname();
        Log.i("nick", ""+nickname1+nickname2);
        player1TV.setText(String.valueOf(nickname1));
        player2TV.setText(String.valueOf(nickname2));
        player1TV.setTextColor(colorPlayer1);
        player2TV.setTextColor(colorPlayer2);
        avatar1.setColorFilter(colorPlayer1);
        avatar2.setColorFilter(colorPlayer2);


    }

    private void sendData(Intent i) {
        i.putExtra("score1", board.getScore().getScore());
        i.putExtra("level", level);
        i.putExtra("player1", player1);
        i.putExtra("player2", player2);
    }

    public void setBoard() {
        View tableroView = ((Board) board).getView(this);
        tableLayout.addView(tableroView);

        board.addObserver(new FlipObserver() {
            @Override
            public void onFlip() {
                audioFacadeInstance.makeSound(Sound.Sounds.flip);
            }

            @Override
            public void onSuccess() {
                audioFacadeInstance.makeSound(Sound.Sounds.correct);
                if(player1Turn) {
                    player1.getScore().correct();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            puntuacionTextView.setText(String.valueOf(player1.getScore().getScore()));
                        }
                    });

                    player1Turn=false;
                    turnCrono.cancel();
                }else{
                    player2.getScore().correct();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            puntuacion2TextView.setText(String.valueOf(player2.getScore().getScore()));
                        }
                    });

                    player1Turn=true;
                    turnCrono.cancel();
                }
                if(board.isComplete()){
                    Intent i = new Intent(MultiGameActivity.this, MultiGameOverActivity.class);
                    sendData(i);
                    ConfigSingleton.getInstance().setLevelsPassed(levelNumber, thisContext);
                    chronometer.cancel();
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure() {
                if(player1Turn) {
                    player1.getScore().fail();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            puntuacionTextView.setText(String.valueOf(player1.getScore().getScore()));
                        }
                    });

                    player1Turn=false;
                }else{
                    player2.getScore().fail();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            puntuacion2TextView.setText(String.valueOf(player2.getScore().getScore()));
                        }
                    });

                    player1Turn=true;
                }
                audioFacadeInstance.makeSound(Sound.Sounds.incorrect);
            }
        });

    }


    private void instanceChronometer(long time){
        chronometer = new CountDownTimer(time, 100) {

            private final DecimalFormat cronoFormatLong = new DecimalFormat("#0.0");

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                gameOverBool = true;
                audioFacadeInstance.stopMusic();
                Intent i = new Intent(MultiGameActivity.this, MultiGameOverActivity.class);
                i.putExtra("gameOverBool", gameOverBool);
                i.putExtra("score", board.getScore().getScore());
                i.putExtra("level", level);
                startActivity(i);
                finish();
            }
        };
    }

    public void ToPausedActivity() {
        imageButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MultiGameActivity.this, PausedActivity.class);
                intent.putExtra("level", level);
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

    @Override
    protected void onDestroy(){
        super.onDestroy();
        chronometer.cancel();
    }

    @Override
    public void onBackPressed() {
        
    }
}
