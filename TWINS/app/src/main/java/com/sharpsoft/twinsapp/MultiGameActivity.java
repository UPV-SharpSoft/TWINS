package com.sharpsoft.twinsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
    private CountDownTimer turnCrono;
    private int turnSeconds;
    private LinearLayout tableLayout;
    private com.sharpsoft.twins_clases.logic.Board board;
    private ImageButton imageButtonPause;
    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();
    private boolean first = true;
    private boolean gameOverBool = false;
    private static boolean closed = false;
    private Level level;
    private int levelNumber;
    private final DecimalFormat cronoFormatLong = new DecimalFormat("#0.0");
    private Player player1;
    private Player player2;
    private boolean player1Turn = true;

    private static MultiGameActivity instance;

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

        instance = this;

        receiveData();

        int song = ConfigSingleton.getInstance().getSelectedMusic();

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

        board.setTiempoVolteo(level.getFlipTime() == null? 500 : level.getFlipTime());

        setBoard();
        ToPausedActivity();

        audioFacadeInstance.setMusicGame(this, song);

        Integer startTimeFlip = level.getFlipStartTime();
        if(startTimeFlip != null) board.flipAllCardsDuring(startTimeFlip);

        turnSeconds = board.getTurn().getDuration();
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
                if(player1Turn) {
                    player1.getScore().missedTurn();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            puntuacionTextView.setText(String.valueOf(player1.getScore().getScore()));

                        }
                    });
                    changeTurn();
                }else{
                    player2.getScore().missedTurn();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            puntuacion2TextView.setText(String.valueOf(player2.getScore().getScore()));
                        }
                    });
                    changeTurn();
                }

            }
        });
    }

    private void changeTurn(){
        player1Turn = !player1Turn;
        Log.i("color", ""+colorPlayer1);
        if(player1Turn){
            tableLayout.getBackground().setAlpha(50);
            tableLayout.getBackground().setTint(colorPlayer1);
        }else{
            tableLayout.getBackground().setAlpha(50);
            tableLayout.getBackground().setTint(colorPlayer2);
        }
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
        tableLayout.getBackground().setAlpha(50);
        tableLayout.getBackground().setTint(colorPlayer1);

    }

    private void sendData(Intent i) {
        // ESTO ES NECESARIO? i.putExtra("score1", board.getScore().getScore());
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

                    changeTurn();
                    //turnCrono.cancel();
                }else{
                    player2.getScore().correct();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            puntuacion2TextView.setText(String.valueOf(player2.getScore().getScore()));
                        }
                    });

                    changeTurn();
                    //turnCrono.cancel();
                }
                if(board.isComplete()){
                    Intent i = new Intent(MultiGameActivity.this, MultiGameOverActivity.class);
                    sendData(i);
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

                    changeTurn();
                }else{
                    player2.getScore().fail();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            puntuacion2TextView.setText(String.valueOf(player2.getScore().getScore()));
                        }
                    });

                    changeTurn();
                }
                audioFacadeInstance.makeSound(Sound.Sounds.incorrect);
            }
        });

    }


    public void ToPausedActivity() {
        imageButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MultiGameActivity.this, PausedActivity.class);
                sendData(intent);
                intent.putExtra("level", level);
                intent.putExtra("multiplayer", true);
                startActivity(intent);
                audioFacadeInstance.pauseMusic();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!first) {
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
    }

    @Override
    public void onBackPressed() {
        
    }

    public static void closedMethod(){
        instance.finish();
    }
}
