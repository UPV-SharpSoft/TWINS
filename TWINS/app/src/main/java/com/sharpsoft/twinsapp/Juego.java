package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Baraja;
import com.sharpsoft.twinsapp.AndroidStudioLogic.BarajaFactory;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Cronometro;
import com.sharpsoft.twinsapp.AndroidStudioLogic.Tablero;


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

        instanciarCronometro();

        addTablero();

        ToPausedActivity();

        cronometro.start();

        audioInstance.createSoundPool(this);

        //Música de fondo partida
        audioInstance.startMusic(this, R.raw.partida_default);

    }

    public void addTablero(){
        Dimension dimension = new Dimension(4,5);
        Baraja baraja = BarajaFactory.getBaraja(BarajaFactory.Barajas.minecraft, dimension, this);
        tablero = new Tablero(dimension, baraja);

        View tableroView = ((Tablero) tablero).getView(this);
        tableroLayout.addView(tableroView);
    }

    private void instanciarCronometro(){

        int valueCrono = Integer.parseInt(cronoTV.getText().toString());
        cronometro = new Cronometro(valueCrono, cronoTV, this);
    }

    /*
    public TextView getCronoTV() {
        return cronoTV;
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
        audioInstance.resumeMusic(this);
    }

    public void ToPausedActivity(){
        imageButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Juego.this, PausedActivity.class);
                /*intent.putExtra("cronometro", cronoTV.getText());*/
                startActivity(intent);
                cronometro.pause();
                audioInstance.pauseMusic(getBaseContext());
            }
        });
    }




}
