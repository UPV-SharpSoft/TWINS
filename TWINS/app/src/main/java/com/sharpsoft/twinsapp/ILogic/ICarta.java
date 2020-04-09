package com.sharpsoft.twinsapp.ILogic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.sharpsoft.twins_clases.logic.Carta;
import com.sharpsoft.twinsapp.R;

public class ICarta implements Carta {
    private Bitmap bitmapDorso, bitmapCarta;
    private ImageView imageView;
    private View layout;
    private boolean bocaArriba;

    private Context ctx;

    public ICarta(Bitmap bitmapDorso, Bitmap bitmapCarta, Context ctx){
        this.bitmapCarta = bitmapCarta;
        this.bitmapDorso = bitmapDorso;
        this.ctx = ctx;

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
        if(!(c instanceof ICarta)) throw new RuntimeException("No son mismo tipo");
        ICarta o = (ICarta) c;
        return this.bitmapCarta.sameAs(o.bitmapCarta);
    }

    @Override
    public void girar() {       //Faltaria animar?
        bocaArriba = !bocaArriba;
        final Bitmap b = bocaArriba?bitmapCarta:bitmapDorso;

        ((Activity) ctx).runOnUiThread(new Thread(){
            public void run(){
                imageView.setImageBitmap(b);
            }
        });
    }

    @Override
    public boolean estaBocaArriba() {
        return bocaArriba;
    }
}
