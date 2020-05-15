package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;

import java.util.Random;

public class MainMenuActivity extends AppCompatActivity {
    boolean onNewGame;
    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();

   /* @Override
    public void onBackPressed(){
        if(onNewGame){
            setMainUI();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Salir de la partida")
                    .setMessage("¿Estás seguro de que quieres salir del juego?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                            finish();
                        }
                    }).setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }*/


    @Override
    protected void onPause() {
        super.onPause();
        audioFacadeInstance.pauseMusic();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);

        final ImageView partidaNivelesImageView = findViewById(R.id.partidaNivelesImageView);
        final ImageView freeGameImageView = findViewById(R.id.freeGameImageView);

        partidaNivelesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainMenuActivity.this, LevelsActivity.class);
                        startActivity(i);
                    }
                }, 500);
            }
        });

        freeGameImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenuActivity.this, FreeGamemodeActivity.class);
                startActivity(i);
            }
        });

        audioFacadeInstance.startMusic(this, R.raw.menus_default);
    }


    private void showExitConfirmation(){
        new AlertDialog.Builder(this)
                .setTitle("Salir")
                .setMessage("¿Estás seguro que quieres salir?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                }).setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
