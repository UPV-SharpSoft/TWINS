package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.sharpsoft.twins_clases.logic.Tablero;
import com.sharpsoft.twinsapp.ILogic.Baraja;
import com.sharpsoft.twinsapp.ILogic.BarajaFactory;
import com.sharpsoft.twinsapp.ILogic.ICarta;
import com.sharpsoft.twinsapp.ILogic.ITablero;


public class Juego extends AppCompatActivity {
    private LinearLayout tableroLayout;

    private Tablero tablero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        tableroLayout = findViewById(R.id.tableroLinearLayout);

        Baraja baraja = BarajaFactory.getBaraja(BarajaFactory.Barajas.minecraft, 20, this);
        tablero = new ITablero(4,5, baraja, this);

        for(int y = 0; y < tablero.getHeight(); y++){
            LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int x = 0; x < tablero.getWidth(); x++){
                ICarta carta = (ICarta) tablero.getCarta(x, y);
                View cartaView = carta.getCartaView();
                horizontalLayout.addView(cartaView);
            }
            tableroLayout.addView(horizontalLayout);
        }
    }
}
