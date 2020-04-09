package com.sharpsoft.twinsapp.ILogic;

import android.content.Context;
import android.view.View;

import com.sharpsoft.twins_clases.logic.Tablero;


public class ITablero extends Tablero {

    public ITablero(int width, int height, Baraja set, Context ctx) {
        super(width, height);

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                final ICarta carta = new ICarta(set.sacarCarta(), set.getReverso(), ctx);
                setClickListener(carta, x, y);
                this.cartas[x][y] = carta;
            }
        }
    }

    private void setClickListener(ICarta c, final int x , final int y){
        c.getCartaView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                girar(x, y);
            }
        });
    }
}
