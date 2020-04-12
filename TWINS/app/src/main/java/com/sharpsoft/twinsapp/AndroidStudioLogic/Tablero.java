package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twins_clases.logic.MalformedTableroException;


public class Tablero extends com.sharpsoft.twins_clases.logic.Tablero {

    public Tablero(Dimension dimension, Baraja set) {
        super(dimension);
        if(set.getNumCartas() != dimension.getTotal()) throw new MalformedTableroException("La dimension de la baraja y el tablero no coinciden!");

        for(int x = 0; x < dimension.width; x++){
            for(int y = 0; y < dimension.height; y++){
                final Carta carta = new Carta(set.getReverso(), set.sacarCarta());
                carta.setTablero(this, x, y);
                this.cartas[x][y] = carta;
            }
        }
    }

    public View getView(Context ctx){
        LinearLayout tableroLayout = new LinearLayout(ctx);
        tableroLayout.setOrientation(LinearLayout.VERTICAL);
        tableroLayout.setGravity(Gravity.CENTER);


        for(int y = 0; y < getDimension().height; y++){
            LinearLayout horizontalLayout = new LinearLayout(ctx);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.setGravity(Gravity.CENTER);
            for(int x = 0; x < getDimension().width; x++){
                Carta carta = (Carta) this.getCarta(x, y);
                View cartaView = carta.getCartaView(ctx, horizontalLayout);

                ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(cartaView.getLayoutParams());
                marginParams.setMargins(5,5,5,5);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(marginParams);
                cartaView.setLayoutParams(layoutParams);

                horizontalLayout.addView(cartaView);
            }
            tableroLayout.addView(horizontalLayout);
        }

        return tableroLayout;
    }
}
