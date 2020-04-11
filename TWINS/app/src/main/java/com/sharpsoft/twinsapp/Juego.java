package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharpsoft.twins_clases.logic.Tablero;
import com.sharpsoft.twinsapp.ILogic.Baraja;
import com.sharpsoft.twinsapp.ILogic.BarajaFactory;
import com.sharpsoft.twinsapp.ILogic.Cronometro;
import com.sharpsoft.twinsapp.ILogic.ICarta;
import com.sharpsoft.twinsapp.ILogic.ITablero;

import java.io.IOException;
import java.util.Formatter;


public class Juego extends AppCompatActivity {
    private LinearLayout tableroLayout;
    static protected MediaPlayer musicaFondo;
    private Tablero tablero;
    static private Cronometro cronometro;
    private TextView cronoTV;
    private ImageButton imageButtonPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);



        cronoTV = findViewById(R.id.cronoTV);
        tableroLayout = findViewById(R.id.tableroLayout);
        imageButtonPause = findViewById(R.id.imageButtonPause);

        instanciarCronometro();

        Baraja baraja = BarajaFactory.getBaraja(BarajaFactory.Barajas.minecraft, 20, this);
        tablero = new ITablero(4,5, baraja, this);

        View tableroView = ((ITablero) tablero).getView(this);
        tableroLayout.addView(tableroView);

        imageButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Juego.this, PausedActivity.class);
                /*intent.putExtra("cronometro", (Parcelable) cronometro);*/
                startActivity(intent);
                cronometro.pause();
            }
        });

        if (savedInstanceState != null) {
            long timeLeftChrono = System.currentTimeMillis() - savedInstanceState.getInt("timeLeft");
            //Intentando que cuando se rote el móvil no se pierda el tiempo del crono
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cronometro.start();
            }
        });

        //Música de fondo partida
        if(savedInstanceState != null && musicaFondo != null) {
            int pos = savedInstanceState.getInt("position");

            musicaFondo.seekTo(pos);
            musicaFondo.start();
        }else{
            musicaFondo = MediaPlayer.create(this, R.raw.partida_default);
            musicaFondo.setLooping(true);
            musicaFondo.setVolume(50, 50);
            musicaFondo.start();
        }

    }

    private void instanciarCronometro(){
        int valueCrono = Integer.parseInt(cronoTV.getText().toString());
        cronometro = new Cronometro(valueCrono, cronoTV, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putInt("position" , musicaFondo.getCurrentPosition());
        musicaFondo.pause();

        outState.putLong("timeLeft", cronometro.timeLeft());

        super.onSaveInstanceState(outState);

    }

}
