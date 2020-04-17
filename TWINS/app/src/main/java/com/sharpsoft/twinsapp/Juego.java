package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twins_clases.logic.FlipObserver;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Baraja;
import com.sharpsoft.twinsapp.AndroidStudioLogic.BarajaFactory;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Puntuacion;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Tablero;

import java.text.DecimalFormat;


import static com.sharpsoft.twinsapp.Audio.Sounds.*;


public class Juego extends AppCompatActivity {
    //Crono's things
    private TextView cronoTV;
    private CountDownTimer cronometro;
    private long timeLeft;
    private boolean primero = true;

    private boolean gameOverBool = false;

    private LinearLayout tableroLayout;
    private com.sharpsoft.twins_clases.logic.Tablero tablero;
    private ImageButton imageButtonPause;
    private Audio audioInstance = Audio.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        cronoTV = findViewById(R.id.cronoTV);
        tableroLayout = findViewById(R.id.tableroLayout);
        imageButtonPause = findViewById(R.id.imageButtonPause);

        addTablero();

        instanciarCronometro(60000);//Time in ms
        cronometro.start();

        ToPausedActivity();

        //Música de fondo partida
        float volume = (float) (1 - (Math.log(Audio.MAX_VOLUME - audioInstance.getMusicSeekbarProgress()) / Math.log(Audio.MAX_VOLUME)));
        audioInstance.startMusic(this, R.raw.partida_default);
        audioInstance.setMusicVolume(volume, volume);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!primero) {
            instanciarCronometro(timeLeft);
            cronometro.start();
            audioInstance.resumeMusic(this);
        }
        primero = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        audioInstance.pauseMusic();
    }

    public void addTablero() {
        Dimension dimension = new Dimension(4, 5);
        Baraja baraja = BarajaFactory.getBaraja(BarajaFactory.Barajas.minecraft, dimension, this);
        tablero = new Tablero(dimension, baraja);

        View tableroView = ((Tablero) tablero).getView(this);
        tableroLayout.addView(tableroView);

        tablero.addObserver(new FlipObserver() {
            @Override
            public void onFlip() {
                audioInstance.makeSound(flip);

                Log.i("tablero", "tablero " + tablero.isComplete());
            }

            @Override
            public void onSuccess() {
                audioInstance.makeSound(correct);
                if(tablero.isComplete()){ //Si se ha terminado el tablero
                    Intent i = new Intent(Juego.this, FinPartida.class);
                    i.putExtra("gameOverBool", gameOverBool);
                    i.putExtra("timeLeft", timeLeft);
                    cronometro.cancel();
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure() {
                audioInstance.makeSound(incorrect);
            }
        });

        TextView puntuacionTextView = findViewById(R.id.puntuacionTextView);
        tablero.setPuntuacion(new Puntuacion(puntuacionTextView));
    }
    
    private void instanciarCronometro(long time){
        cronometro = new CountDownTimer(time, 100) {

            private final DecimalFormat cronoFormatLong = new DecimalFormat("#0.0");

            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished>10000) cronoTV.setText("" + millisUntilFinished / 1000);
                else cronoTV.setText("" + cronoFormatLong.format(millisUntilFinished / 1000.0));
                timeLeft = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                gameOverBool = true;
                audioInstance.stopMusic();
                audioInstance.makeSound(Audio.Sounds.gameover);
                cronoTV.setText(cronoFormatLong.format(0));

                Intent i = new Intent(Juego.this, FinPartida.class);
                i.putExtra("gameOverBool", gameOverBool);
                startActivity(i);
                finish();
            }
        };
    }


    public void ToPausedActivity() {
        imageButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Juego.this, PausedActivity.class);
                /*intent.putExtra("cronometro", cronoTV.getText());*/
                startActivity(intent);
                cronometro.cancel();
                audioInstance.pauseMusic();
            }
        });
    }



}
