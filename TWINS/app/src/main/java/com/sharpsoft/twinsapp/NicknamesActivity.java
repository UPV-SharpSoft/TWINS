package com.sharpsoft.twinsapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.divyanshu.colorseekbar.ColorSeekBar;
import com.sharpsoft.twins_clases.logic.FlipObserver;

import androidx.appcompat.app.AppCompatActivity;

public class NicknamesActivity extends AppCompatActivity {

    //UI
    private ColorSeekBar colorSeekBar1;
    private ColorSeekBar colorSeekBar2;
    private ImageView player1;
    private ImageView player2;
    private Button startButton;
    private EditText nickname1;
    private EditText nickname2;

    private int colorPlayer1;
    private int colorPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nicknames);

        //UI
        colorSeekBar1 = findViewById(R.id.colorSeekBar1);
        colorSeekBar2 = findViewById(R.id.colorSeekBar2);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        startButton = findViewById(R.id.startButton);

        colorSeekBar1.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int i) {
                player1.setColorFilter(i);
                colorPlayer1 = i;
            }
        });

        colorSeekBar2.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int i) {
                player2.setColorFilter(i);
                colorPlayer2 = i;
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(colorPlayer1 == colorPlayer2 || nickname1 == nickname2) {
                    Intent i = new Intent(NicknamesActivity.this, GameActivity.class);
                    i.putExtra("nickname1", nickname1.getText());
                    i.putExtra("nickname2", nickname2.getText());
                    i.putExtra("color1", colorPlayer1);
                    i.putExtra("color2", colorPlayer2);
                } else {
                    Toast.makeText(getApplicationContext(),"El color o el nickname de los" +
                            " jugadores no puede ser igual", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
