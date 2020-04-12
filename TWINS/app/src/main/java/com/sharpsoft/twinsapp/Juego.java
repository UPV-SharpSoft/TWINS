package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twins_clases.logic.FlipObserver;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Baraja;
import com.sharpsoft.twinsapp.AndroidStudioLogic.BarajaFactory;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Cronometro;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Tablero;

import java.util.Observable;

import static com.sharpsoft.twinsapp.Audio.Sounds.correct;
import static com.sharpsoft.twinsapp.Audio.Sounds.incorrect;


public class Juego extends AppCompatActivity {
    private TextView cronoTV;
    private LinearLayout tableroLayout;
    private com.sharpsoft.twins_clases.logic.Tablero tablero;
    private Cronometro cronometro;
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

        instanciarCronometro();

        ToPausedActivity();

        cronometro.start();

        audioInstance.createSoundPool(this);

        //Música de fondo partida
        audioInstance.startMusic(this, R.raw.partida_default);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //instanciarCronometro();
        cronometro.start();
        audioInstance.resumeMusic(this);
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
                Log.i("Flip", "Flip");
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }
        });

        /*tablero.addObserver(new FlipObserver() {
            @Override
            public void update(Observable observable, Object o) {
                if (o == On.success && tablero.isComplete()) {
                    Log.i("Completado", "Tablero completado");
                    //
                } else if (o == On.success) {
                    new Thread() {
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            audioInstance.makeSound(correct);
                        }
                    }.start();
                } else if (o == On.failure) {
                    new Thread() {
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            audioInstance.makeSound(incorrect);
                        }
                    }.start();
                }
            }

        });*/
    }
    
    private void instanciarCronometro(){

        int valueCrono = Integer.parseInt(cronoTV.getText().toString());
        cronometro = new Cronometro(valueCrono, cronoTV, this);
    }


    public void ToPausedActivity() {
        imageButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Juego.this, PausedActivity.class);
                /*intent.putExtra("cronometro", cronoTV.getText());*/
                startActivity(intent);
                cronometro.pause();
                audioInstance.pauseMusic(getBaseContext());
            }
        });
    }
}
