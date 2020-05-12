package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.AndroidStudioLogic.AudioFacade;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ConfigSingleton;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Deck;
import com.sharpsoft.twinsapp.AndroidStudioLogic.ILevelBuilder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;
import com.sharpsoft.twinsapp.AndroidStudioLogic.LevelDirector;
import com.sharpsoft.twinsapp.AndroidStudioLogic.LevelRandomBuilder;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Sound;

import java.util.Random;

public class MainMenuActivity extends AppCompatActivity {
    boolean onNewGame;
    private AudioFacade audioFacadeInstance = AudioFacade.getInstance();

    @Override
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
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        audioFacadeInstance.startMusic(this, R.raw.menus_default);

        setMainUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //audioFacadeInstance.startMusic(this, R.raw.menus_default);

        setMainUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        audioFacadeInstance.pauseMusic();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setMainUI();

        audioFacadeInstance.startMusic(this, R.raw.menus_default);
    }

    private void setMainUI(){
        setContentView(R.layout.activity_main_menu);

        final ImageButton closeAppButton = findViewById(R.id.closeAppButton);
        Button newGameButton = findViewById(R.id.newGameButton);

        closeAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExitConfirmation();
            }
        });

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView playImage = findViewById(R.id.imageView3);
                playImage.animate()
                        .rotationY(360)
                        .setDuration(500)
                        .start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setNewGameUI();
                    }
                }, 500);
            }
        });

        Button buttonEditDeck = findViewById(R.id.buttonEditDeck);

        buttonEditDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, EditDeckActivity.class);
                startActivity(intent);
            }
        });

        setBottomButtons();
        onNewGame = false;
    }

    private void setNewGameUI(){
        setContentView(R.layout.new_game);

        final ImageButton homeImageButton = findViewById(R.id.homeImageButton);
        final ImageView partidaRapidaImageView = findViewById(R.id.partidaRapidaImageView);
        final TextView partidaRapidaTextView = findViewById(R.id.partidaRapidaTextView);
        final ImageView partidaNivelesImageView = findViewById(R.id.partidaNivelesImageView);
        final TextView partidaNivelesTextView = findViewById(R.id.partidaNivelesTextView);
        final ImageView freeGameImageView = findViewById(R.id.freeGameImageView);
        final TextView freeGameTextView = findViewById(R.id.freeGameTextView);


        View.OnClickListener partidaRapidaClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioFacadeInstance.makeSound(Sound.Sounds.button);
                partidaRapidaImageView.animate()
                        .translationX(5000)
                        .scaleX(20)
                        .scaleY(20)
                        .setDuration(1000)
                        .start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ILevelBuilder builder = new LevelRandomBuilder();
                        new LevelDirector().Construct(builder);
                        Level level = builder.getLevel();

                        Intent i = new Intent(MainMenuActivity.this, GameActivity.class);
                        i.putExtra("level", level);
                        startActivity(i);
                    }
                }, 500);

            }
        };

        View.OnClickListener nivelesClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainMenuActivity.this, LevelsActivity.class);
                        startActivity(i);
                    }
                }, 500);
            }
        };

        View.OnClickListener freeGameClick = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuActivity.this, FreeGamemodeActivity.class);
                startActivity(i);
            }
        };

        homeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMainUI();
            }
        });

        partidaRapidaImageView.setOnClickListener(partidaRapidaClick);
        partidaRapidaTextView.setOnClickListener(partidaRapidaClick);
        partidaNivelesImageView.setOnClickListener(nivelesClick);
        partidaNivelesTextView.setOnClickListener(nivelesClick);
        freeGameImageView.setOnClickListener(freeGameClick);
        freeGameTextView.setOnClickListener(freeGameClick);


        onNewGame = true;
    }

    private void setBottomButtons(){
        ImageButton store = findViewById(R.id.appStoreButton);
        ImageButton twitter = findViewById(R.id.twitterButton);
        ImageButton facebook = findViewById(R.id.facebookButton);
        ImageButton gmail = findViewById(R.id.gmailButton);

        store.setOnClickListener(openWeb("https://play.google.com"));
        twitter.setOnClickListener(openWeb("https://www.twitter.com/MalakitoDeTWINS"));
        facebook.setOnClickListener(openWeb("https://www.facebook.com"));
        gmail.setOnClickListener(sendEmail());
    }

    private View.OnClickListener openWeb(final String url){
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        };
    }

    private View.OnClickListener sendEmail(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String[] TO = {"twinsmalakito@gmail.com"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        };
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
