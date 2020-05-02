package com.sharpsoft.twinsapp.AndroidStudioLogic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharpsoft.twinsapp.Game;
import com.sharpsoft.twinsapp.R;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainUI();
    }

    private void setMainUI(){
        setContentView(R.layout.activity_main_menu);

        ImageButton closeAppButton = findViewById(R.id.closeAppButton);
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
                setNewGameUI();
            }
        });
    }

    private void setNewGameUI(){
        setContentView(R.layout.new_game);

        ImageButton homeImageButton = findViewById(R.id.homeImageButton);
        ImageView partidaRapidaImageView = findViewById(R.id.partidaRapidaImageView);
        TextView partidaRapidaTextView = findViewById(R.id.partidaRapidaTextView);

        View.OnClickListener partidaRapidaClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this, Game.class);
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
