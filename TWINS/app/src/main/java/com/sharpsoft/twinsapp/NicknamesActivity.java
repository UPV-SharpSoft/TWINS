package com.sharpsoft.twinsapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.divyanshu.colorseekbar.ColorSeekBar;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Level;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Player;

import androidx.appcompat.app.AppCompatActivity;

public class NicknamesActivity extends AppCompatActivity {

    //UI
    private ColorSeekBar colorSeekBar1;
    private ColorSeekBar colorSeekBar2;
    private ImageView player1;
    private ImageView player2;
    private Button startButton;
    private EditText nickname1TV, nickname2TV;
    private String nickname1, nickname2;

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
        nickname1TV = findViewById(R.id.nickname);
        nickname2TV = findViewById(R.id.nickname2);
        startButton = findViewById(R.id.startButton);

        colorPlayer1 = -5687754;
        colorPlayer2 = -12616451;

        nickname1TV.setText("");
        nickname2TV.setText("");

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

                nickname1 = nickname1TV.getText().toString();
                nickname2 = nickname2TV.getText().toString();

                if (nickname1.equals(nickname2)) {
                    Toast.makeText(getApplicationContext(),"El nickname de los" +
                            " jugadores no puede ser igual", Toast.LENGTH_SHORT).show();
                } else {
                    if (nickname1.equals("") || nickname2.equals("")) {
                        Toast.makeText(getApplicationContext(),"El campo de nombre" +
                                " no puede estar vacío", Toast.LENGTH_SHORT).show();
                    } else {
                        Player player1 = new Player(colorPlayer1, nickname1);
                        Player player2 = new Player(colorPlayer2, nickname2);
                        Intent i = new Intent(NicknamesActivity.this, MultiGameActivity.class);
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
