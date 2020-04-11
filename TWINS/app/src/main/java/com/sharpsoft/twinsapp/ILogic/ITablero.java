package com.sharpsoft.twinsapp.ILogic;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.sharpsoft.twins_clases.logic.Tablero;


public class ITablero extends Tablero {

    public ITablero(int width, int height, Baraja set, Context ctx) {
        super(width, height);

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                final ICarta carta = new ICarta(set.getReverso(), set.sacarCarta());
                carta.setTablero(this, x, y);
                this.cartas[x][y] = carta;
            }
        }
    }

    public View getView(Context ctx){
        LinearLayout tableroLayout = new LinearLayout(ctx);
        tableroLayout.setOrientation(LinearLayout.VERTICAL);
        tableroLayout.setGravity(Gravity.CENTER);

        for(int y = 0; y < this.getHeight(); y++){
            LinearLayout horizontalLayout = new LinearLayout(ctx);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.setGravity(Gravity.CENTER);
            for(int x = 0; x < this.getWidth(); x++){
                ICarta carta = (ICarta) this.getCarta(x, y);
                View cartaView = carta.getCartaView(ctx);
                horizontalLayout.addView(cartaView);
            }
            tableroLayout.addView(horizontalLayout);
        }

        return tableroLayout;
    }

}
