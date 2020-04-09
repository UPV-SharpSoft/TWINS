package com.sharpsoft.twinsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.sharpsoft.twins_clases.logic.Tablero;
import com.sharpsoft.twinsapp.ILogic.Baraja;
import com.sharpsoft.twinsapp.ILogic.BarajaFactory;
import com.sharpsoft.twinsapp.ILogic.ICarta;
import com.sharpsoft.twinsapp.ILogic.ITablero;


public class Juego extends AppCompatActivity {
    private LinearLayout tableLayout;

    private ITablero tablero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        
        tableLayout = findViewById(R.id.tableroLinearLayout);

        Baraja baraja = BarajaFactory.getBaraja(BarajaFactory.Barajas.minecraft, 20, this);
        tablero = new ITablero(4,5, baraja, this);

        for(int y = 0; y < tablero.getHeight(); y++){
            LinearLayout horizontalLinearLayout = new LinearLayout(this);
            horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int x = 0; x < tablero.getWidth(); x++){
                ICarta carta = (ICarta) tablero.getCarta(x, y);
                View cartaView = carta.getCartaView();
                horizontalLinearLayout.addView(cartaView);
            }
            tableLayout.addView(horizontalLinearLayout);
        }
    }
}
