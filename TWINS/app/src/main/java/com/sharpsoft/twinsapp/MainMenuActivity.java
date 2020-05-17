package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;


public class MainMenuActivity extends AppCompatActivity {
    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();

    private Button partidaNivelesImageView;
    private Button freeGameImageView;
    private Button buttonEditDeck;
    private Button buttonRanking;
    private Button settingsButton;
    private ImageView closeAppButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        partidaNivelesImageView = findViewById(R.id.partidaNivelesImageView);
        freeGameImageView = findViewById(R.id.freeGameImageView);
        buttonEditDeck = findViewById(R.id.buttonEditDeck);
        closeAppButton = findViewById(R.id.closeAppButton);
        buttonRanking = findViewById(R.id.buttonRanking);
        settingsButton = findViewById(R.id.settingsButton);


        onClickButton();
        showExitConfirmation();

        audioFacadeInstance.startMusic(this, R.raw.menus_default);
    }

    @Override
    protected void onPause() {
        super.onPause();
        audioFacadeInstance.pauseMusic();
    }


    private void onClickButton(){
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
                Intent i = new Intent(MainMenuActivity.this, GameActivity.class);
                i.putExtra("level", ConfigSingleton.getInstance().getLevelConfig(MainMenuActivity.this));
                startActivity(i);
            }
        });

        buttonEditDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenuActivity.this, EditDeckActivity.class);
                startActivity(i);
            }
        });

        buttonRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenuActivity.this, RankingActivity.class);
                startActivity(i);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenuActivity.this, ConfigActivity.class);
                startActivity(i);
            }
        });

    }


    private void showExitConfirmation(){
        closeAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainMenuActivity.this)
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
        });
    }
}
