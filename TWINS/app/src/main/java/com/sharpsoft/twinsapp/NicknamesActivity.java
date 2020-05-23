package com.sharpsoft.twinsapp;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.divyanshu.colorseekbar.ColorSeekBar;
import com.sharpsoft.twins_clases.logic.FlipObserver;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Player;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Score;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class NicknamesActivity extends AppCompatActivity {

    //UI
    private ColorSeekBar colorSeekBar1;
    private ColorSeekBar colorSeekBar2;
    private ImageView player1;
    private ImageView player2;
    private Button startButton;
    private EditText nickname1;
    private EditText nickname2;

    Level level;
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
        nickname1 = findViewById(R.id.nickname);
        nickname2 = findViewById(R.id.nickname2);
        startButton = findViewById(R.id.startButton);

        colorPlayer1 = -5687754;
        colorPlayer2 = -12616451;

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

        //Receive Level
        level = (Level) getIntent().getExtras().get("level");
        level.setTotalTime(Integer.MAX_VALUE);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (colorPlayer1 == colorPlayer2 || nickname1.getText().equals(nickname2.getText())) {
                    Toast.makeText(getApplicationContext(),"El color o el nickname de los" +
                            " jugadores no puede ser igual", Toast.LENGTH_SHORT).show();
                } else {
                    if (nickname1.getText().equals("") || nickname2.getText().equals("")) {
                        Toast.makeText(getApplicationContext(),"El campo de nombre" +
                                " no puede estar vacío", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(NicknamesActivity.this, MultiGameActivity.class);
                        Player player1 = new Player(colorPlayer1, nickname1.toString());
                        Player player2 = new Player(colorPlayer2, nickname2.toString());
                        try{
                            i.putExtra("player1", player1);
                            i.putExtra("player2", player2);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        i.putExtra("level", level);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });


    }
}
