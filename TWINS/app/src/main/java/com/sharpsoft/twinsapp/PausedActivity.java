package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Player;

public class PausedActivity extends AppCompatActivity {

    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();
    private Level level;
    private Bundle bundle;
    private Player player1, player2;
    Boolean isMultiplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paused);

        final Context ctx = this;

        Button resumeGameButton = findViewById(R.id.resumeGame);
        Button restartGameButton = findViewById(R.id.restartGame);
        Button mainMenuButton = findViewById(R.id.mainMenu);
        final ImageButton muteAllButton = findViewById(R.id.muteAll);

        bundle = getIntent().getExtras();
        receiveData();


        if (audioFacadeInstance.isMutedAll()) {
            muteAllButton.setImageResource(android.R.drawable.ic_lock_silent_mode);
        }else{
            muteAllButton.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
        }

        resumeGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        muteAllButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(!audioFacadeInstance.isMutedAll()){
                    muteAllButton.setImageResource(android.R.drawable.ic_lock_silent_mode);
                    audioFacadeInstance.muteAll();
                }else{
                    muteAllButton.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
                    audioFacadeInstance.unMuteAll();
                }
            }

        });

        restartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ctx)
                        .setTitle("Reiniciar Partida")
                        .setMessage("¿Estás seguro de que quieres reiniciar la partida? \n\nPerderás todo el progreso de la partida en curso")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(isMultiplayer){
                                    MultiGameActivity.closedMethod();
                                    Intent i = new Intent(PausedActivity.this, MultiGameActivity.class);
                                    sendData(i);
                                    startActivity(i);
                                    finish();
                                }else {
                                    GameActivity.closedMethod();
                                    Intent i = new Intent(PausedActivity.this, GameActivity.class);
                                    sendData(i);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        }).setNegativeButton("No", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ctx)
                        .setTitle("Salir de la partida")
                        .setMessage("¿Estás seguro de que quieres salir de la partida? \n\nPerderás todo el progreso de la partida en curso")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                if(isMultiplayer){
                                    MultiGameActivity.closedMethod();
                                }else{
                                    GameActivity.closedMethod();
                                }
                            }
                        }).setNegativeButton("No", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void receiveData() {
        isMultiplayer = bundle.getBoolean("multiplayer");
        level = (Level) bundle.get("level");
        if(isMultiplayer) {
            player1 = (Player) bundle.get("player1");
            player2 = (Player) bundle.get("player2");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void sendData(Intent intent){
        intent.putExtra("level", level);
        if(isMultiplayer) {
            player1.resetScore();
            player2.resetScore();
            intent.putExtra("player1", player1);
            intent.putExtra("player2", player2);

        }
    }
}
