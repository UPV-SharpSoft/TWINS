package com.sharpsoft.twinsapp.ILogic;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sharpsoft.twins_clases.logic.Carta;
import com.sharpsoft.twinsapp.R;

public class ICarta implements Carta {
    private Bitmap bitmapDorso, bitmapCarta;
    private ImageView imageView;
    private View layout;
    private boolean bocaArriba;

    public ICarta(Bitmap bitmapDorso, Bitmap bitmapCarta, Context ctx){
        this.bitmapCarta = bitmapCarta;
        this.bitmapDorso = bitmapDorso;

        LayoutInflater inflater = LayoutInflater.from(ctx);
        layout = inflater.inflate(R.layout.carta, null, false);
        imageView = layout.findViewById(R.id.cartaImageView);

        imageView.setImageBitmap(bitmapDorso);
    }

    public View getCartaView(){
        return layout;
    }

    @Override
    public boolean mismaImagen(Carta c) {
        if(!(c instanceof ICarta)) throw new RuntimeException("No son la misma implementacion");
        ICarta o = (ICarta) c;
        return this.bitmapCarta.equals(o.bitmapCarta);
    }

    @Override
    public void girar() {       //Faltaria animar?
        bocaArriba = !bocaArriba;
        Bitmap b = bocaArriba?bitmapCarta:bitmapDorso;
        imageView.setImageBitmap(b);
    }

    @Override
    public boolean estaBocaArriba() {
        return bocaArriba;
    }
}
