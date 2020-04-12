package com.sharpsoft.twinsapp.AndroidStudioLogic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sharpsoft.twins_clases.logic.Tablero;
import com.sharpsoft.twinsapp.Audio;
import com.sharpsoft.twinsapp.R;

import static com.sharpsoft.twinsapp.Audio.Sounds.correct;
import static com.sharpsoft.twinsapp.Audio.Sounds.flip;
import static com.sharpsoft.twinsapp.Audio.Sounds.incorrect;

public class Carta implements com.sharpsoft.twins_clases.logic.Carta {
    private Bitmap bitmapDorso, bitmapCarta;
    private ImageView imageView;
    private View layout;
    private boolean bocaArriba;

    private Tablero tablero;
    private int x, y;

    private Audio audioInstance = Audio.getInstance();

    private Context ctx;

    public Carta(Bitmap bitmapDorso, Bitmap bitmapCarta){
        this.bitmapCarta = bitmapCarta;
        this.bitmapDorso = bitmapDorso;
        bocaArriba = false;
    }

    public void setTablero(Tablero tablero, int x, int y){
        this.tablero = tablero;
        this.x = x;
        this.y = y;
    }

    public View getCartaView(Context ctx, ViewGroup parent){
        if(layout == null){
            this.ctx = ctx;
            LayoutInflater inflater = LayoutInflater.from(ctx);
            layout = inflater.inflate(R.layout.carta, parent, false);
            imageView = layout.findViewById(R.id.cartaImageView);

            imageView.setImageBitmap(bitmapDorso);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tablero != null){
                        tablero.girar(x, y);
                    }
                }
            });
        }

        return layout;
    }

    @Override
    public boolean mismaImagen(com.sharpsoft.twins_clases.logic.Carta c) {
        if(!(c instanceof Carta)) throw new RuntimeException("No son mismo tipo");
        Carta o = (Carta) c;

        return this.bitmapCarta.sameAs(o.bitmapCarta);
    }

    @Override
    public void girar() {       //Faltaria animar?
        bocaArriba = !bocaArriba;
        if(bocaArriba){
            audioInstance.makeSound(flip);
        }
        final Bitmap b = bocaArriba?bitmapCarta:bitmapDorso;



        if(layout != null){
            ((Activity) layout.getContext()).runOnUiThread(new Thread(){
                public void run(){
                    imageView.setImageBitmap(b);
                }
            });
        }
    }

    @Override
    public boolean estaBocaArriba() {
        return bocaArriba;
    }
}
