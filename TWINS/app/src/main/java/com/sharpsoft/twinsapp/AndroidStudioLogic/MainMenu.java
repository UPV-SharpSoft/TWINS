package com.sharpsoft.twinsapp.AndroidStudioLogic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharpsoft.twinsapp.EditDeck;
import com.sharpsoft.twinsapp.Game;
import com.sharpsoft.twinsapp.R;

public class MainMenu extends AppCompatActivity {
    boolean onNewGame;

    @Override
    public void onBackPressed(){
        if(onNewGame){
            setMainUI();
        }else{
            System.exit(0);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainUI();

        audioInstance.startMusic(this, R.raw.menus_default);


        buttonEditDeck = findViewById(R.id.buttonEditDeck);

        buttonEditDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, EditDeck.class);
                startActivity(intent);
            }
        });
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
        onNewGame = false;
    }

    private void setNewGameUI(){
        setContentView(R.layout.new_game);

        ImageButton homeImageButton = findViewById(R.id.homeImageButton);
        final ImageView partidaRapidaImageView = findViewById(R.id.partidaRapidaImageView);
        TextView partidaRapidaTextView = findViewById(R.id.partidaRapidaTextView);

        View.OnClickListener partidaRapidaClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioInstance.makeSound(Audio.Sounds.button);
                partidaRapidaImageView.animate()
                        .translationX(5000)
                        .scaleX(20)
                        .scaleY(20)
                        .setDuration(1000)
                        .start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainMenu.this, Game.class);
                        startActivity(i);
                    }
                }, 500);

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

        onNewGame = true;
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
