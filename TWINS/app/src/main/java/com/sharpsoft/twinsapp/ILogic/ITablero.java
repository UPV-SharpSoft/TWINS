package com.sharpsoft.twinsapp.ILogic;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sharpsoft.twins_clases.logic.Dimension;
import com.sharpsoft.twins_clases.logic.Tablero;


public class ITablero extends Tablero {

    public ITablero(Dimension dimension, Baraja set) {
        super(dimension);

        for(int x = 0; x < dimension.width; x++){
            for(int y = 0; y < dimension.height; y++){
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


        for(int y = 0; y < getDimension().height; y++){
            LinearLayout horizontalLayout = new LinearLayout(ctx);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.setGravity(Gravity.CENTER);
            for(int x = 0; x < getDimension().width; x++){
                ICarta carta = (ICarta) this.getCarta(x, y);
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
